import Moves.Move
import Moves.MoveData
import Players.Chain

class Fighter(player: String, hp: Int) {
    private val p = player
    var currentHP = hp

    fun inputMove(input: String) {
        val validMoves = ArrayList<Move>()
        var moveSum = 0
        val chainLimit = 4

        if (input.length == 3) {
            val pat = Regex("KPGDS", RegexOption.IGNORE_CASE)
            pat.containsMatchIn(input)
            input.lowercase()

            input.forEach {
                val m: Move = when (it) {
                    'k' -> MoveData.kick
                    'p' -> MoveData.punch
                    'g' -> MoveData.grab
                    'd' -> MoveData.dodge
                    's' -> MoveData.shield
                    else -> MoveData.noMove
                }
                validMoves.add(m)
            }
        } else {
            println("Please enter K,P,G,D or S for each of the three s in the chain")
        }
        validMoves.forEach {
            moveSum += it.cost
        }
        allFightersChain.addAll(validMoves)
        if (moveSum <= chainLimit) {
            val c = Chain(validMoves)
            println("$p's moves are ${c.first.name}, ${c.second.name} and ${c.third.name}")
            println(" ")
            if (allFightersChain.size == 6) {
                moveComparison(allFightersChain)
                allFightersChain.clear()
            }
        } else {
            println("$p's moves costs exceeds the chain's limit, try again")
            allFightersChain.clear()
            resultsChain.clear()
        }
    }
}