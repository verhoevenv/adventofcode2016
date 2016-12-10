package com.github.verhoevenv.adventofcode2016.day5


val md5 = java.security.MessageDigest.getInstance("MD5")

fun password(doorId: String): String {
    return hashes(doorId).filter(Hash::startsWith5Zeroes).map(Hash::sixthChar).take(8).joinToString(separator = "")
}

private fun hashes(doorId: String): Sequence<Hash> {
    return generateSequence(1) { i -> i + 1 }.map { n -> doorId + n }.map(::hash)
}

fun orderedPassword(doorId: String): String {
    return hashes(doorId)
            .filter(Hash::startsWith5Zeroes)
            .filter { x -> x.sixthChar() in '0'..'7' }
            .distinctBy(Hash::sixthChar)
            .take(8)
            .sortedBy(Hash::sixthChar)
            .map(Hash::seventhChar)
            .joinToString(separator = "")
}

data class Hash(val ba: ByteArray) {
    fun startsWith5Zeroes(): Boolean {
        val zero: Byte = 0
        return ba[0] == zero && ba[1] == zero && (ba[2].toInt() and 0xF0) == 0
    }

    fun sixthChar(): Char {
        return toAsciiChar((ba[2].toInt() and 0x0F).toByte())
    }

    fun seventhChar(): Char {
        return toAsciiChar((ba[3].toInt() and 0xF0).shr(4).toByte())
    }

}


fun toAsciiChar(byte: Byte): Char {
    return when(byte) {
        in(0..9) -> '0' + byte.toInt()
        in(10..15) -> 'a' + byte.toInt() - 10
        else -> throw IllegalArgumentException("argument doesn't fit in hexadec char: $byte")
    }
}

fun hash(s: String): Hash {
    val result = md5.digest(s.toByteArray())
    md5.reset()
    return Hash(result)
}