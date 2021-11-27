//[utf-8b](../../../index.md)/[com.github.ephemient.utf8b.spi](../index.md)/[UTF8bProvider](index.md)

# UTF8bProvider

[jvm]\
@AutoService(value = [[CharsetProvider::class](https://docs.oracle.com/javase/8/docs/api/java/nio/charset/spi/CharsetProvider.html)])

class [UTF8bProvider](index.md) : [CharsetProvider](https://docs.oracle.com/javase/8/docs/api/java/nio/charset/spi/CharsetProvider.html)

## Functions

| Name | Summary |
|---|---|
| [charsetForName](charset-for-name.md) | [jvm]<br>open override fun [charsetForName](charset-for-name.md)(charsetName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Charset](https://docs.oracle.com/javase/8/docs/api/java/nio/charset/Charset.html)? |
| [charsets](charsets.md) | [jvm]<br>open override fun [charsets](charsets.md)(): [Iterator](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterator/index.html)&lt;[Charset](https://docs.oracle.com/javase/8/docs/api/java/nio/charset/Charset.html)&gt; |
