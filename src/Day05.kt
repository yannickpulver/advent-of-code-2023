import kotlin.math.max
import kotlin.math.min

fun main() {

    fun part1(input: List<String>): Long {
        // parse data
        val seeds = input[0].drop(7).split(" ").map { it.toLong() }

        val cleanedInput = input.drop(2)

        val indexes = cleanedInput.mapIndexedNotNull { index, s -> index.takeIf { s == "" } }.toList() + listOf(
            cleanedInput.lastIndex + 1
        )

        val rowGroups = indexes.mapIndexed { index, i ->
            val start = if (index - 1 >= 0) indexes[index - 1] + 2 else 1
            val end = i

            val rows = cleanedInput.subList(start, end).map {
                val split = it.split(" ").map { it.toLong() }
                Row(split[0], split[1], split[2])
            }
            RowGroup(rows)
        }

        var curSeeds = seeds
        rowGroups.forEach { group ->
            curSeeds = curSeeds.map {
                group.convert(it).first
            }
        }

        val number = curSeeds.sorted().first()

        return number
    }

    fun part2(input: List<String>): Long {
        val seedRanges = input[0].drop(7).split(" ").map { it.toLong() }.chunked(2)

        val cleanedInput = input.drop(2)
        val indexes = cleanedInput.mapIndexedNotNull { index, s -> index.takeIf { s == "" } }.toList() + listOf(
            cleanedInput.lastIndex + 1
        )

        val rowGroups = indexes.mapIndexed { index, i ->
            val start = if (index - 1 >= 0) indexes[index - 1] + 2 else 1
            val end = i

            val rows = cleanedInput.subList(start, end).map {
                val split = it.split(" ").map { it.toLong() }
                Row(split[0], split[1], split[2])
            }
            RowGroup(rows)
        }

        var lowestNumber = Long.MAX_VALUE
        seedRanges.forEach { (seedStart, seedLength) ->
            var seed = seedStart
            while (seed <= seedStart + seedLength) {
                var maxSkip = Long.MAX_VALUE

                var currentSeed = seed
                rowGroups.forEach { group ->
                    val (result, skip) = group.convert(currentSeed)
                    currentSeed = result
                    maxSkip = min(maxSkip, skip)
                }
                lowestNumber = min(lowestNumber, currentSeed)

                seed += max(1, maxSkip)
            }
        }

        return lowestNumber
    }

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}

data class RowGroup(val rows: List<Row>) {

    fun convert(number: Long): Pair<Long, Long> {
        var newNumber = number
        var maxSkip = Long.MAX_VALUE
        rows.forEach {
            if (number >= it.sourceStart && number < (it.sourceStart + it.length)) {
                newNumber = it.destinationStart + (number - it.sourceStart)
                maxSkip = min(maxSkip, it.sourceStart + it.length - number)
            }
        }
        return newNumber to maxSkip
    }
}

data class Row(val destinationStart: Long, val sourceStart: Long, val length: Long)



