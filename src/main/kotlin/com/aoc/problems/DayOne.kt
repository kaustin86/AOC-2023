package com.aoc.problems
class DayOne

private const val SOURCE = "/Day1.txt"

fun main() {
	val result = process(false)
	val resultWithWords = process(true)
	print("Total: $result \n Total With Words: $resultWithWords")
}

private fun process(withWords: Boolean): Int {
	val codes = getCodes(withWords)
	val extractedCodes = extractCodes(codes)
	return sum(extractedCodes)
}

private fun getCodes(withWords: Boolean): List<String>? {
	var blob = object {}.javaClass.getResource(SOURCE)?.readText()
	if (withWords) {
		wordNumbers.forEach {
			blob = blob?.replace(it.word, it.number)
		}
	}
	return blob?.lines()
}

private fun extractCodes(codes: List<String>?): ArrayList<Int>{
	val extractedCodes = ArrayList<Int>()
	fun String.getFirstAndLast() = first() to last()
	codes?.forEach  {
		val digits = it.filter(Char::isDigit)
		val (firstInt, lastInt) = digits.getFirstAndLast()
		extractedCodes.add("${firstInt}${lastInt}".toInt())
	}
	return extractedCodes
}

private fun sum(extractedCodes: List<Int>): Int {
	var total = 0
	extractedCodes.forEach{
		total += it
	}
	return total
}

data class WordNumber(val word: String, val number: String)
val wordNumbers = listOf(
	WordNumber("one", "o1e"),
	WordNumber("two", "t2o"),
	WordNumber("three", "t3e"),
	WordNumber("four", "f4r"),
	WordNumber("five", "f5e"),
	WordNumber("six", "s6x"),
	WordNumber("seven", "s7n"),
	WordNumber("eight", "e8t"),
	WordNumber("nine", "n9e")
)