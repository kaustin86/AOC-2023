package com.aoc.problems

private const val SOURCE = "/Day3.txt"

fun main () {
    processInput()
}

data class Part (
    val id: Int,
    val lineNo: Int,
    val range: IntRange,
    val startPos: Int,
    val endPos: Int,
    val adjGear: Symbol? = null
)

data class Symbol (
    val value: String,
    val posX: Int,
    val lineNo: Int,
)

data class MatchResult (
    val symbol: Symbol?,
    val result: Boolean
)

fun processInput() {
    val lines = object {}.javaClass.getResource(SOURCE)!!.readText().lines()
    val partRegex = Regex("(\\d+)")
    val symbolRegex = Regex("[^\\w.]+")
    val partList = mutableListOf<Part>()
    val validParts = mutableListOf<Part>()
    val symbolList = mutableListOf<Symbol>()
    lines.forEachIndexed{ index, line ->
        symbolRegex.findAll(line).forEach{
            symbolList.add(
                Symbol(
                    it.value,
                    it.range.first,
                    index,
                )
            )
        }
        partRegex.findAll(line).forEach{
            partList.add(
                Part(
                    it.value.toInt(),
                    index,
                    it.range,
                    it.range.first,
                    it.range.last,
                )
            )
        }
    }
    partList.forEach{
        if(isValid(it, symbolList).result) {
            validParts.add(it)
        }
    }
    findGear(partList, symbolList)
    return
}
fun isValid(part: Part, symbols: MutableList<Symbol>): MatchResult {
    val validRange = (0.coerceAtLeast((part.startPos) - 1)..part.endPos+1)
    val searchLines = (0.coerceAtLeast(part.lineNo-1) .. part.lineNo+1.coerceAtMost(symbols.size))
    for (i in searchLines) {
        symbols.filter{symbol -> symbol.lineNo == i}.map{
            if(validRange.contains(it.posX)) return MatchResult(
                it,
                true
            )
        }
    }
    return MatchResult(
        null,
        false
    )
}

fun findGear(parts: MutableList<Part>, symbols: MutableList<Symbol>) {
   val partsWithGears = mutableListOf<Part>()
    val gears = symbols.filter{it.value == "*"}.toMutableList()
    val ratios = mutableListOf<Int>()
    val toRemove = mutableListOf<Part>()
    parts.forEach{
        val valid = isValid(it, gears)
        if (valid.result) {
            val newPart = it.copy(
                id = it.id,
                lineNo = it.lineNo,
                range = it.range,
                startPos = it.startPos,
                endPos = it.endPos,
                adjGear = valid.symbol
            )
            partsWithGears.add(newPart)
        }
    }
    partsWithGears.forEach{ part ->
        val match = partsWithGears.first { it !in toRemove && it.adjGear == part.adjGear }
        if ((match.id != part.id)) {
            ratios.add(match.id * part.id)
            toRemove.add(match)
        }
    }
    println(ratios.sum())
}
