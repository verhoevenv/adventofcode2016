package com.github.verhoevenv.adventofcode2016.day11

import io.kotlintest.specs.StringSpec

class ElevatorSpec : StringSpec() {
    init {
        "example" {
            val input = """
            |The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.
            |The second floor contains a hydrogen generator.
            |The third floor contains a lithium generator.
            |The fourth floor contains nothing relevant.
            """.trimMargin()

            val beginState = parse(input)

            val plan = searchShortestPlan(beginState)

            plan!!.size shouldBe 11
        }

        "parse" {
            val input = """
            |The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.
            |The second floor contains a hydrogen generator.
            |The third floor contains a lithium generator.
            |The fourth floor contains nothing relevant.
            """.trimMargin()

            parse(input) shouldBe FloorState(elevator = 1, floors = mapOf(
                    1 to setOf(Microchip("hydrogen"), Microchip("lithium")),
                    2 to setOf(Generator("hydrogen")),
                    3 to setOf(Generator("lithium")),
                    4 to emptySet()
            ))
        }

        "state is safe" {
            setOf(Microchip("H")).isSafe() shouldBe true
            setOf(Microchip("H"), Generator("H")).isSafe() shouldBe true
            setOf(Microchip("H"), Generator("X")).isSafe() shouldBe false
            setOf(Microchip("H"), Generator("H"), Microchip("X")).isSafe() shouldBe false
            setOf(Microchip("H"), Generator("H"), Generator("X")).isSafe() shouldBe true
        }

        "generate valid steps" {
            FloorState(elevator = 1, floors = mapOf(1 to setOf(Microchip("H")), 2 to setOf(Generator("H")))).generateValidSteps() shouldBe
                    listOf(Pair(MoveUp1(Microchip("H")), FloorState(elevator = 2, floors = mapOf(1 to emptySet(), 2 to setOf(Microchip("H"), Generator("H"))))))
        }

        "subsetsOfSize1" {
            setOf("A", "B").subsetsOfSize1() shouldBe setOf("A", "B")
        }

        "subsetsOfSize2" {
            setOf("A", "B", "C").subsetsOfSize2() shouldBe setOf(setOf("A", "B"), setOf("B", "C"), setOf("A", "C"))
        }

        "move on maps" {
            mapOf(1 to setOf("A", "B"), 2 to emptySet()).move("A", from = 1, to = 2) shouldBe mapOf(1 to setOf("B"), 2 to setOf("A"))
        }

        "move on FloorState" {
            val aState = FloorState(elevator = 2, floors = mapOf(1 to emptySet(), 2 to setOf(Microchip("H"), Microchip("A")), 3 to setOf(Generator("H"))))
            aState.move(MoveUp1(Microchip("H"))) shouldBe FloorState(elevator = 3, floors = mapOf(1 to emptySet(), 2 to setOf(Microchip("A")), 3 to setOf(Generator("H"), Microchip("H"))))
            aState.move(MoveDown1(Microchip("H"))) shouldBe FloorState(elevator = 1, floors = mapOf(1 to setOf(Microchip("H")), 2 to setOf(Microchip("A")), 3 to setOf(Generator("H"))))
            aState.move(MoveUp2(Microchip("H"), Microchip("A"))) shouldBe FloorState(elevator = 3, floors = mapOf(1 to emptySet(), 2 to emptySet(), 3 to setOf(Generator("H"), Microchip("H"), Microchip("A"))))
            aState.move(MoveDown2(Microchip("H"), Microchip("A"))) shouldBe FloorState(elevator = 1, floors = mapOf(1 to setOf(Microchip("H"), Microchip("A")), 2 to emptySet(), 3 to setOf(Generator("H"))))
        }


    }
}
