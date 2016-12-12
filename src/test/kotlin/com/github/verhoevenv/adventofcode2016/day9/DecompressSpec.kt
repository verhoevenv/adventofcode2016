package com.github.verhoevenv.adventofcode2016.day9

import io.kotlintest.specs.StringSpec
import java.math.BigInteger

class DecompressSpec : StringSpec() {
    init {

        "parse" {
            tokenize("ADVENT") shouldBe listOf(Text("A"),Text("D"),Text("V"),Text("E"),Text("N"),Text("T"))
            tokenize("A(1x5)BC") shouldBe listOf(Text("A"), Marker(1, 5), Text("B"),Text("C"))
            tokenize("(10x100)ABCDEFGHIJ") shouldBe listOf(Marker(10, 100), Text("A"),Text("B"),Text("C"),Text("D"),Text("E"),Text("F"),Text("G"),Text("H"),Text("I"),Text("J"))
            tokenize("X(8x2)(3x3)ABCY") shouldBe listOf(Text("X"), Marker(8, 2), Marker(3, 3), Text("A"),Text("B"),Text("C"),Text("Y"))
        }

        "without markers" {
            decompress("ADVENT").decompressedString() shouldBe "ADVENT"
            decompress("ADVENT").decompressedLength() shouldBe bi(6)
        }

        "marker of length 1" {
            decompress("A(1x5)BC").decompressedString() shouldBe "ABBBBBC"
            decompress("A(1x5)BC").decompressedLength() shouldBe bi(7)
        }

        "marker of length 3" {
            decompress("(3x3)XYZ").decompressedString() shouldBe "XYZXYZXYZ"
            decompress("(3x3)XYZ").decompressedLength() shouldBe bi(9)
        }

        "multiple markers" {
            decompress("A(2x2)BCD(2x2)EFG").decompressedString() shouldBe "ABCBCDEFEFG"
            decompress("A(2x2)BCD(2x2)EFG").decompressedLength() shouldBe bi(11)
        }

        "markers bigger that 10" {
            decompress("(15x100)ABCDEFGHIJKLMNO").decompressedString() shouldBe "ABCDEFGHIJKLMNO".repeat(100)
            decompress("(15x100)ABCDEFGHIJKLMNO").decompressedLength() shouldBe bi(1500)
        }

        "markers needs to consume multiple markers" {
            decompress("(11x2)(6x3)(1x2)A").decompressedString() shouldBe "(6x3)(1x2)A(6x3)(1x2)A"
            decompress("(11x2)(6x3)(1x2)A").decompressedLength() shouldBe bi(22)
        }

        "marker as data" {
            decompress("(6x1)(1x3)A").decompressedString() shouldBe "(1x3)A"
            decompress("(6x1)(1x3)A").decompressedLength() shouldBe bi(6)
        }

        "marker + characters as data" {
            decompress("X(8x2)(3x3)ABCY").decompressedString() shouldBe "X(3x3)ABC(3x3)ABCY"
            decompress("X(8x2)(3x3)ABCY").decompressedLength() shouldBe bi(18)
        }

        "decompressV2" {
            decompressV2("(3x3)XYZ").decompressedString() shouldBe "XYZXYZXYZ"
            decompressV2("X(8x2)(3x3)ABCY").decompressedString() shouldBe "XABCABCABCABCABCABCY"
            decompressV2("(27x12)(20x12)(13x14)(7x10)(1x12)A").decompressedLength() shouldBe bi(241920)
            decompressV2("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN").decompressedLength() shouldBe bi(445)
        }

    }

    private fun bi(i: Int): BigInteger = BigInteger.valueOf(i.toLong())
}