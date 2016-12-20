package com.github.verhoevenv.adventofcode2016.day10

import io.kotlintest.specs.StringSpec

class BotsSpec : StringSpec() {
    init {
        "example" {
            val input = """
            |value 5 goes to bot 2
            |bot 2 gives low to bot 1 and high to bot 0
            |value 3 goes to bot 1
            |bot 1 gives low to output 1 and high to bot 0
            |bot 0 gives low to output 2 and high to output 0
            |value 2 goes to bot 2
            """.trimMargin()
            val botnet = parse(input)

            botnet.responsibleForComparing(5, 2) shouldBe 2
        }

        "example part 2" {
            val input = """
            |value 5 goes to bot 2
            |bot 2 gives low to bot 1 and high to bot 0
            |value 3 goes to bot 1
            |bot 1 gives low to output 1 and high to bot 0
            |bot 0 gives low to output 2 and high to output 0
            |value 2 goes to bot 2
            """.trimMargin()
            val botnet = parse(input)

            botnet.keepRunning()

            botnet.multiplyFromOutputs(0, 1, 2) shouldBe 30
        }

        "parse input" {
            val input = """
            |value 1 goes to bot 0
            """.trimMargin()
            val botnet = parse(input)

            botnet.bot(0).chips shouldBe listOf(1)
        }

        "parse rule" {
            val input = """
            |bot 2 gives low to output 1 and high to bot 0
            """.trimMargin()
            val botnet = parse(input)

            botnet.bot(2).rule shouldBe Give(lowto=Output(1),highto=Bot(0))
        }

        "apply rule" {
            val bot = Bot(0)
            val receiverBot = Bot(1)
            val receiverOutput = Output(1)
            bot.rule = Give(lowto = receiverOutput, highto = receiverBot)
            bot.giveChip(2)
            bot.giveChip(3)

            bot.applyRule()

            bot.chips should beEmpty()
            receiverBot.chips should contain(3)
            receiverOutput.chips should contain(2)
        }

    }
}