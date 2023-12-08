fun main() {

    val regex = Regex("\\w+")
    fun parseNodes(input: List<String>): Map<String, Pair<String, String>> {
        val nodes = input.drop(2).associate {
            val findings = regex.findAll(it).map { it.value }.toList()
            findings[0] to (findings[1] to findings[2])
        }
        return nodes
    }

    fun part1(input: List<String>): Int {
        val nodes = parseNodes(input)
        var instructions = input[0]
        var node = "AAA"
        var i = 0
        while (node != "ZZZ") {
            val char = instructions.first()
            instructions = instructions.drop(1) + char

            nodes[node]?.let { (left, right) ->
                node = if (char == 'L') left else right
            }
            i++
        }
        return i
    }

    fun part2(input: List<String>): Any {
        val nodes = parseNodes(input)

        val numbers = nodes.keys.filter { it.endsWith("A") }.map {
            var instructions = input[0]
            var i = 0L
            var node = it
            while (node.last() != 'Z') {
                val char = instructions.first()
                instructions = instructions.drop(1) + char

                nodes[node]?.let { (left, right) ->
                    node = if (char == 'L') left else right
                }
                i++
            }
            i
        }
        return numbers.reduce { acc, num -> lcm(acc, num) }
    }

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}

fun gcd(a: Long, b: Long): Long {
    return if (b == 0L) a else gcd(b, a % b)
}

fun lcm(a: Long, b: Long): Long {
    return a / gcd(a, b) * b
}


