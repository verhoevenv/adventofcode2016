package com.github.verhoevenv.adventofcode2016.day8

import io.kotlintest.specs.StringSpec

class DisplaySpec : StringSpec() {
    init {
        "display starts off" {
            Display().nbLitPixels() shouldBe 0
        }

        "rect lights pixels" {
            Display().operate(Rect(1, 1)).nbLitPixels() shouldBe 1
        }

        "rect lights multiple pixels" {
            val display = Display().operate(Rect(3, 2))

            display.nbLitPixels() shouldBe 6
            display.isLit(Pixel(0,0)) shouldBe true
            display.isLit(Pixel(2,1)) shouldBe true
            display.isLit(Pixel(3,1)) shouldBe false
            display.isLit(Pixel(2,2)) shouldBe false
        }

        "rect doesn't turn off pixels" {
            Display()
                    .operate(Rect(1, 1))
                    .operate(Rect(1, 1))
                    .nbLitPixels() shouldBe 1
        }

        "rotate row moves lit pixels in row" {
            val display = Display()
                    .operate(Rect(1, 2))
                    .operate(RotateRow(1, 1))

            display.nbLitPixels() shouldBe 2
            display.isLit(Pixel(0, 1)) shouldBe false
            display.isLit(Pixel(1, 1)) shouldBe true
            display.isLit(Pixel(2, 1)) shouldBe false
        }

        "rotate row makes pixels that fall off the right appear at the left" {
            val display = Display(width = 3)
                    .operate(Rect(2, 2))
                    .operate(RotateRow(1, 2))

            display.nbLitPixels() shouldBe 4
            display.isLit(Pixel(0, 1)) shouldBe true
            display.isLit(Pixel(1, 1)) shouldBe false
            display.isLit(Pixel(2, 1)) shouldBe true
        }

        "rotate column moves lit pixels in column" {
            val display = Display()
                    .operate(Rect(3, 2))
                    .operate(RotateCol(1, 1))

            display.nbLitPixels() shouldBe 6
            display.isLit(Pixel(1, 0)) shouldBe false
            display.isLit(Pixel(1, 1)) shouldBe true
            display.isLit(Pixel(1, 2)) shouldBe true
            display.isLit(Pixel(1, 3)) shouldBe false
        }

        "rotate column makes pixels that fall off the bottom appear at the top" {
            val display = Display(height = 3)
                    .operate(Rect(3, 2))
                    .operate(RotateCol(1, 2))

            display.nbLitPixels() shouldBe 6
            display.isLit(Pixel(1, 0)) shouldBe true
            display.isLit(Pixel(1, 1)) shouldBe false
            display.isLit(Pixel(1, 2)) shouldBe true
        }

        "parse" {
            parse("rect 1x1") shouldBe Rect(1, 1)
            parse("rotate row y=1 by 1") shouldBe RotateRow(1, 1)
            parse("rotate column x=1 by 1") shouldBe RotateCol(1, 1)
        }

        "example" {
            val display = Display(width=7, height=3)

            display.operate(parse("rect 3x2"))

            display.show() shouldBe
                    """
                    |###....
                    |###....
                    |.......
                    """.trimMargin()

            display.operate(parse("rotate column x=1 by 1"))

            display.show() shouldBe
                    """
                    |#.#....
                    |###....
                    |.#.....
                    """.trimMargin()

            display.operate(parse("rotate row y=0 by 4"))

            display.show() shouldBe
                    """
                    |....#.#
                    |###....
                    |.#.....
                    """.trimMargin()

            display.operate(parse("rotate column x=1 by 1"))

            display.show() shouldBe
                    """
                    |.#..#.#
                    |#.#....
                    |.#.....
                    """.trimMargin()
        }

    }

}