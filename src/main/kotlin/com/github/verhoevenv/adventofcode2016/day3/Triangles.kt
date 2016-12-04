package com.github.verhoevenv.adventofcode2016.day3


fun countTriangles(inputLines: String): Int {
    return inputLines.split("\n").filter { s -> !s.isBlank() }.count(::isPossibleTriangle)
}

fun countColumnTriangles(inputLines: String): Int {
    val sides = inputLines.split("\n").filter { s -> !s.isBlank() }.map(::parse)
    val columnTriangles = transpose3x3(sides)
    return columnTriangles.count(::isPossibleTriangle)
}

fun transpose3x3(sides: List<Sides>): List<Sides> {
    if(sides.isEmpty()) return emptyList()
    return transpose(sides.take(3)) + transpose3x3(sides.drop(3))
}

fun transpose(threebythree: List<Sides>): List<Sides> {
    val row0 = threebythree[0]
    val row1 = threebythree[1]
    val row2 = threebythree[2]
    return listOf(
            Sides(row0.n1, row1.n1, row2.n1),
            Sides(row0.n2, row1.n2, row2.n2),
            Sides(row0.n3, row1.n3, row2.n3)
    )
}


fun isPossibleTriangle(inputLine: String) : Boolean =
    isPossibleTriangle(parse(inputLine))

fun isPossibleTriangle(sides: Sides) : Boolean =
    isPossibleTriangle(sides.ordered())

fun isPossibleTriangle(orderedNumbers: OrderedNumbers) : Boolean =
    orderedNumbers.n1 + orderedNumbers.n2 > orderedNumbers.n3

data class Sides(val n1: Int, val n2: Int, val n3: Int) {
    fun ordered() : OrderedNumbers {
        val sorted = listOf(n1, n2, n3).sorted()
        return OrderedNumbers(sorted[0], sorted[1], sorted[2])
    }
}

data class OrderedNumbers(val n1: Int, val n2: Int, val n3: Int) {
    init {
        if(n1 > n2) throw IllegalStateException("$n1 > $n2")
        if(n2 > n3) throw IllegalStateException("$n2 > $n3")
    }
}

fun parse(s: String): Sides {
    val nums = s.trim().split("""\s+""".toRegex())
    return Sides(nums[0].toInt(), nums[1].toInt(), nums[2].toInt())
}
