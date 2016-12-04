package com.github.verhoevenv.adventofcode2016.day2

import com.github.verhoevenv.adventofcode2016.day2.Movement.*
import io.kotlintest.specs.StringSpec

class ListOfListKeypadSpec : StringSpec() {
    init {

        val part1Keypad = ListOfListKeypad(buttons = listOf(
                listOf("1", "2", "3"),
                listOf("4", "5", "6"),
                listOf("7", "8", "9")
        ))

        val exampleInstructions = """
        |ULL
        |RRDDD
        |LURDL
        |UUUUD
        """.trimMargin()

        "example 1" {
            part1Keypad.code(exampleInstructions) shouldBe "1985"
        }

        "nextButton" {
            part1Keypad.nextButton(part1Keypad.buttonWithLabel("5"), Line(listOf(U, L, L))) shouldBe part1Keypad.buttonWithLabel("1")
        }

        "buttonAt" {
            part1Keypad.buttonAt(0, 0)!!.label shouldBe "7"
        }

        "button" {
            for(num in 1..9) {
                part1Keypad.buttonWithLabel(num.toString()).label shouldBe num.toString()
            }
        }


        val part2Keypad = ListOfListKeypad(buttons = listOf(
                listOf(null, null,  "1", null, null),
                listOf(null,  "2",  "3",  "4", null),
                listOf( "5",  "6",  "7",  "8",  "9"),
                listOf(null,  "A",  "B",  "C", null),
                listOf(null, null,  "D", null, null)
        ))

        "part 2 example" {
            part2Keypad.code(exampleInstructions) shouldBe "5DB3"
        }

    }
}
