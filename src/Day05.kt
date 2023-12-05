import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

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
                group.convert(it)
            }
        }

        val number = curSeeds.sorted().first()

        return number
    }

    fun part2(input: List<String>): Long {
        // Breaking up the list into smaller lists, but thats hacky
        val seedRanges = input[0].drop(7).split(" ").map { it.toLong() }.chunked(2).map {
            val length = it[1]
            val value = it[0]

            buildList {
                val splitLength = length / 100
                repeat(100) {
                    add(listOf(value+splitLength*it, splitLength))
                }
            }
        }.flatten()


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

        var numbers = mutableListOf<Long>()
        seedRanges.forEach { seedRange ->
            var seeds = List(seedRange[1].toInt()) {
                seedRange[0] + it
            }

            var curSeeds = seeds
            rowGroups.forEach { group ->
                curSeeds = curSeeds.map {
                    group.convert(it)
                }
            }

            val number = curSeeds.sorted().first()
            println(number)
            seeds.toMutableList().clear()
            curSeeds.toMutableList().clear()
            numbers.add(number)
        }

        return numbers.sorted().first()
    }

    val input = readInput("Day05")
    part2(input).println()
}

data class RowGroup(val rows: List<Row>) {

    fun convert(number: Long): Long {
        return rows.find { it.isInRange(number) }?.convert(number) ?: number
    }
}

data class Row(val destinationStart: Long, val sourceStart: Long, val length: Long) {
    fun isInRange(number: Long): Boolean {
        return number >= sourceStart && number <= sourceStart + length
        //return number in range
    }

    fun convert(number: Long): Long {
        return destinationStart + (number - sourceStart)
    }
}



