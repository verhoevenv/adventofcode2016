package com.github.verhoevenv.adventofcode2016.day4

import java.util.*
import java.util.Comparator.comparing

fun sumOfSectorsOfRealRooms(roomStrings: List<String>): Int {
    return roomStrings.map(::parse).filter(Room::isRealRoom).map(Room::sectorId).sum()
}

data class Room(val name: String, val sectorId: Int, val checksum: String) {
    val isRealRoom = checksum(name) == checksum
    val decrypt = name.map(curriedDecrypt(sectorId)).joinToString(separator = "")
}

fun parse(room: String): Room {
    val match = """([-a-z]+)-(\d+)\[([a-z]+)\]""".toRegex().matchEntire(room)
    when (match) {
        null -> throw IllegalArgumentException("$room doesn't match")
        else -> {
            val (name, sectorId, checkSum) = match.destructured
            return Room(name, sectorId.toInt(), checkSum)
        }
    }
}

fun checksum(name: String): String {
    val nameWithoutDashes = name.filter { x -> x != '-' }
    val counts = countOccurences(nameWithoutDashes)
    val fiveMostCommon = counts.toList().sortedWith(mostCommonLetter.reversed()).take(5)
    return fiveMostCommon.map { entry -> entry.first }.joinToString(separator = "")
}

private val mostCommonLetter: Comparator<Pair<Char, Int>> =
        comparing { pair: Pair<Char, Int> -> pair.second }
                .thenComparing(comparing { pair: Pair<Char, Int> -> pair.first }.reversed() )


fun countOccurences(name: String): Map<Char, Int> {
    return name.groupBy { x -> x }.mapValues { entry -> entry.value.size }
}

fun curriedDecrypt(sectorId: Int): (Char) -> Char = { c -> decryptChar(sectorId, c) }

fun decryptChar(sectorId: Int, char: Char): Char = when(char) {
    in 'a'..'z' -> rotateForward(sectorId, char)
    '-' -> ' '
    else -> throw IllegalArgumentException("Unknown character: $char")
}

fun rotateForward(number: Int, char: Char): Char = ((char - 'a' + number) % 26 + 'a'.toInt()).toChar()