package com.github.verhoevenv.adventofcode2016.day8

import java.util.*

class Display(val width: Int = 50, val height: Int = 6) {

    var pixels = emptySet<Pixel>()


    fun nbLitPixels(): Int = pixels.size
    fun isLit(p: Pixel): Boolean = pixels.contains(p)

    fun operate(command: Command): Display {
        when(command) {
            is Rect -> rect(command)
            is RotateCol -> rotateCol(command)
            is RotateRow -> rotateRow(command)
        }

        return this
    }

    fun show(): String {
        return (0..height - 1).map { y ->
            (0..width - 1).map { x -> if(isLit(Pixel(x, y))) { "#" } else { "." } }.joinToString(separator = "")
        }.joinToString(separator = "\n")
    }

    private fun rect(command: Rect) {
        for (x in 0..command.w - 1) {
            for (y in 0..command.h - 1) {
                pixels += Pixel(x, y)
            }
        }
    }

    private fun rotateRow(command: RotateRow) {
        pixels = pixels.map {
            val (x, y) = it
            when(y) {
                command.row -> Pixel((x + command.num) % width, y)
                else -> it
        } }.toSet()
    }

    private fun rotateCol(command: RotateCol) {
        pixels = pixels.map {
            val (x, y) = it
            when(x) {
                command.col -> Pixel(x, (y + command.num) % height)
                else -> it
        } }.toSet()
    }
}

data class Pixel(val x: Int, val y: Int)

interface Command
data class Rect(val w: Int, val h: Int): Command
data class RotateCol(val col: Int, val num: Int): Command
data class RotateRow(val row: Int, val num: Int): Command

fun parse(commandString: String): Command {
    return when {
        commandString.startsWith("rect") -> {
            val (w, h) = """rect (\d+)x(\d+)""".toRegex().matchEntire(commandString)!!.destructured
            Rect(w.toInt(), h.toInt())
        }
        commandString.startsWith("rotate row") -> {
            val (row, num) = """rotate row y=(\d+) by (\d+)""".toRegex().matchEntire(commandString)!!.destructured
            RotateRow(row.toInt(), num.toInt())
        }
        commandString.startsWith("rotate column") -> {
            val (col, num) = """rotate column x=(\d+) by (\d+)""".toRegex().matchEntire(commandString)!!.destructured
            RotateCol(col.toInt(), num.toInt())
        }
        else -> throw IllegalArgumentException("unknown command $commandString")
    }
}