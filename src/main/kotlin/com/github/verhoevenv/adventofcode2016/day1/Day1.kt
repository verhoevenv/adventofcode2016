package com.github.verhoevenv.adventofcode2016.day1

import com.github.verhoevenv.adventofcode2016.util.unfold

fun distance(sequence:String) : Int {
    val steps = parse(sequence)
    return walk(steps).location.manhattanDistance()
}

fun distanceToFirstVisitedTwice(sequence:String) : Int {
    val steps = parse(sequence)
    return firstVisitedTwice(steps).manhattanDistance()
}

fun firstVisitedTwice(steps: List<Step>) : Location {
    val visitedLocations = visitedLocations(steps)
    val uniqueLocations = visitedLocations.distinct()
    return firstDifferent(visitedLocations, uniqueLocations).first
}

private fun <T> firstDifferent(l1: List<T>, l2: List<T>) =
        l1.zip(l2).first { it.first != it.second }

fun walk(steps: List<Step>): Position =
        steps.fold(Position(Location(0, 0), Direction.N), Position::step)

fun visitedLocations(steps: List<Step>): List<Location> {

    val visited: Sequence<Position> = unfold(Itinerary(Position(Location(0, 0), Direction.N), steps)) {
        val (pos, furtherSteps) = it
        if (furtherSteps.isEmpty()) null
        else {
            val visitedInStep: List<Position> = pos.stepExplicit(furtherSteps.first())
            Pair(Itinerary(visitedInStep.last(), furtherSteps.drop(1)), visitedInStep)
        }
    }.flatten()

    return visited.map(Position::location).toList()
}

data class Itinerary(val pos: Position, val steps: List<Step>)

data class Position(val location: Location, val dir: Direction) {
    fun turn(rot: Rotation) = Position(location, dir.turn(rot))
    fun move(amount: Int) = Position(location.move(dir, amount), dir)
    fun step(step: Step): Position {
        val (rotation, dist) = step
        return turn(rotation).move(dist)
    }
    fun moveExplicit(amount: Int) : List<Position> {
        if(amount == 0) return emptyList()
        val posAfterOneStep = move(1)
        return listOf(posAfterOneStep) + posAfterOneStep.moveExplicit(amount - 1)
    }
    fun stepExplicit(step: Step): List<Position> {
        val (rotation, dist) = step
        return turn(rotation).moveExplicit(dist)
    }
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
