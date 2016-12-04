package com.github.verhoevenv.adventofcode2016.day1

fun distance(sequence:String) : Int {
    val steps = parse(sequence)
    return walk(steps).location.manhattanDistance()
}

fun walk(steps: List<Step>): Position {
    return steps
            .fold(Position(Location(0, 0), Direction.N)) {
                prevPosition, step ->
                val (rotation, dist) = step
                prevPosition.turn(rotation).move(dist)
            }
}


data class Position(val location: Location, val dir: Direction) {
    fun turn(rot: Rotation) = Position(location, dir.turn(rot))
    fun move(amount: Int) = Position(location.move(dir, amount), dir)
}

data class Location(val x: Int, val y: Int) {
    fun move(dir: Direction, amount: Int) = when(dir) {
        Direction.N -> Location(x, y + amount)
        Direction.E -> Location(x + amount, y)
        Direction.S -> Location(x, y - amount)
        Direction.W -> Location(x - amount, y)
    }

    fun manhattanDistance() : Int = Math.abs(x) + Math.abs(y)
}


enum class Direction {
    N {
        override fun turn(rot: Rotation): Direction = when(rot) {
            Rotation.LEFT -> W
            Rotation.RIGHT -> E
        }
    }, S {
        override fun turn(rot: Rotation): Direction = when(rot) {
            Rotation.LEFT -> E
            Rotation.RIGHT -> W
        }
    }, E {
        override fun turn(rot: Rotation): Direction = when(rot) {
            Rotation.LEFT -> N
            Rotation.RIGHT -> S
        }
    }, W {
        override fun turn(rot: Rotation): Direction = when(rot) {
            Rotation.LEFT -> S
            Rotation.RIGHT -> N
        }
    };

    abstract fun turn(rot: Rotation): Direction
}

fun parse(sequence: String): List<Step> =
        sequence.split(",")
                .map(String::trim)
                .map(::parseOne)

fun parseOne(s: String): Step {
    return when (s[0]) {
        'L' -> Step(Rotation.LEFT, s.drop(1).toInt())
        'R' -> Step(Rotation.RIGHT, s.drop(1).toInt())
        else -> throw IllegalArgumentException("Unknown direction $s")
    }
}

data class Step(val turn: Rotation, val dist: Int)

enum class Rotation {
    LEFT, RIGHT
}

fun decimalDigitValue(c: Char): Int {
    if (c !in '0'..'9')
        throw IllegalArgumentException("Out of range")
    return c.toInt() - '0'.toInt()
}