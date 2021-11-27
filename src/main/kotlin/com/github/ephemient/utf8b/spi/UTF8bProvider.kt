package com.github.ephemient.utf8b.spi

import com.google.auto.service.AutoService
import com.github.ephemient.utf8b.UTF8b
import java.nio.charset.Charset
import java.nio.charset.spi.CharsetProvider

@AutoService(CharsetProvider::class)
class UTF8bProvider : CharsetProvider() {
    override fun charsets(): Iterator<Charset> = iterator { yield(UTF8b) }
    override fun charsetForName(charsetName: String?): Charset? =
        if (charsetName.equals("UTF-8b", ignoreCase = true) ||
            charsetName.equals("UTF8b", ignoreCase = true)
        ) UTF8b else null
}
