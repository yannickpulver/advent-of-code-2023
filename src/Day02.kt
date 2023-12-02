import kotlin.math.max

fun main() {

    val idRegex = "Game (\\d*)".toRegex()
    val valueRegex = "(\\d*) (red|blue|green)".toRegex()

    fun part1(input: List<String>): Int {

        val result = input.sumOf {
            val gameId = idRegex.find(it)?.groupValues?.get(1)?.toInt() ?: 0
            val games = it.drop(8).split("; ")

            val possible = games.map {
                val map = valueRegex.findAll(it).map {
                    val key = it.groupValues[2]
                    val value = it.groupValues[1].toInt()
                    key to value
                }.toMap()

                val possible = (map["red"] ?: 0) <= 12
                    && (map["green"] ?: 0) <= 13
                    && (map["blue"] ?: 0) <= 14
                possible
            }.all { it }

            if (possible) gameId else 0
        }
        return result
    }

    fun part2(input: List<String>): Int {
        val result = input.sumOf {
            val games = it.drop(8).split("; ")
            val map = mutableMapOf<String, Int>()
            games.forEach { game ->
                valueRegex.findAll(game).forEach {
                    val key = it.groupValues[2]
                    val value = it.groupValues[1].toInt()
                    map[key] = max(map[key] ?: 0, value)
                }
            }
            map.values.reduce { acc, i -> acc * i }
        }
        return result
    }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}


