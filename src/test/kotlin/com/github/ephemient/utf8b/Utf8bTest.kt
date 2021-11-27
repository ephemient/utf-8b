package com.github.ephemient.utf8b

import org.jetbrains.jetCheck.Generator
import org.jetbrains.jetCheck.Generator.charsInRange
import org.jetbrains.jetCheck.Generator.integers
import org.jetbrains.jetCheck.Generator.listsOf
import org.jetbrains.jetCheck.Generator.stringsOf
import org.jetbrains.jetCheck.PropertyChecker.forAll
import org.junit.jupiter.api.Test

class Utf8bTest {
    @Test
    fun `decode non-special characters`() = forAll(
        listsOf(integers(0, Byte.MAX_VALUE.toInt()).map { it.toByte() })
    ) { String(it.toByteArray(), UTF8b) == String(it.toByteArray(), Charsets.US_ASCII) }

    @Test
    fun `encode non-special characters`() = forAll(
        stringsOf(charsInRange('\u0000', '\u007f'))
    ) { it.toByteArray(UTF8b) contentEquals it.toByteArray(Charsets.US_ASCII) }

    @Test
    fun `decode Unicode`() = forAll(unicodeStrings()) { String(it.toByteArray(), UTF8b) == it }

    @Test
    fun `encode Unicode`() = forAll(unicodeStrings()) { it.toByteArray(UTF8b) contentEquals it.toByteArray() }

    @Test
    fun `round-trip decode+encode arbitrary data`() = forAll(
        listsOf(integers(Byte.MIN_VALUE.toInt(), Byte.MAX_VALUE.toInt()).map { it.toByte() })
    ) { String(it.toByteArray(), UTF8b).toByteArray(UTF8b) contentEquals it.toByteArray() }

    private fun unicodeStrings(): Generator<String> = listsOf(
        integers(0, 0x10ffff).suchThat { it !in Char.MIN_SURROGATE.code..Char.MAX_SURROGATE.code }
    ).map { String(it.toIntArray(), 0, it.size) }
}
