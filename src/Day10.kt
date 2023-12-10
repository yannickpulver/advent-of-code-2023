fun main() {

    val directions = listOf(
        0 to -1,
        0 to 1,
        -1 to 0,
        1 to 0
    )

    fun Char.isCompatible(other: Char, dir: Pair<Int, Int>): Boolean {
        return when (this) {
            'S' -> true
            '|' -> when (dir) {
                -1 to 0 -> other in "F7|" // todo this S can lead to errors possibly
                1 to 0 -> other in "LJ|"
                else -> false
            }

            '-' -> when (dir) {
                0 to 1 -> other in "7J-"
                0 to -1 -> other in "FL-"
                else -> false
            }

            'L' -> when (dir) {
                0 to 1 -> other in "-J7"
                -1 to 0 -> other in "|7F"
                else -> false
            }

            'J' -> when (dir) {
                0 to -1 -> other in "-LF"
                -1 to 0 -> other in "|7F"
                else -> false
            }

            '7' -> when (dir) {
                0 to -1 -> other in "-LF"
                1 to 0 -> other in "|JL"
                else -> false
            }

            'F' -> when (dir) {
                0 to 1 -> other in "-J7"
                1 to 0 -> other in "|JL"
                else -> false
            }

            else -> false
        }
    }

    fun findNext(
        input: List<String>,
        current: Pair<Int, Int>,
        last: Pair<Int, Int>,
    ): Pair<Int, Int>? {
        directions.forEach { dir ->
            val next = current + dir

            if (next.first in input.indices && next.second in input[0].indices) {
                if (next != last) {
                    if (input[current.first][current.second].isCompatible(input[next.first][next.second], dir)) {
                        return next
                    }
                }
            }
        }

        return null
    }

    fun findStart(input: List<String>): Pair<Int, Int> {
        input.forEachIndexed { index, s ->
            if (s.contains('S')) {
                return index to s.indexOf('S')
            }
        }
        error("no start found")
    }

    fun part1(input: List<String>): Int {

        val (col, row) = findStart(input)

        var last = -1 to -1
        var current = col to row
        var i = 0
        while (true) {
            val next = findNext(input = input, current = current, last = last)
            if (next != null) {
                last = current
                current = next
            } else {
                break
            }
            i++
        }

        return if (i % 2L == 0L) {
            i / 2
        } else {
            (i + 1) / 2
        }
    }

    fun part2(input: List<String>): Any {
        val path = input.map {
            it.map { -1 }.toMutableList()
        }.toMutableList()

        val loops = input.map {
            it.map { ' ' }.toMutableList()
        }.toMutableList()


        val (col, row) = findStart(input)

        var last = -1 to -1
        var current = col to row

        var i = 0
        while (true) {
            val next = findNext(input = input, current = current, last = last)
            if (next != null) {
                val new = path[next.first].apply { set(next.second, i) }
                loops[next.first] = loops[next.first].apply { set(next.second, input[next.first][next.second]) }
                path[next.first] = new
                last = current
                current = next
            } else {
                break
            }
            i++
        }
        loops[row] = loops[row].apply { set(col, '|') }



        loops.forEachIndexed { ir, row ->
            row.forEachIndexed { ic, col ->
                val pipes = "|LJ"
                val substring = row.joinToString("").substring((ic + 1).coerceAtMost(row.lastIndex))
                val inLoop = substring.count { it in pipes } % 2L != 0L
                if (inLoop && path[ir][ic] == -1) {
                    loops[ir][ic] = 'x'
                }
            }
        }

        loops.forEach {
            println(it.joinToString(""))
        }

        return loops.sumOf { it.count { it == 'x' } }
    }

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = (first + other.first) to (second + other.second)




