package com.github.verhoevenv.adventofcode2016.day1

import com.github.verhoevenv.adventofcode2016.day1.Rotation.LEFT
import com.github.verhoevenv.adventofcode2016.day1.Rotation.RIGHT
import io.kotlintest.specs.StringSpec

class Day1Spec: StringSpec() {
    init {
        "distance example 1" {
            distance("R2, L3") shouldBe 5
        }

        "distance example 2" {
            distance("R2, R2, R2") shouldBe 2
        }

        "distance example 3" {
            distance("R5, L5, R5, R3") shouldBe 12
        }


        "distance to first visited twice example" {
            distanceToFirstVisitedTwice("R8, R4, R4, R8") shouldBe 4
        }

        "firstVisitedTwice for distance to first visited twice example" {
            firstVisitedTwice(listOf(Step(RIGHT, 8), Step(RIGHT, 4), Step(RIGHT, 4), Step(RIGHT, 8))) shouldBe Location(4, 0)
        }

        "visitedLocations" {
            visitedLocations(listOf(Step(RIGHT, 2))) shouldBe listOf(Location(1, 0), Location(2, 0))
        }

        "parseOne should create Step objects" {
            parseOne("R2") shouldBe Step(RIGHT, 2)
        }

        "parseOne should work with multi-digit numbers" {
            parseOne("R10") shouldBe Step(RIGHT, 10)
        }

        "parse should return a list of Step objects" {
            parse("R2, L3") shouldBe listOf(Step(RIGHT, 2), Step(LEFT, 3))
        }

        "walk in example 1" {
            walk(listOf(Step(RIGHT, 2), Step(LEFT, 3))) shouldBe Position(Location(2, 3), Direction.N)
        }

        "walk in example 2" {
            walk(listOf(Step(RIGHT, 2), Step(RIGHT, 2), Step(RIGHT, 2))) shouldBe Position(Location(0, -2), Direction.W)
        }

        "walk in example 3" {
            walk(listOf(Step(RIGHT, 5), Step(LEFT, 5), Step(RIGHT, 5), Step(RIGHT, 3))) shouldBe Position(Location(10, 2), Direction.S)
        }
    }
}
