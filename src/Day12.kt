fun main() {

    fun generateCombinations(pattern: String): List<String> {
        val questionMarks = pattern.mapIndexedNotNull { index, c -> if (c == '?') index else null }
        val possibleCombinationCount = 1 shl questionMarks.size // 2^number_of_question_marks
        return (0 until possibleCombinationCount).map { combination ->
            pattern.mapIndexed { index, c ->
                when {
                    c != '?' -> c
                    combination and (1 shl questionMarks.indexOf(index)) != 0 -> '#'
                    else -> '.'
                }
            }.joinToString("")
        }
    }

    fun part1(input: List<String>): Int {
        val parsed = input.map {
            val (text, numbers) = it.split(" ")
            text to numbers.split(",").map { it.toInt() }
        }

        return parsed.sumOf { (springs, numbers) ->

            val regex = buildString {
                append("([.]*)")
                numbers.forEachIndexed { index, number ->
                    append("#{$number}")
                    if (index != numbers.lastIndex) {
                        append("([.]+)")
                    }
                }
                append("([.]*)")
            }.toRegex()

            val combinations = generateCombinations(springs)

            val matches = combinations.count { regex.matches(it) }

            matches
        }

    }

    fun part2(input: List<String>): Any {
        return 1
    }

    val input = readInput("Day12")
    //part1(input).println()
    part2(input).println()
}



