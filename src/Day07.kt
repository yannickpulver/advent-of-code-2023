import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

fun main() {

    fun getType(string: String): Int {
        val charGroups = string.groupBy { it }
        return when (charGroups.size) {
            1 -> 7
            2 -> if (charGroups.any { it.value.size == 4 }) 6 else 5
            3 -> if (charGroups.any { it.value.size == 3 }) 4 else 3
            4 -> 2
            5 -> 1
            else -> 0
        }
    }

    fun getTypeWithJoker(string: String): Int {
        val mostCommon = string.groupBy { it }.filterKeys { it != 'J' }.maxByOrNull { it.value.size }?.key ?: 'J'
        val charGroups = string.replace('J', mostCommon).groupBy { it }
        return when (charGroups.size) {
            1 -> 7
            2 -> if (charGroups.any { it.value.size == 4 }) 6 else 5
            3 -> if (charGroups.any { it.value.size == 3 }) 4 else 3
            4 -> 2
            5 -> 1
            else -> 0
        }
    }

    fun part1(input: List<String>): Int {
        val hands = input.filter { it.isNotEmpty() }.map { it.split(" ").let { Hand(it[0], it[1].toInt(), getType(it[0])) } }
        val strengths = setOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')
        val comparator = comparator(strengths)

        val sortedHands = hands.sortedWith(comparator)
        return sortedHands.mapIndexed { index, hand -> hand.bid * (index+1) }.sum()
    }

    fun part2(input: List<String>): Any {
        val hands = input.filter { it.isNotEmpty() }.map { it.split(" ").let { Hand(it[0], it[1].toInt(), getTypeWithJoker(it[0])) } }
        val strengths = setOf('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A')
        val comparator = comparator(strengths)
        val sortedHands = hands.sortedWith(comparator)
        return sortedHands.mapIndexed { index, hand -> hand.bid * (index+1) }.sum()
    }

    //42515755
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}

private fun comparator(strengths: Set<Char>): java.util.Comparator<Hand> {
    return Comparator { o1: Hand, o2: Hand ->
        if (o1.type > o2.type) return@Comparator 1
        if (o1.type < o2.type) return@Comparator -1

        repeat(o1.cards.length) {
            val result = o1.cards[it].strength(strengths) - o2.cards[it].strength(strengths)
            if (result != 0) return@Comparator result
        }
        return@Comparator 0
    }
}

data class Hand(val cards: String, val bid: Int, val type: Int)
fun Char.strength(strengths: Set<Char>) = strengths.indexOf(this)



