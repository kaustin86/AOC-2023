package com.aoc
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication

class DayOne

private const val SOURCE = "/data.txt"
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

fun main() {
	val result = process(false)
	val resultWithWords = process(true)
	print("Total: $result")
	print("\n")
	print("Total with words: $resultWithWords")
}

private fun process(withWords: Boolean): Int {
	if (withWords) {
		val codesWithWords = getCodesWithWords()
		val extractedCodes = extractCodes(codesWithWords)
		return sum(extractedCodes)
	}
	val codes = getCodes()
	val extractedCodes = extractCodes(codes)
	return sum(extractedCodes)
}

private fun getCodes(): MutableList<String>? {
	val blob = object {}.javaClass.getResource(SOURCE)?.readText()
	return blob?.split("\n")?.toMutableList()
}

private fun getCodesWithWords(): MutableList<String>? {
	var blob = object {}.javaClass.getResource(SOURCE)?.readText()
	wordNumbers.forEach {
		blob = blob?.replace(it.word, it.number)
	}
	return  blob?.split("\n")?.toMutableList()
}

private fun extractCodes(codes: MutableList<String>?): ArrayList<Int>{
	val extractedCodes = ArrayList<Int>()
	fun String.getFirstAndLast() = first() to last()
	codes?.forEach  {
		val digits = it.filter(Char::isDigit)
		val (firstInt, lastInt) = digits.getFirstAndLast()
		extractedCodes.add("${firstInt}${lastInt}".toInt())
	}
	return extractedCodes
}

private fun sum(extractedCodes: MutableList<Int>): Int {
	var total = 0
	extractedCodes.forEach{
		total += it
	}
	return total
}