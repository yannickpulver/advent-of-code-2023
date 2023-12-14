import kotlin.math.abs

fun main() {

    fun calculateRowMatches(group: List<String>): List<Int> {
        val rowMatch =
            group.withIndex().zipWithNext().filter { it.first.value == it.second.value }.map { it.first.index }
        val rowMatches = rowMatch.mapNotNull { index ->
            var match = true
            var i = 1
            while ((index + 1 + i) <= group.lastIndex && (index - i) >= 0) {
                match = group[index + 1 + i] == group[index - i]
                if (!match) break
                i++
            }
            if (match) index else null
        }
        return rowMatches
    }

    fun transposeGroup(group: List<String>): List<String> {
        val transpose = Array(group.first().length) { CharArray(group.size) }
        for (i in group.indices) {
            for (j in group.first().indices) {
                transpose[j][i] = group[i][j]
            }
        }
        return transpose.map { it.joinToString("") }
    }

    fun parseGroups(input: List<String>): List<List<String>> {
        val index = listOf(0) + input.withIndex().filter { it.value.isEmpty() }
            .map { it.index + 1 } + listOf(input.lastIndex + 2)
        val groups = index.zipWithNext().map { (from, to) -> input.subList(from, to - 1) }
        return groups
    }

    fun calculate(groups: List<List<String>>): Int {
        val sum = groups.sumOf { group ->
            val rowMatches = calculateRowMatches(group)
            if (rowMatches.isNotEmpty()) {
                return@sumOf (rowMatches.first() + 1) * 100
            }

            val transposedGroup = transposeGroup(group)
            val colMatches = calculateRowMatches(transposedGroup)
            return@sumOf colMatches.firstOrNull()?.let { it + 1 } ?: 0
        }

        return sum
    }

    fun part1(input: List<String>): Int {
        val groups = parseGroups(input)

        val sum = calculate(groups)

        return sum
    }

    fun findSmudge(group: List<String>, transposed: Boolean, returnMatchNoSmudge: Boolean): Pair<Int, Boolean>? {
        val matches = Array(group.size) { IntArray(group.size) { -1 } }

        for (i in group.indices) {
            for (j in (i + 1)..group.lastIndex) {
                if (group[i] == group[j]) {
                    matches[i][j] = 0
                } else {
                    val differences = group[i].withIndex().filter { it.value != group[j][it.index] }
                    if (differences.size == 1) {
                        matches[i][j] = 1
                    }
                }
            }
        }
        val possibleSmudges = matches.withIndex()
            .map { row -> row.value.withIndex().filter { it.value == 1 }.map { row.index to it.index } }.flatten()
        var matchButNoSmudge: Int? = null
        possibleSmudges.forEach { (row, col) ->
            var cr = row
            var cc = col
            if (abs(col - row) % 2 == 0) return@forEach
            var match = false
            while (cr + 1 < cc - 1 && cr + 1 <= matches.lastIndex && cc - 1 >= 0) {
                cr++
                cc--
                match = matches[cr][cc] == 0
                if (!match) return@forEach
            }
            //if (match) {
                return if (transposed) cr + 1 to match else (cr + 1) * 100 to match
            //}
            //matchButNoSmudge = if (transposed) cr + 1 else (cr + 1) * 100
        }
        return if (returnMatchNoSmudge) matchButNoSmudge?.let { it to false } else null
    }

    fun getDifferences(groups: List<List<String>>): Int {
        return groups.sumOf { group ->
            var smudge = findSmudge(group, false, true)
            //if (smudge == null) {
            val transposedGroup = transposeGroup(group)
            val smudge2 = findSmudge(transposedGroup, true, smudge == null)
            //}

            val smudges = listOfNotNull(smudge, smudge2)
            if (smudge != null && smudge2 != null) {
                println(smudges)
                group.forEach {
                    println(it)
                }
                println()
            }

            val value = smudges.firstOrNull { !it.second }?.first ?: smudges.firstOrNull()?.first ?: 0
            value
        }
    }

    fun part2(input: List<String>): Any {
        val groups = parseGroups(input)

        return getDifferences(groups)
    }

    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}



