package com.github.verhoevenv.adventofcode2016.day7

fun String.startsWithABBA(): Boolean {
    return this.length >= 4 && this[0] == this[3] && this[1] == this[2] && this[0] != this[1]
}

fun String.startsWithABA(): Boolean {
    return this.length >= 3 && this[0] == this[2] && this[0] != this[1]
}

fun String.containsABBA(): Boolean {
    return (0..this.length).any { this.substring(it).startsWithABBA() }
}

data class IPv7(val parts: Set<AddressPart>) {

    private fun hypernetParts(): List<String> = parts.filterIsInstance<HyperNetAddress>().map { it.adress }
    private fun normalParts(): List<String> = parts.filterIsInstance<NormalAdress>().map { it.adress }

    fun supportsTLS(): Boolean {
        return normalParts().any { it.containsABBA() } &&
                hypernetParts().none() { it.containsABBA() }

    }

    fun supportsSSL(): Boolean {
        val abas = normalParts().flatMap { it.getABAs() }
        return abas.map(ABA::bab).any { bab -> hypernetParts().any{ part -> part.contains(bab) } }
    }

}

interface AddressPart
data class HyperNetAddress(val adress: String) : AddressPart
data class NormalAdress(val adress: String) : AddressPart

fun String.getABAs(): List<ABA> {
    return (0..this.length).map { this.substring(it) }.filter(String::startsWithABA).map { ABA(it[0], it[1]) }
}


data class ABA(val a: Char, val b: Char) {
    constructor(aba: String) : this(aba[0], aba[1]) {
        require(aba.length == 3 && aba.startsWithABA())
    }
    val bab: String = "$b$a$b"
}

fun parse(s: String): IPv7 {
    val hypernetRegex = """\[([^\]]*)\]""".toRegex()
    val hyperNets = hypernetRegex.findAll(s).map { it.groupValues[1] }.map { HyperNetAddress(it) }
    val addresses = hypernetRegex.split(s).map { NormalAdress(it) }
    return IPv7((addresses + hyperNets).toSet())
}
