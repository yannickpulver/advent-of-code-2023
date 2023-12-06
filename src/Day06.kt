import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

fun main() {

    val regex = Regex("\\d+")

    fun part1(input: List<String>): Int {
        val times = regex.findAll(input[0]).map { it.value.toInt() }.toList()
        val distances = regex.findAll(input[1]).map { it.value.toInt() }.toList()

        val wins = times.mapIndexed { i, s ->
            var wins = 0;
            val d = distances[i]

            repeat(s) {
                val win = d < (it * (s - it))
                if (win) wins++
            }
            wins
        }.reduce { acc, i -> acc * i }

        return wins
    }

    fun part2(input: List<String>): Any {
        val time = regex.find(input[0].replace(" ", ""))?.value!!.toLong()
        val distance = regex.find(input[1].replace(" ", ""))?.value!!.toLong()

        var i = 0
        var wins = 0

        while (i < time / 2) {
            val win = distance < (i * (time - i))
            if (win) wins++
            i++
        }

        return if (time % 2L == 0L) {
            wins * 2 + 1
        } else {
            wins * 2 + 2
        }

    }

    //42515755
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}



