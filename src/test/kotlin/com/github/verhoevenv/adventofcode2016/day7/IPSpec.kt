package com.github.verhoevenv.adventofcode2016.day7

import io.kotlintest.specs.StringSpec
import java.io.File

class IPSpec  : StringSpec() {
    init {
        "isABBA" {
            "qwer".startsWithABBA() shouldBe false
            "abba".startsWithABBA() shouldBe true
            "oxxo".startsWithABBA() shouldBe true
            "aaaa".startsWithABBA() shouldBe false
        }

        "containsABBA" {
            "abc".containsABBA() shouldBe false
            "abba".containsABBA() shouldBe true
            "ioxxoj".containsABBA() shouldBe true
        }

        "supportsTLS" {
            IPv7(setOf(NormalAdress("abba"), HyperNetAddress("qrst"))).supportsTLS() shouldBe true
            IPv7(setOf(NormalAdress("xyyx"), HyperNetAddress("bddb"))).supportsTLS() shouldBe false
            IPv7(setOf(NormalAdress("aaaa"), HyperNetAddress("qwer"))).supportsTLS() shouldBe false
            IPv7(setOf(NormalAdress("ioxxoj"), HyperNetAddress("asdfgh"), NormalAdress("zxcvbn"))).supportsTLS() shouldBe true
        }

        "parse" {
            parse("abba[mnop]qrst") shouldBe IPv7(setOf(NormalAdress("abba"), NormalAdress("qrst"), HyperNetAddress("mnop")))
        }

        "input examples" {
            parse("spwwppgjgfexuezrixp[rotgzyxzqxyrroafx]tkwxfiamzdjdqpftvq").supportsTLS() shouldBe true
        }
        
    }
}

