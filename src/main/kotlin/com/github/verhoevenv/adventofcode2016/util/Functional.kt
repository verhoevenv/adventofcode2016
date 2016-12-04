package com.github.verhoevenv.adventofcode2016.util

fun <S, V> unfold(state: S, transition: (S) -> Pair<S, V>?) : Sequence<V> {

    val iterator = object : Iterator<V> {
        var s = state
        override fun next(): V {
            val (newS, v) = transition.invoke(s)!!
            s = newS
            return v
        }

        override fun hasNext(): Boolean {
            return transition.invoke(s) != null
        }
    }

    return Sequence { iterator }
}
