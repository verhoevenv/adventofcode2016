package com.github.verhoevenv.adventofcode2016.day2

import com.github.verhoevenv.adventofcode2016.util.unfold

interface Keypad {
    fun buttonAt(x: Int, y: Int) : Button?
    fun buttonWithLabel(label: String) : Button

    fun code(instructions: String) : String {
        val allLines = parse(instructions)
        val buttons = unfold(Pair(buttonWithLabel("5"), allLines)) { state ->
            val (button, leftoverLines) = state
            if(leftoverLines.isEmpty()) null
            else {
                val nextLine = leftoverLines.first()
                val newButton = nextButton(button, nextLine)
                Pair(Pair(newButton, leftoverLines.drop(1)), newButton.label.toString())
            }
        }
        return buttons.toList().joinToString(separator = "")
    }

    fun nextButton(button: Button, line: Line) : Button =
            line.movements.fold(button) {
                but, mov -> move(but, mov)
            }

    fun move(b: Button, m: Movement): Button {
        val x = b.x
        val y = b.y
        return when(m) {
            Movement.U -> buttonAt(x, y+1) ?: b
            Movement.D -> buttonAt(x, y-1) ?: b
            Movement.L -> buttonAt(x - 1, y) ?: b
            Movement.R -> buttonAt(x + 1, y) ?: b
        }
    }
}