package com.github.verhoevenv.adventofcode2016.day2


fun parse(instructions: String) : List<Line> =
    instructions.split("\n").map(::parseLine)

fun parseLine(line: String) : Line =
    Line(line.map(::fromChar))

data class Line(val movements: List<Movement>)

private fun fromChar(it: Char): Movement {
    return when (it) {
        'U' -> Movement.U
        'L' -> Movement.L
        'R' -> Movement.R
        'D' -> Movement.D
        else -> throw IllegalArgumentException("No such movement: $it")
    }
}

enum class Movement {
    U,
    L,
    R,
    D
}