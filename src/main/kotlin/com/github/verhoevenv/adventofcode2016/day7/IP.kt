package com.github.verhoevenv.adventofcode2016.day7

fun String.startsWithABBA(): Boolean {
    return this.length >= 4 && this[0] == this[3] && this[1] == this[2] && this[0] != this[1]
}

fun String.containsABBA(): Boolean {
    return (0..this.length).any { this.substring(it).startsWithABBA() }
}

data class IPv7(val parts: Set<AddressPart>) {
    fun supportsTLS(): Boolean {
        return parts.filterIsInstance<NormalAdress>().any { it.adress.containsABBA() } &&
                parts.filterIsInstance<HyperNetAddress>().none() { it.adress.containsABBA() }

    }
}

interface AddressPart
data class HyperNetAddress(val adress: String) : AddressPart
data class NormalAdress(val adress: String) : AddressPart

fun parse(s: String): IPv7 {
    val hypernetRegex = """\[([^\]]*)\]""".toRegex()
    val hyperNets = hypernetRegex.findAll(s).map { it.groupValues[1] }.map { HyperNetAddress(it) }
    val addresses = hypernetRegex.split(s).map { NormalAdress(it) }
    return IPv7((addresses + hyperNets).toSet())
}
