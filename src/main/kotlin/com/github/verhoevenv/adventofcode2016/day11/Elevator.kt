package com.github.verhoevenv.adventofcode2016.day11

fun parse(input: String) : FloorState {
    val floorDescriptions = input.split("\n")

    return FloorState(elevator = 1, floors = parseFloors(floorDescriptions))
}

fun parseFloors(floorDescriptions: List<String>): Map<Int, Set<Item>> {
    return floorDescriptions.map(::parseFloor).toMap()
}

fun parseFloor(floor: String): Pair<Int, Set<Item>> {
    val nb = """The ([a-z]+) floor""".toRegex().find(floor)!!.groupValues[1].positionalToInt()
    val chips = """a ([a-z]+)-compatible microchip""".toRegex().findAll(floor).map {it.groupValues[1]}.map { Microchip(it) }
    val generators = """a ([a-z]+) generator""".toRegex().findAll(floor).map {it.groupValues[1]}.map { Generator(it) }

    return Pair(nb, (chips + generators).toSet())
}

fun String.positionalToInt() : Int {
    return when(this) {
        "first" -> 1
        "second" -> 2
        "third" -> 3
        "fourth" -> 4
        else -> throw IllegalArgumentException("can't convert $this to a number")
    }
}

fun searchShortestPlan(from: FloorState): List<Step>? {
    val allItems = from.floors.values.flatten().toSet()
    val goal = FloorState(elevator = 4, floors = mapOf(1 to emptySet(), 2 to emptySet(), 3 to emptySet(), 4 to allItems))


    return breadthFirstSearch(goal, from)
}

private fun breadthFirstSearch(from: FloorState, to: FloorState): List<Step>? {
    val paths = mutableMapOf(from to emptyList<Step>())
    val statesToVisit = mutableListOf(from)

    while (statesToVisit.isNotEmpty()) {
        val state = statesToVisit.minBy { paths[it]!!.size + heur(it, to) }!!
        val pathToState = paths[state]!!
        statesToVisit.remove(state)

        val generateSteps = state.generateValidSteps()
        for (generated in generateSteps) {
            val (genstep, genstate) = generated
            val newPath = pathToState + genstep
            if (genstate == to) return newPath
            if (!paths.containsKey(genstate) || paths[genstate]!!.size > newPath.size) {
                paths.put(genstate, newPath)
                statesToVisit.add(genstate)
            }
        }
    }
    return null
}

fun heur(state: FloorState, goal: FloorState): Int {
    val reverseGoal = goal.reverseMap()
    val reverseState = state.reverseMap()
    return reverseGoal.keys.map { item -> reverseState[item]!! - reverseGoal[item]!! }.sum() / 2
}


data class FloorState(val elevator: Int, val floors: Map<Int, Set<Item>>) {
    fun generateValidSteps(): List<Pair<Step, FloorState>> = generateSteps().filter { it.second.isValid() }

    fun generateSteps(): List<Pair<Step, FloorState>> = generateAllSteps().map { Pair(it, this.move(it)) }

    private fun generateAllSteps(): List<Step> {
        val stateAtElevator = floors[elevator]!!
        val subsetsOfSize1: Set<Item> = stateAtElevator.subsetsOfSize1()
        val subsetsOfSize2: Set<Set<Item>> = stateAtElevator.subsetsOfSize2()
        return subsetsOfSize2.map { MoveUp2(it.first(), it.last()) } + subsetsOfSize1.map { MoveUp1(it) } + subsetsOfSize1.map { MoveDown1(it) } + subsetsOfSize2.map { MoveDown2(it.first(), it.last()) }
    }

    fun move(step: Step): FloorState {
        return when(step) {
            is MoveUp1 -> FloorState(elevator + 1, floors.move(step.item, from = elevator, to = elevator + 1))
            is MoveDown1 -> FloorState(elevator - 1, floors.move(step.item, from = elevator, to = elevator - 1))
            is MoveUp2 -> FloorState(elevator + 1, floors.move(step.item1, from = elevator, to = elevator + 1).move(step.item2, from = elevator, to = elevator + 1))
            is MoveDown2 -> FloorState(elevator - 1, floors.move(step.item1, from = elevator, to = elevator - 1).move(step.item2, from = elevator, to = elevator - 1))
            else -> throw IllegalArgumentException("cannot handle $step")
        }
    }

    fun isSafe(): Boolean = floors.values.all(Set<Item>::isSafe)
    fun isValid(): Boolean = elevator >= 1 && elevator <= 4 && isSafe()

    fun reverseMap(): Map<Item, Int> {
        return floors.flatMap { entry -> entry.value.map { item -> Pair(item, entry.key) } }.toMap()
    }
}

fun <K, V> Map<K, Set<V>>.move(item: V, from: K, to: K): Map<K, Set<V>> {
    require(this.get(from)!!.contains(item))
    val removeFromPrevious = this.plus(from to this.get(from).orEmpty().minus(item))
    val addToNext = removeFromPrevious.plus(to to this.get(to).orEmpty().plus(item))
    return addToNext
}

fun <E> Set<E>.subsetsOfSize1() : Set<E> {
    return this
}

fun <E> Set<E>.subsetsOfSize2() : Set<Set<E>> {
    return this.flatMap { e -> this.minus(e).subsetsOfSize1().map {setOf(e, it)}.toSet()  }.toSet()
}

fun Set<Item>.isSafe() : Boolean {
    val unshieldedItems: Map<String, List<Item>> = this.groupBy(Item::element).filterValues { list -> list.distinct().size < 2 }
    val hasUnshieldedChip = unshieldedItems.values.flatten().filterIsInstance<Microchip>().isNotEmpty()
    val hasGenerator = this.filterIsInstance<Generator>().isNotEmpty()
    return !(hasGenerator && hasUnshieldedChip)
}

interface Step

data class MoveUp1(val item: Item) : Step
data class MoveUp2(val item1: Item, val item2: Item) : Step
data class MoveDown1(val item: Item) : Step
data class MoveDown2(val item1: Item, val item2: Item) : Step

interface Item {
    val element: String
}

data class Microchip(override val element: String) : Item
data class Generator(override val element: String) : Item
