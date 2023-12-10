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
            it.map { ' ' }.toMutableList()
        }.toMutableList()

        val (col, row) = findStart(input)

        var last = -1 to -1
        var current = col to row
        var i = 0
        while (true) {
            val next = findNext(input = input, current = current, last = last)
            if (next != null) {
                val new = path[next.first].apply { set(next.second, input[next.first][next.second]) }
                path[next.first] = new
                last = current
                current = next
            } else {
                break
            }
            i++
        }

        // adding x's to all outside locations (no pipes)
        while (true) {
            var dirty = false
            input.forEachIndexed { ir, row ->
                row.forEachIndexed { ic, col ->
                    val isEmpty = path[ir][ic] == ' '
                    if (
                        (((ic - 1) !in row.indices || path[ir][ic - 1] == 'x')
                            || ((ic + 1) !in row.indices || path[ir][ic + 1] == 'x'))
                        && isEmpty
                    ) {
                        path[ir][ic] = 'x'
                        dirty = true
                    }

                    if (
                        (((ir - 1) !in input.indices || path[ir - 1][ic] == 'x')
                            || ((ir + 1) !in input.indices || path[ir + 1][ic] == 'x')
                            )
                        && isEmpty
                    ) {
                        path[ir][ic] = 'x'
                        dirty = true
                    }
                }
            }
            if (!dirty) break
        }

        val verticalPipes = listOf("JF", "||", "J|", "|F", "|L", "7|", "7F", "JL", "7L")
        val horizontalPipes = listOf("--", "LF", "JF", "L7", "J7", "-F", "-7", "J-", "L-")
        // finding all not-enclosed inside locations
        input.forEachIndexed { ir, row ->
            row.forEachIndexed { ic, col ->
                if (path[ir][ic] != ' ') return@forEachIndexed

                directions.forEach { directions ->
                    val next = (ir to ic) + directions
                    if (directions.first == 0) {
                        val pipeString = path[next.first-1][next.second].toString() + path[next.first][next.second] + path[next.first-1][next.second+1]
                        horizontalPipes.any { pipeString.contains(it) }
                        // horizontal
                    } else {
                        val pipeString = path[next.first].subList(next.second - 1, next.second + 1).joinToString("")
                        verticalPipes.any { pipeString.contains(it) }
                        // vertical
                    }
                }
            }
        }

        path.forEach {
            println(it.joinToString(""))
        }

        return 1
    }

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = (first + other.first) to (second + other.second)




