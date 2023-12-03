import kotlin.math.max
import kotlin.math.min

fun main() {

    val digitRegex = Regex(pattern = "\\d+")
    val symbolRegex = Regex(pattern = "([^\\d.])")
    val gearRegex = Regex(pattern = "\\*")

    val offsets = listOf(-1, 0, 1)

    fun part1(input: List<String>): Int {
        var sum = 0
        input.mapIndexed { i, s ->
            digitRegex.findAll(s).forEach { match ->
                val range = max(match.range.first - 1, 0)..min(match.range.last + 1, s.length - 1)

                val hasSymbol = offsets.any {
                    val row = i + it
                    if (row !in input.indices) return@any false

                    val y = input[row].substring(range)
                    symbolRegex.containsMatchIn(y)
                }

                if (hasSymbol) {
                    sum += match.value.toInt()
                }
            }
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        input.forEachIndexed { i, s ->
            gearRegex.findAll(s).forEach { match ->
                sum += offsets.mapNotNull { j ->
                    val row = i + j
                    if (row !in input.indices) return@mapNotNull null

                    offsets.mapNotNull { k ->
                        val col = match.range.first + k
                        if (col !in s.indices) return@mapNotNull null

                        digitRegex.findAll(input[row]).find { col in it.range }
                    }
                        .distinctBy { it.range to it.value }
                }
                    .flatten()
                    .map { it.value.toInt() }
                    .takeIf { it.size == 2 }
                    ?.reduce { acc, i -> acc * i } ?: 0

            }
        }

        return sum
    }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}


