import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

fun main() {

    val numberRegex = Regex("\\d+")

    fun part1(input: List<String>): Int {
        val sum = input.sumOf {
            val x = it.split(":")[1].split("|")
            val win = numberRegex.findAll(x[0]).map { it.value.toInt() }
            val my = numberRegex.findAll(x[1]).map { it.value.toInt() }

            val pow = my
                .toList()
                .filter { it in win }
                .size

            if (pow == 0) 0 else 2.0.pow(pow - 1.0).toInt()
        }
        return sum
    }

    fun process(number: Int, map: Map<Int, Card>): Int {
        val card = map[number] ?: return 0
        val matches = card.my
            .toList()
            .filter { it in card.win }
            .size

        return if (matches > 0) {
            1 + (number + 1..number + matches).sumOf {
                process(it, map)
            }
        } else {
            1
        }
    }

    fun part2(input: List<String>): Int {
        val map = input.associate {
            val x = it.split(":")
            val card = x[0].drop(5).trim().toInt()
            val y = x[1].split("|")
            val win = numberRegex.findAll(y[0]).map { it.value.toInt() }.toList()
            val my = numberRegex.findAll(y[1]).map { it.value.toInt() }.toList()
            card to Card(win, my)
        }

        return map.toList().sumOf { (number, _) ->
            process(number, map)
        }
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

data class Card(val win: List<Int>, val my: List<Int>)


