package com.github.verhoevenv.adventofcode2016.day5


val md5 = java.security.MessageDigest.getInstance("MD5")

fun password(doorId: String): String {
    val numbers = generateSequence(1) { i -> i + 1 }
    return numbers.map { n -> doorId + n }.map(::passwordChar).filter { x -> x != null }.take(8).joinToString(separator = "")
}

fun passwordChar(s: String): Char? = extractPasswordChar(hash(s))

fun extractPasswordChar(ba: ByteArray): Char? {
    val zero: Byte = 0
    if(ba[0] == zero && ba[1] == zero) {
        return toAsciiChar(ba[2])
    }
    return null
}

fun toAsciiChar(byte: Byte): Char? {
    return when(byte) {
        in(0..9) -> '0' + byte.toInt()
        in(10..15) -> 'a' + byte.toInt() - 10
        else -> null
    }
}

fun hash(s: String): ByteArray {
    val result = md5.digest(s.toByteArray())
    md5.reset()
    return result
}