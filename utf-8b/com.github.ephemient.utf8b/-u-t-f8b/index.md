//[utf-8b](../../../index.md)/[com.github.ephemient.utf8b](../index.md)/[UTF8b](index.md)

# UTF8b

[jvm]\
object [UTF8b](index.md) : [Charset](https://docs.oracle.com/javase/8/docs/api/java/nio/charset/Charset.html)

[UTF-8b/surrogateescape](http://permalink.gmane.org/gmane.comp.internationalization.linux/920) encoding.

All valid UTF-8 bytes should decode and and all valid Unicode strings (no unpaired surrogates) should encode to the same value as [StandardCharsets.UTF_8](https://docs.oracle.com/javase/8/docs/api/java/nio/charset/StandardCharsets.html#UTF_8--).

However, instead of [ignoring](https://docs.oracle.com/javase/8/docs/api/java/nio/charset/CodingErrorAction.html#IGNORE--), [replacing](https://docs.oracle.com/javase/8/docs/api/java/nio/charset/CodingErrorAction.html#REPLACE--), or [reporting](https://docs.oracle.com/javase/8/docs/api/java/nio/charset/CodingErrorAction.html#REPORT--) invalid UTF-8 bytes, they will be instead be escaped as unpaired low surrogates on decoding, which cannot otherwise appear in UTF-8/Unicode, and subsequently unescaped on encoding.

See also [PEP-383](https://www.python.org/dev/peps/pep-0383/), [Re: UTF-16 encoding of malformed UTF-8 sequences](https://www.unicode.org/mail-arch/unicode-ml/Archives-Old/UML019/0978.html), and [Substituting malformed UTF-8 sequences in a decoder](http://hyperreal.org/~est/utf-8b/releases/utf-8b-20060413043934/kuhn-utf-8b.html).

## Functions

| Name | Summary |
|---|---|
| [aliases](index.md#-546971317%2FFunctions%2F-1216412040) | [jvm]<br>fun [aliases](index.md#-546971317%2FFunctions%2F-1216412040)(): [MutableSet](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [canEncode](index.md#728209475%2FFunctions%2F-1216412040) | [jvm]<br>open fun [canEncode](index.md#728209475%2FFunctions%2F-1216412040)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [compareTo](index.md#-818878491%2FFunctions%2F-1216412040) | [jvm]<br>operator override fun [compareTo](index.md#-818878491%2FFunctions%2F-1216412040)(other: [Charset](https://docs.oracle.com/javase/8/docs/api/java/nio/charset/Charset.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [contains](contains.md) | [jvm]<br>open operator override fun [contains](contains.md)(cs: [Charset](https://docs.oracle.com/javase/8/docs/api/java/nio/charset/Charset.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [decode](index.md#2064500977%2FFunctions%2F-1216412040) | [jvm]<br>fun [decode](index.md#2064500977%2FFunctions%2F-1216412040)(p0: [ByteBuffer](https://docs.oracle.com/javase/8/docs/api/java/nio/ByteBuffer.html)): [CharBuffer](https://docs.oracle.com/javase/8/docs/api/java/nio/CharBuffer.html) |
| [displayName](index.md#-1513488708%2FFunctions%2F-1216412040) | [jvm]<br>open fun [displayName](index.md#-1513488708%2FFunctions%2F-1216412040)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>open fun [displayName](index.md#-994016414%2FFunctions%2F-1216412040)(p0: [Locale](https://docs.oracle.com/javase/8/docs/api/java/util/Locale.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [encode](index.md#1454322059%2FFunctions%2F-1216412040) | [jvm]<br>fun [encode](index.md#1454322059%2FFunctions%2F-1216412040)(p0: [CharBuffer](https://docs.oracle.com/javase/8/docs/api/java/nio/CharBuffer.html)): [ByteBuffer](https://docs.oracle.com/javase/8/docs/api/java/nio/ByteBuffer.html)<br>fun [encode](index.md#-934959415%2FFunctions%2F-1216412040)(p0: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [ByteBuffer](https://docs.oracle.com/javase/8/docs/api/java/nio/ByteBuffer.html) |
| [equals](index.md#1995042864%2FFunctions%2F-1216412040) | [jvm]<br>operator override fun [equals](index.md#1995042864%2FFunctions%2F-1216412040)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](index.md#182600374%2FFunctions%2F-1216412040) | [jvm]<br>override fun [hashCode](index.md#182600374%2FFunctions%2F-1216412040)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [isRegistered](index.md#559096485%2FFunctions%2F-1216412040) | [jvm]<br>fun [isRegistered](index.md#559096485%2FFunctions%2F-1216412040)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [name](index.md#-831779770%2FFunctions%2F-1216412040) | [jvm]<br>fun [name](index.md#-831779770%2FFunctions%2F-1216412040)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [newDecoder](new-decoder.md) | [jvm]<br>open override fun [newDecoder](new-decoder.md)(): [CharsetDecoder](https://docs.oracle.com/javase/8/docs/api/java/nio/charset/CharsetDecoder.html) |
| [newEncoder](new-encoder.md) | [jvm]<br>open override fun [newEncoder](new-encoder.md)(): [CharsetEncoder](https://docs.oracle.com/javase/8/docs/api/java/nio/charset/CharsetEncoder.html) |
| [toString](index.md#4434309%2FFunctions%2F-1216412040) | [jvm]<br>override fun [toString](index.md#4434309%2FFunctions%2F-1216412040)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
