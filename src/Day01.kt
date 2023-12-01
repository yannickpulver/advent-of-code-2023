fun main() {
    val map = mapOf(
            "one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9
    )

    fun part1(input: List<String>): Int {

        val result = input.sumOf {
            val first = it.first(Char::isDigit).digitToInt()
            val last = it.last(Char::isDigit).digitToInt()
            first * 10 + last
        }

        return result
    }

    fun part2(input: List<String>): Int {
        val options = map.keys + map.values.map { it.toString() }

        val result = input.sumOf {
            val first = it.findAnyOf(options)?.second.orEmpty().let { map.getOrElse(it) { it.toInt() } }
            val last = it.findLastAnyOf(options)?.second.orEmpty().let { map.getOrElse(it) { it.toInt() } }
            first * 10 + last
        }
        return result
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}


