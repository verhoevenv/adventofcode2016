package com.github.verhoevenv.adventofcode2016.day6

fun mostCommonCorrect(messages: String): String {
    val listWithMaxChars = listWithCounts(messages).map { x -> x.maxBy { it.value } }.map { it!!.key }
    return listWithMaxChars.joinToString(separator = "")
}

fun leastCommonCorrect(messages: String): String {
    val listWithMaxChars = listWithCounts(messages).map { x -> x.minBy { it.value } }.map { it!!.key }
    return listWithMaxChars.joinToString(separator = "")
}

private fun listWithCounts(messages: String): List<Map<Char, Int>> {
    val splitted: List<String> = messages.split("\n").filter { s -> !s.isBlank() }
    val transposed = transpose(splitted)
    val listWithCounts: List<Map<Char, Int>> = transposed.map { s -> s.groupBy { it }.mapValues { entry -> entry.value.size } }
    return listWithCounts
}

fun transpose(strings: List<String>) : List<String>{
    val result = Array(strings[0].length, { c -> Array(strings.size, {'0'})})
    strings.forEachIndexed { i, s -> s.forEachIndexed { j, c -> result[j][i] = c } }
    return result.map { y -> y.joinToString(separator = "") }
}