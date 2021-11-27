package com.github.ephemient.utf8b.spi

import com.google.auto.service.AutoService
import com.github.ephemient.utf8b.UTF8b
import java.nio.charset.Charset
import java.nio.charset.spi.CharsetProvider
import java.util.Collections

@AutoService(CharsetProvider::class)
class UTF8bProvider : CharsetProvider() {
    override fun charsets(): Iterator<Charset> = Collections.singletonList(UTF8b).iterator()

    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
    override fun charsetForName(charsetName: String): Charset? = when {
        charsetName.length == 6 && (charsetName as java.lang.String).equalsIgnoreCase("UTF-8b") -> UTF8b
        charsetName.length == 5 && (charsetName as java.lang.String).equalsIgnoreCase("UTF8b") -> UTF8b
        else -> null
    }
}
