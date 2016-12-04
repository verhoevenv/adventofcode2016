package com.github.verhoevenv.adventofcode2016.day2

import io.kotlintest.specs.StringSpec

class MovementSpec: StringSpec() {
    init {

        val exampleInstructions = """
        |ULL
        |RRDDD
        |LURDL
        |UUUUD
        """.trimMargin()


        "parse" {
            parse(exampleInstructions) shouldBe listOf(
                    Line(listOf(Movement.U, Movement.L, Movement.L)),
                    Line(listOf(Movement.R, Movement.R, Movement.D, Movement.D, Movement.D)),
                    Line(listOf(Movement.L, Movement.U, Movement.R, Movement.D, Movement.L)),
                    Line(listOf(Movement.U, Movement.U, Movement.U, Movement.U, Movement.D)))
        }
    }
}