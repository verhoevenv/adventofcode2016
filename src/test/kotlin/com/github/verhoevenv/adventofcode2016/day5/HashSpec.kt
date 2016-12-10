package com.github.verhoevenv.adventofcode2016.day5

import io.kotlintest.specs.StringSpec

class HashSpec: StringSpec() {
    init {
        "password" {
            password("abc") shouldBe "18f47a30"
        }
        "orderedPassword" {
            orderedPassword("abc") shouldBe "05ace8e3"
        }

        "sixthChar" {
            Hash(bytes(0x00, 0x00, 0x01)).sixthChar() shouldBe '1'
            Hash(bytes(0x00, 0x00, 0x0A)).sixthChar() shouldBe 'a'
            Hash(bytes(0x00, 0x00, 0x0F)).sixthChar() shouldBe 'f'
        }

        "seventhChar" {
            Hash(bytes(0x00, 0x00, 0x00, 0x10)).seventhChar() shouldBe '1'
            Hash(bytes(0x00, 0x00, 0x00, 0xA0)).seventhChar() shouldBe 'a'
            Hash(bytes(0x00, 0x00, 0x00, 0xF0)).seventhChar() shouldBe 'f'
        }

        "isUsableHash" {
            Hash(bytes(0x00, 0x00, 0x01)).startsWith5Zeroes() shouldBe true
            Hash(bytes(0x10, 0x00, 0x0F)).startsWith5Zeroes() shouldBe false
            Hash(bytes(0x00, 0x00, 0x10)).startsWith5Zeroes() shouldBe false
        }

    }

    private fun bytes(vararg bs: Int): ByteArray = bs.map(Int::toByte).toByteArray()
}
