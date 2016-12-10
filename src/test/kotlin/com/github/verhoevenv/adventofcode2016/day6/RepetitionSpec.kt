package com.github.verhoevenv.adventofcode2016.day6

import io.kotlintest.specs.StringSpec

class RepetitionSpec : StringSpec() {
    init {

        val exampleInput = """
eedadn
drvtee
eandsr
raavrd
atevrs
tsrnev
sdttsa
rasrtv
nssdts
ntnada
svetve
tesnvt
vntsnd
vrdear
dvrsen
enarar
"""

        "most common correct" {
            mostCommonCorrect(exampleInput) shouldBe "easter"
        }

        "least common correct" {
            leastCommonCorrect(exampleInput) shouldBe "advent"
        }

        "transpose" {
            transpose(listOf("ABC", "DEF")) shouldBe listOf("AD", "BE", "CF")
        }


    }

}
