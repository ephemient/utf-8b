package com.github.ephemient.utf8b

import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.charset.Charset
import java.nio.charset.CharsetDecoder
import java.nio.charset.CharsetEncoder
import java.nio.charset.CoderResult
import java.nio.charset.CodingErrorAction
import java.nio.charset.StandardCharsets

/**
 * [UTF-8b/surrogateescape](http://permalink.gmane.org/gmane.comp.internationalization.linux/920) encoding.
 *
 * All valid UTF-8 bytes should decode and and all valid Unicode strings (no unpaired surrogates) should encode to the
 * same value as [StandardCharsets.UTF_8].
 *
 * However, instead of [ignoring](CodingErrorAction.IGNORE), [replacing](CodingErrorAction.REPLACE), or
 * [reporting](CodingErrorAction.REPORT) invalid UTF-8 bytes, they will be instead be escaped as unpaired low
 * surrogates on decoding, which cannot otherwise appear in UTF-8/Unicode, and subsequently unescaped on encoding.
 *
 * See also [PEP-383](https://www.python.org/dev/peps/pep-0383/),
 * [Re: UTF-16 encoding of malformed UTF-8 sequences][https://www.unicode.org/mail-arch/unicode-ml/Archives-Old/UML019/0978.html], and
 * [Substituting malformed UTF-8 sequences in a decoder][https://www.unicode.org/mail-arch/unicode-ml/Archives-Old/UML019/0978.html].
 */
object UTF8b : Charset("UTF-8b", arrayOf("UTF8b")) {
    override fun contains(cs: Charset?): Boolean = cs is UTF8b || StandardCharsets.UTF_8.contains(cs)
    override fun newDecoder(): CharsetDecoder = Decoder(this)
    override fun newEncoder(): CharsetEncoder = Encoder(this)

    private class Decoder(cs: Charset) : CharsetDecoder(cs, 1f, 2f) {
        private val buffer = ByteArray(4)
        private var buffered = 0

        override fun decodeLoop(`in`: ByteBuffer, out: CharBuffer): CoderResult {
            while (buffered > 0 || `in`.hasRemaining()) {
                if (!out.hasRemaining()) return CoderResult.OVERFLOW
                val length: Int
                var code = when (val b = if (buffered > 0) buffer[0] else `in`.get().also { buffer[buffered++] = it }) {
                    in 0..0x7f -> b.toInt().also { length = 1 }
                    in 0xc0.toByte()..0xdf.toByte() -> b.toInt().and(0x1f).also { length = 2 }
                    in 0xe0.toByte()..0xef.toByte() -> b.toInt().and(0x0f).also { length = 3 }
                    in 0xf0.toByte()..0xf7.toByte() -> b.toInt().and(0x07).also { length = 4 }
                    else -> (-1).also { length = 1 }
                }
                for (i in 1 until length) {
                    val b = when {
                        i < buffered -> buffer[i]
                        `in`.hasRemaining() -> `in`.get().also { buffer[buffered++] = it }
                        else -> return CoderResult.UNDERFLOW
                    }
                    if (b !in 0x80.toByte()..0xbf.toByte()) {
                        code = -1
                        break
                    }
                    code = code shl 6 or b.toInt().and(0x3f)
                }
                when (code) {
                    in 0xd800..0xdfff, !in when (length) {
                        1 -> 0..0x7f
                        2 -> 0x80..0x07ff
                        3 -> 0x0800..0xffff
                        4 -> 0x10000..0x10ffff
                        else -> IntRange.EMPTY
                    } -> {
                        out.put(buffer[0].toInt().and(0x7f).or(0xDC80).toChar())
                        buffer.copyInto(buffer, 0, 1, buffered--)
                        continue
                    }
                    in 0..0xffff -> out.put(code.toChar())
                    in 0x10000..0x10ffff -> {
                        if (out.remaining() < 2) return CoderResult.OVERFLOW
                        out.put(code.minus(0x10000).shr(10).and(0x03ff).or(0xd800).toChar())
                        out.put(code.minus(0x10000).and(0x03ff).or(0xdc00).toChar())
                    }
                    else -> throw IllegalStateException("Unicode codepoint out of range: U+%04X".format(code))
                }
                buffer.copyInto(buffer, 0, length, buffered)
                buffered -= length
            }
            return CoderResult.UNDERFLOW
        }

        override fun implFlush(out: CharBuffer): CoderResult {
            if (out.remaining() <= buffered) return CoderResult.OVERFLOW
            repeat(buffered) { out.put(buffer[it].toInt().and(0x7f).or(0xDC80).toChar()) }
            buffered = 0
            return super.implFlush(out)
        }

        override fun implReset() {
            buffered = 0
            super.implReset()
        }
    }

    private class Encoder(cs: Charset) : CharsetEncoder(cs, 1f, 3f) {
        override fun encodeLoop(`in`: CharBuffer, out: ByteBuffer): CoderResult {
            while (`in`.hasRemaining()) {
                val position = `in`.position()
                val length: Int
                val code = when (val high = `in`.get(position)) {
                    in '\ud800'..'\udbff' -> {
                        length = 2
                        if (`in`.remaining() < length) return CoderResult.UNDERFLOW
                        val low = `in`.get(position + 1)
                        if (low !in '\udc00'..'\udfff') return CoderResult.malformedForLength(length)
                        high.code.and(0x03ff).shl(10).or(low.code and 0x03ff) + 0x10000
                    }
                    in '\udc80'..'\udcff' -> {
                        if (!out.hasRemaining()) return CoderResult.OVERFLOW
                        out.put(high.code.toByte())
                        `in`.position(position + 1)
                        continue
                    }
                    in '\udc00'..'\udfff' -> return CoderResult.malformedForLength(1)
                    else -> high.code.also { length = 1 }
                }
                when (code) {
                    in 0..0x7f -> {
                        if (!out.hasRemaining()) return CoderResult.OVERFLOW
                        out.put(code.toByte())
                    }
                    in 0x80..0x07ff -> {
                        if (out.remaining() < 2) return CoderResult.OVERFLOW
                        out.put(code.shr(6).or(0xc0).toByte())
                        out.put(code.and(0x3f).or(0x80).toByte())
                    }
                    in 0x0800..0xffff -> {
                        if (out.remaining() < 3) return CoderResult.OVERFLOW
                        out.put(code.shr(12).or(0xe0).toByte())
                        out.put(code.shr(6).and(0x3f).or(0x80).toByte())
                        out.put(code.and(0x3f).or(0x80).toByte())
                    }
                    in 0x10000..0x10ffff -> {
                        if (out.remaining() < 4) return CoderResult.OVERFLOW
                        out.put(code.shr(18).or(0xf0).toByte())
                        out.put(code.shr(12).and(0x3f).or(0x80).toByte())
                        out.put(code.shr(6).and(0x3f).or(0x80).toByte())
                        out.put(code.and(0x3f).or(0x80).toByte())
                    }
                    else -> throw IllegalStateException("Unicode codepoint out of range: U+%04X".format(code))
                }
                `in`.position(position + length)
            }
            return CoderResult.UNDERFLOW
        }
    }
}
