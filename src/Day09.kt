fun main() {

    val regex = "(\\d|-)+".toRegex()
    fun parse(it: String) = regex.findAll(it).map { it.value.toLong() }.toList()

    fun getLastDiff(values: List<Long>): Long {
        val diffs = values.mapIndexed { index, i ->
            i - values[(index - 1).coerceAtLeast(0)]
        }

        val recDiff = if (diffs.any { it != 0L }) {
            getLastDiff(diffs.drop(1))
        } else 0

        return recDiff + diffs.last()
    }

    fun getFirstDiff(values: List<Long>): Long {
        val diffs = values.mapIndexed { index, i ->
            values[(index + 1).coerceAtMost(values.lastIndex)] - i
        }

        val recDiff = if (diffs.any { it != 0L }) {
            getFirstDiff(diffs.dropLast(1))
        } else 0

        return diffs.first() - recDiff
    }

    fun part1(input: List<String>): Long {
        return input.sumOf {
            val values = parse(it)
            getLastDiff(values) + values.last()
        }
    }

    fun part2(input: List<String>): Any {
        return input.sumOf {
            val values = parse(it)
            values.first() - getFirstDiff(values)
        }
    }

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}



