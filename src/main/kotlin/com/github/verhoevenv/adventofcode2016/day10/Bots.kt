package com.github.verhoevenv.adventofcode2016.day10

fun parse(input: String): BotNetwork {
    val botNetwork = BotNetwork()
    val inputregex = """value (\d+) goes to bot (\d+)""".toRegex()
    val ruleRegex = """bot (\d+) gives low to (bot|output) (\d+) and high to (bot|output) (\d+)""".toRegex()

    input.split("\n").forEach {
        when {
            it.matches(inputregex) -> {
                val (v, botNb) = inputregex.matchEntire(it)!!.destructured
                botNetwork.bot(botNb.toInt()).giveChip(v.toInt())
            }
            it.matches(ruleRegex) -> {
                val (botNb, directionlow, low, directionhigh, high) = ruleRegex.matchEntire(it)!!.destructured
                val highTarget = botNetwork.findTarget(directionhigh, high.toInt())
                val lowTarget = botNetwork.findTarget(directionlow, low.toInt())
                botNetwork.bot(botNb.toInt()).rule = Give(highto = highTarget, lowto = lowTarget)
            }
            else -> throw IllegalArgumentException("unknown command $it")
        }
    }

    return botNetwork
}

class BotNetwork() {

    var bots = mutableMapOf<Int, Bot>()
    var outputs = mutableMapOf<Int, Output>()

    fun responsibleForComparing(val1: Int, val2: Int): Int {
        var bot = findBotWith2Chips()
        while(bot != null && !(bot.chips.contains(val1) && bot.chips.contains(val2))) {
            bot.applyRule()
            bot = findBotWith2Chips()
        }
        if(bot == null) throw IllegalStateException("No bot compared $val1 and $val2")
        return bot.number
    }


    fun keepRunning() {
        var bot = findBotWith2Chips()
        while(bot != null) {
            bot.applyRule()
            bot = findBotWith2Chips()
        }
    }

    private fun findBotWith2Chips() : Bot? {
        return bots.values.find {bot -> bot.chips.size == 2}
    }

    fun bot(i: Int): Bot {
        return bots.getOrPut(i) {Bot(i)}
    }

    fun output(i: Int): Output {
        return outputs.getOrPut(i) { Output(i) }
    }

    fun findTarget(target: String, nb: Int) : Target {
        return when(target) {
            "bot" -> bot(nb)
            "output" -> output(nb)
            else -> throw IllegalArgumentException("Can't handle $target")
        }
    }

    fun multiplyFromOutputs(vararg i: Int): Int {
        return i.map { nb -> output(nb).chips.first() }.reduce { a, b -> a*b }
    }


}

interface Target {
    fun giveChip(v: Int)
}

data class Bot(val number: Int, var chips: MutableList<Int> = mutableListOf(), var rule: Give? = null) : Target {
    override fun giveChip(v: Int) {
        if(chips.size >= 2) throw IllegalStateException("can't hold more than 2 chips")
        chips.add(v)
    }

    fun applyRule() {
        if(rule != null && chips.size == 2) {
            val (lowChip, highChip) = chips.sorted()
            rule!!.lowto.giveChip(lowChip)
            rule!!.highto.giveChip(highChip)
            chips.clear()
        }
    }

}


data class Output(val number: Int) : Target {
    var chips: MutableList<Int> = mutableListOf()

    override fun giveChip(v: Int) {
        chips.add(v)
    }
}

data class Give(val lowto: Target, val highto: Target)