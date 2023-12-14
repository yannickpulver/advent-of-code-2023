fun main() {

    fun transposeGroup(group: List<String>): List<String> {
        val transpose = Array(group.first().length) { CharArray(group.size) }
        for (i in group.indices) {
            for (j in group.first().indices) {
                transpose[j][i] = group[i][j]
            }
        }
        return transpose.map { it.joinToString("") }
    }

    fun part1(input: List<String>): Int {
        val transposed = transposeGroup(input)

        val sum = transposed.sumOf { line ->
            val value = line.split('#').map { it.toCharArray().sortedBy { it != 'O' }.joinToString("") }.joinToString("#")
            value.withIndex().filter { it.value == 'O' }.map { line.length - it.index }.sum()
        }

        return sum

    }

    fun rotate(input: List<String>): List<String> {
        val transposed = transposeGroup(input)
        return transposed.map { line ->
            line.split('#').map { it.toCharArray().sortedBy { it != 'O' }.joinToString("") }.joinToString("#")
        }
    }

    fun part2(input: List<String>): Any {

        return 1
    }

    val input = readInput("Day14")
    part1(input).println()
    part2(input).println()
}



