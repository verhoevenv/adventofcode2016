package com.github.verhoevenv.adventofcode2016.day2

import com.github.verhoevenv.adventofcode2016.day2.Movement.*
import io.kotlintest.specs.StringSpec

class Day2Spec: StringSpec() {
    init {

        val exampleInstructions = """
        |ULL
        |RRDDD
        |LURDL
        |UUUUD
        """.trimMargin()

        "example 1" {
            codeFrom(exampleInstructions) shouldBe "1985"
        }

        "nextButton" {
            nextButton(button(5), Line(listOf(U, L, L))) shouldBe button(1)
        }

        "button" {
            for(num in 1..9) {
                button(num).num shouldBe num
            }
        }

        "parse" {
            parse(exampleInstructions) shouldBe listOf(
                    Line(listOf(U, L, L)),
                    Line(listOf(R, R, D, D, D)),
                    Line(listOf(L, U, R, D, L)),
                    Line(listOf(U, U, U, U, D)))
        }
        
    }
}
