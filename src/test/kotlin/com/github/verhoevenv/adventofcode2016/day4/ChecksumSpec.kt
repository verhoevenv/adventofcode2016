package com.github.verhoevenv.adventofcode2016.day4

import io.kotlintest.specs.StringSpec

class ChecksumSpec: StringSpec() {
    init {
        "parse" {
            parse("aaaaa-bbb-z-y-x-123[abxyz]") shouldBe Room("aaaaa-bbb-z-y-x", 123, "abxyz")
            parse("a-b-c-d-e-f-g-h-987[abcde]") shouldBe Room("a-b-c-d-e-f-g-h", 987, "abcde")
            parse("not-a-real-room-404[oarel]") shouldBe Room("not-a-real-room", 404, "oarel")
            parse("totally-real-room-200[decoy]") shouldBe Room("totally-real-room", 200, "decoy")
        }

        "checksum" {
            checksum("aaaaa-bbb-z-y-x") shouldBe "abxyz"
        }

        "countOccurences" {
            countOccurences("aaaaabbbzyx") shouldBe mapOf(Pair('a', 5), Pair('b', 3), Pair('z', 1), Pair('y', 1), Pair('x', 1))
        }

        "isRealRoom" {
            Room("aaaaa-bbb-z-y-x", 123, "abxyz").isRealRoom shouldBe true
            Room("a-b-c-d-e-f-g-h", 987, "abcde").isRealRoom shouldBe true
            Room("not-a-real-room", 404, "oarel").isRealRoom shouldBe true
            Room("totally-real-room", 200, "decoy").isRealRoom shouldBe false
        }
        
    }
}
