package com.github.ephemient.utf8b.spi

import com.github.ephemient.utf8b.UTF8b
import java.nio.charset.Charset
import java.util.stream.Stream
import kotlin.streams.asStream
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class UTF8bProviderTest {
    @ParameterizedTest
    @MethodSource("provideCharsetNames")
    fun `provider is registered`(name: String) {
        assertEquals(UTF8b, Charset.forName(name))
    }

    companion object {
        @JvmStatic
        fun provideCharsetNames(): Stream<String> = sequence<String> {
            for (u in "Uu") {
                for (t in "Tt") {
                    for (f in "Ff") {
                        for (b in "bB" ) {
                            yield("$u$t$f-8$b")
                            yield("$u$t${f}8$b")
                        }
                    }
                }
            }
        }.asStream()
    }
}
