package com.aoc.problems
import kotlin.math.max

private const val SOURCE = "/Day2.txt"
private const val RED_COUNT = 12
private const val GREEN_COUNT = 13
private const val BLUE_COUNT = 14

data class Game (
    val id: Int,
    var maxRed: Int = 0,
    var maxGreen: Int = 0,
    var maxBlue: Int = 0,
    var power: Int = 0,
)
data class Result (
    val sum: Int,
    val power: Int,
)

fun main() {
    val games = processGames()
    println(games)
}

fun processGames(): Result {
    val validGames = ArrayList<Game>()
    val allGames = ArrayList<Game>()
    val idRegex = "(?<=Game.)(\\d+)".toRegex()
    val redRegex = "(\\d+)(?=.red)".toRegex()
    val blueRegex = "(\\d+)(?=.blue)".toRegex()
    val greenRegex = "(\\d+)(?=.green)".toRegex()
    val blob = object {}.javaClass.getResource(SOURCE)?.readText()
    val entries = blob?.lines()

    entries?.map { line ->
        val game = Game(idRegex.find(line)!!.value.toInt())
        val reds = redRegex.findAll(line)
        reds.forEach { red ->
            game.maxRed = max(game.maxRed, red.groupValues.first().toInt())
        }
        val greens = greenRegex.findAll(line)
        greens.forEach { green ->
            game.maxGreen = max(game.maxGreen, green.groupValues.first().toInt())
        }
        val blues = blueRegex.findAll(line)
        blues.forEach { blue ->
            game.maxBlue = max(game.maxBlue, blue.groupValues.first().toInt())
        }

        game.power = game.maxRed * game.maxBlue * game.maxGreen

        if ((game.maxRed <= RED_COUNT) && (game.maxBlue <= BLUE_COUNT) && (game.maxGreen <= GREEN_COUNT)) {
            validGames.add(game)
        }
        allGames.add(game)
    }
    return Result(
        validGames.sumOf { it.id },
        allGames.sumOf{it.power},
    )
}


