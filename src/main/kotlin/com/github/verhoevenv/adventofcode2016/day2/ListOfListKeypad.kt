package com.github.verhoevenv.adventofcode2016.day2

class ListOfListKeypad(val buttons: List<List<String?>>) : Keypad {

    private fun toListIndices(x: Int, y: Int) : Pair<Int, Int> =
        Pair(x, 2-y)

    private fun toXY(x: Int, y: Int) : Pair<Int, Int> =
        Pair(x, 2-y)

    override fun buttonAt(x: Int, y: Int) : Button? {
        val (lx, ly) = toListIndices(x, y)
        val elementAtOrNull = buttons.elementAtOrNull(ly)?.elementAtOrNull(lx)
        if(elementAtOrNull != null) return Button(x, y, elementAtOrNull) else return null
    }

    override fun buttonWithLabel(label: String) : Button {
        buttons.forEachIndexed { ly, line -> line.forEachIndexed { lx, s -> if (s == label) {
            val (x, y) = toXY(lx, ly)
            return Button(x, y, label)
        } } }
        throw IllegalArgumentException("No button found for $label")
    }


}
