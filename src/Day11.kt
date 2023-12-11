import kotlin.math.abs

fun main() {

    fun parse(input: List<String>): Triple<List<Int>, List<Int>, List<Pair<Int, Int>>> {
        val filledCols = Array(input[0].length) { false }
        val galaxyMap = input.mapIndexed { i, s ->
            s.withIndex().filter { (it.value == '#') }.map {
                filledCols[it.index] = true
                it.index
            }
        }

        val emptyCols = filledCols.withIndex().filterNot { it.value }.map { it.index }
        val emptyRows = galaxyMap.withIndex().filter { it.value.isEmpty() }.map { it.index }
        val galaxies = galaxyMap.withIndex().map { row -> row.value.map { row.index to it } }.flatten()
        return Triple(emptyCols, emptyRows, galaxies)
    }

    fun part1(input: List<String>): Int {

        // prepare input (make empty col / rows twice as big)

        val (emptyCols, emptyRows, galaxies) = parse(input)
        // to through each row
        val distances = mutableListOf<Int>()
        for (i in galaxies.indices) {
            for (j in (i+1)..galaxies.lastIndex) {
                val (row1, col1) = galaxies[i]
                val (row2, col2) = galaxies[j]
                val colRange = if (col1 > col2) col2..col1 else col1..col2
                val rowRange = if (row1 > row2) row1..row2 else row1..row2
                val distance = abs(row1 - row2) + abs(col1 - col2) + emptyCols.count { it in colRange } + emptyRows.count { it in rowRange }
                distances.add(distance)
            }
        }

        // make pairs with all galaxies that are in your line or below

        return distances.sum()
    }

    fun part2(input: List<String>): Any {
        // prepare input (make empty col / rows twice as big)

        val (emptyCols, emptyRows, galaxies) = parse(input)

        // to through each row
        val distances = mutableListOf<Long>()
        for (i in galaxies.indices) {
            for (j in (i+1)..galaxies.lastIndex) {
                val (row1, col1) = galaxies[i]
                val (row2, col2) = galaxies[j]
                val colRange = if (col1 > col2) col2..col1 else col1..col2
                val rowRange = if (row1 > row2) row1..row2 else row1..row2
                val distance = abs(row1 - row2) + abs(col1 - col2) + ((emptyCols.count { it in colRange } + emptyRows.count { it in rowRange }) * 999_999L)
                distances.add(distance)
            }
        }

        // make pairs with all galaxies that are in your line or below

        return distances.sum()
    }

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}



