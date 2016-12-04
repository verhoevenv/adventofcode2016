package com.github.verhoevenv.adventofcode2016.day2

import com.github.verhoevenv.adventofcode2016.util.unfold

fun codeFrom(instructions: String) : String {
    val allLines = parse(instructions)
    val buttons = unfold(Pair(button(5), allLines)) { state ->
        val (button, leftoverLines) = state
        if(leftoverLines.isEmpty()) null
        else {
            val nextLine = leftoverLines.first()
            val newButton = nextButton(button, nextLine)
            Pair(Pair(newButton, leftoverLines.drop(1)), newButton.num.toString())
        }
    }
    return buttons.toList().joinToString(separator = "")
}

fun nextButton(button: Button, line: Line) : Button =
    line.movements.fold(button, ::move)


fun move(b: Button, m: Movement): Button {
    fun attemptMoveTo(x: Int, y: Int) : Button? =
        when {
            x < 0 -> null
            x > 2 -> null
            y < 0 -> null
            y > 2 -> null
            else -> Button(x(x), y(y))
        }

    val (x, y) = b
    return when(m) {
        Movement.U -> attemptMoveTo(x.asInt(), y.asInt()+1) ?: b
        Movement.D -> attemptMoveTo(x.asInt(), y.asInt()-1) ?: b
        Movement.L -> attemptMoveTo(x.asInt() - 1, y.asInt()) ?: b
        Movement.R -> attemptMoveTo(x.asInt() + 1, y.asInt()) ?: b
    }
}


fun x(v: Int): KeyPadRange = if(v >= 0 && v < 3) KeyPadRange(v) else throw IndexOutOfBoundsException("Out of bounds: $v")
fun y(v: Int): KeyPadRange = if(v >= 0 && v < 3) KeyPadRange(v) else throw IndexOutOfBoundsException("Out of bounds: $v")
data class KeyPadRange(val v: Int) {
    fun asInt() = v
}

fun button(num: Int) : Button =
    when(num) {
        1 -> Button(x(0), y(2))
        2 -> Button(x(1), y(2))
        3 -> Button(x(2), y(2))
        4 -> Button(x(0), y(1))
        5 -> Button(x(1), y(1))
        6 -> Button(x(2), y(1))
        7 -> Button(x(0), y(0))
        8 -> Button(x(1), y(0))
        9 -> Button(x(2), y(0))
        else -> throw IllegalArgumentException("No button for $num")
    }

data class Button(val x: KeyPadRange, val y: KeyPadRange) {
    val num : Int = x.v + (2 - y.v) * 3 + 1

    override fun toString(): String {
        return "Button($num, x=${x.v}, y=${y.v})"
    }
}


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