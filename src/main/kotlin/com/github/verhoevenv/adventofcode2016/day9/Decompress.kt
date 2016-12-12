package com.github.verhoevenv.adventofcode2016.day9

import java.math.BigInteger
import kotlin.reflect.KFunction2

fun decompress(input: String) : Decompressor {
    return Decompressor.buildDecompressorV1(input)
}
fun decompressV2(input: String) : Decompressor {
    return Decompressor.buildDecompressorV2(input)
}

class Decompressor(val instructions: Instruction) {

    fun decompressedString(): String {
        return instructions.doIt()
    }

    fun decompressedLength(): BigInteger {
        return instructions.length()
    }

    companion object {
        fun buildDecompressorV1(input: String): Decompressor {
            return Decompressor(parse(tokenize(input), ::slurpText))
        }

        fun buildDecompressorV2(input: String): Decompressor {
            return Decompressor(parse(tokenize(input), ::recursiveParse))
        }

    }

}


fun tokenize(input: String) : List<CompressionToken> {
    val markerRegex = """\((\d+)x(\d+)\)""".toRegex()

    val find: MatchResult? = markerRegex.find(input)
    when(find) {
        null -> return input.map { Text(it.toString()) }
        else -> {
            val (numChars, repeats) = find.destructured

            val before = input.substring(0..find.range.first - 1)
            val after = input.substring(find.range.last + 1)

            return before.map { Text(it.toString()) } + Marker(numChars.toInt(), repeats.toInt()) + tokenize(after)
        }
    }
}
interface CompressionToken {
    fun asText(): Text
}
data class Text(val s: String) : CompressionToken {
    override fun asText(): Text = this
}
data class Marker(val amountReferenced: Int, val repeats: Int) : CompressionToken {
    override fun asText() = Text("(${amountReferenced}x$repeats)")
}


fun parse(list: List<CompressionToken>, onMarker: KFunction2<Int, List<CompressionToken>, Pair<Instruction, List<CompressionToken>>>) : Instruction {
    if(list.isEmpty()) return NoOp()
    val head = list[0]
    val tail = list.drop(1)
    return when(head) {
        is Text -> DoAll(listOf(DumbString(head.s)) + parse(tail, onMarker))
        is Marker -> {
            val (instuction, newTail) = onMarker(head.amountReferenced, tail)
            DoAll(listOf(Repeat(instuction, head.repeats)) + parse(newTail, onMarker))
        }
        else -> throw IllegalStateException("What is this $head")
    }
}

interface Instruction {
    fun doIt() : String
    fun length() : BigInteger
}
data class DumbString(val s: String) : Instruction {
    override fun doIt(): String = s
    override fun length(): BigInteger = BigInteger.valueOf(s.length.toLong())
}
data class Repeat(val i: Instruction, val num: Int) : Instruction {
    override fun doIt(): String = i.doIt().repeat(num)
    override fun length(): BigInteger = i.length().times(BigInteger.valueOf(num.toLong()))
}
data class DoAll(val i: List<Instruction>) : Instruction {
    override fun doIt(): String = i.map(Instruction::doIt).joinToString(separator = "")
    override fun length(): BigInteger = i.map(Instruction::length).reduce(BigInteger::plus)
}
class NoOp : Instruction {
    override fun doIt(): String = ""
    override fun length(): BigInteger = BigInteger.ZERO
}


fun slurpText(amountChars: Int, list: List<CompressionToken>) : Pair<Instruction, List<CompressionToken>> {
    var remainingList = list
    var amountCopied = 0
    var copiedData = ""

    while (amountCopied < amountChars) {
        val token = remainingList[0]
        remainingList = remainingList.drop(1)
        amountCopied += token.asText().s.length
        val incomingText = token.asText().s
        copiedData += incomingText
    }

    return Pair(DumbString(copiedData), remainingList)
}

fun recursiveParse(amountChars: Int, list: List<CompressionToken>) : Pair<Instruction, List<CompressionToken>> {
    var remainingList = list
    var amountCopied = 0
    var copiedData = emptyList<CompressionToken>()

    while (amountCopied < amountChars) {
        val token = remainingList[0]
        remainingList = remainingList.drop(1)
        amountCopied += token.asText().s.length
        copiedData += token
    }

    return Pair(parse(copiedData, ::recursiveParse), remainingList)

}