fun main() {

    val regex = "(\\d|-)+".toRegex()
    fun parse(it: String) = regex.findAll(it).map { it.value.toLong() }.toList()

    fun diffs(values: List<Long>): Long {
        val diffs = values.zipWithNext { a, b -> b - a }
        val recDiff = if (diffs.any { it != 0L }) {
            diffs(diffs)
        } else 0
        return recDiff + diffs.last()
    }

    fun part1(input: List<String>): Long {
        return input.sumOf {
            val values = parse(it)
            diffs(values) + values.last()
        }
    }

    fun part2(input: List<String>): Any {
        return input.sumOf {
            val values = parse(it).reversed()
            diffs(values) + values.last()
        }
    }

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}



