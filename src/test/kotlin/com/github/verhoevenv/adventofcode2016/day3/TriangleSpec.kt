package com.github.verhoevenv.adventofcode2016.day3

import com.github.verhoevenv.adventofcode2016.day1.Rotation.LEFT
import com.github.verhoevenv.adventofcode2016.day1.Rotation.RIGHT
import io.kotlintest.specs.StringSpec

class TriangleSpec: StringSpec() {
    init {
        "possibleTriangle" {
            isPossibleTriangle(OrderedNumbers(5, 10, 25)) shouldBe false
        }

        "impossibleTriangle" {
            isPossibleTriangle(OrderedNumbers(5, 10, 12)) shouldBe true
        }

        "triangle with a + b = c is impossible" {
            isPossibleTriangle(OrderedNumbers(5, 10, 15)) shouldBe false
        }

        "line from input" {
            isPossibleTriangle("  775  785  361") shouldBe true
        }


        val multipleTriangles = """
  2  3  4
  2  3  4
  1  1  6
  2  3  4
  2  3  4
  1  1  6
"""

        "count possible triangles" {
            countTriangles(multipleTriangles) shouldBe 4
        }

        "transpose" {
            transpose(listOf(
                    Sides(1, 2, 3),
                    Sides(4, 5, 6),
                    Sides(7, 8, 9)
                    )
            ) shouldBe listOf(
                    Sides(1, 4, 7),
                    Sides(2, 5, 8),
                    Sides(3, 6, 9)
            )
        }

        "count possible triangles in column order" {
            countColumnTriangles(multipleTriangles) shouldBe 6
        }

    }
}
