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

        "supportsSSL" {
            IPv7(setOf(NormalAdress("aba"), HyperNetAddress("bab"), NormalAdress("xyz"))).supportsSSL() shouldBe true
            IPv7(setOf(NormalAdress("xyx"), HyperNetAddress("xyx"), NormalAdress("xyx"))).supportsSSL() shouldBe false
            IPv7(setOf(NormalAdress("aaa"), HyperNetAddress("kek"), NormalAdress("eke"))).supportsSSL() shouldBe true
            IPv7(setOf(NormalAdress("zazbz"), HyperNetAddress("bzb"), NormalAdress("cdb"))).supportsSSL() shouldBe true
            IPv7(setOf(NormalAdress("zazbz"), HyperNetAddress("argbzbirg"), NormalAdress("cdb"))).supportsSSL() shouldBe true
        }

        "aba's" {
            "aba".getABAs() shouldBe listOf(ABA("aba"))
            "aaa".getABAs() shouldBe emptyList<String>()
            "zazbz".getABAs() shouldBe listOf(ABA("zaz"), ABA("zbz"))
        }

        "parse" {
            parse("abba[mnop]qrst") shouldBe IPv7(setOf(NormalAdress("abba"), NormalAdress("qrst"), HyperNetAddress("mnop")))
        }

        "input examples" {
            parse("spwwppgjgfexuezrixp[rotgzyxzqxyrroafx]tkwxfiamzdjdqpftvq").supportsTLS() shouldBe true
        }

    }
}

