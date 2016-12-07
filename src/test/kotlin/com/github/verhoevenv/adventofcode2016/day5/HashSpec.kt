package com.github.verhoevenv.adventofcode2016.day5

import io.kotlintest.specs.StringSpec

class HashSpec: StringSpec() {
    init {
        "password" {
            password("abc") shouldBe "18f47a30"
        }

        "passwordChar" {
            passwordChar("abc3231928") shouldBe null
            passwordChar("abc3231929") shouldBe '1'
        }

        "extractPasswordChar" {
            extractPasswordChar(bytes(0x00, 0x00, 0x01)) shouldBe '1'
            extractPasswordChar(bytes(0x00, 0x00, 0x0A)) shouldBe 'a'
            extractPasswordChar(bytes(0x00, 0x00, 0x0F)) shouldBe 'f'
            extractPasswordChar(bytes(0x10, 0x00, 0x0F)) shouldBe null
            extractPasswordChar(bytes(0x00, 0x00, 0x10)) shouldBe null
        }

    }

    private fun bytes(vararg bs: Byte): ByteArray = bs
}
