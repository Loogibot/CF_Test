
fun main() {

    val player = Fighter("player")
    val opponent = Fighter("opponent")

    println(
        """        
        Welcome to CHAIN FIGHTER!!!
        
        An opponent appears before you!
        
        You each have 200 HP. You can play 3 moves during your turn, which is called a chain.
        Your chain will be pitted against the opponent's chain, comparing each position. If 
        the moves are the same, they cancel each other. When the greater number of moves 
        per chain wins against the opposing chain, that chain's total damage is summed and
        applied to the loser's HP. If one position succeeds for each player, the final moves
        are compared.
        
        Each move also has a cost, which limits how many of them you can use in a chain. The 
        chain has a value of 4 points. Kick and Punch cost 2 points, Grab and Dodge cost 1
        and Shield costs 0. The chain's value returns to 4 after each turn.
        
        Lastly, the damage dealt is determined by the position, where first positions gains
        points from second and third regardless of their success. If position 1 fails, 2
        gets from 3, and finally if position 3 is the only successful move to win the chain
        comparison, its damage is the only one applied.
        
        Type 'K' for Kick, 'P' for Punch, 'G' for Grab, 'D' for Dodge and 'S' for Shield,
        in the order you want them to appear in your chain without spaces, like 'KPG' or 
        'SDD'. Choose wisely! Type `start` to begin!
        
    """.trimIndent()
    )

    val gameTurn = GameState()
    val gameStart = readln()

    while (gameStart == "start") {
        gameTurn.turn = true

        println(
            """
            Your current HP is ${player.currentHP}, opponent current HP is ${opponent.currentHP}.
            What are your 3 moves?:
    """.trimIndent()
        )

        val input = readln()
        println()
        player.inputMove(input)
        opponent.inputMove(randomMove())

        if (player.currentHP <= 0) println("Your HP is drained, you lose!")
        if (opponent.currentHP <= 0) println("Opponent HP is drained, you win!")
    }
}



class Results

class GameState(// controls the flow of the game via turns
    turnStart: Boolean = false, moveOpen: Boolean = false, // Make moves available to choose from
    val moveRun: Boolean = false, // runs moves
    val result: Results? = null
) {
    var turn = when (turnStart) {
        moveOpen -> true
        else -> {}
    }
}

class Fighter(position: String) {
    // implement game state
    // val gameState = GameState()
    // gameState.turn

    /* this class NEEDS this method, otherwise an init block or secondary constructor is needed,
    or you'll get expecting member declaration
    */
    private val hp = 200

    var currentHP = hp

    fun inputMove(input: String) {
        val validMoves = arrayListOf<Move>()
        var moveSum = 0
        val chainLimit = 4

        if (input.length == 3) {
            val pat = Regex("KPGDS", RegexOption.IGNORE_CASE)
            pat.containsMatchIn(input)
            input.lowercase()

            input.forEach {
                val m: Move = when (it) {
                    'k' -> kick
                    'p' -> punch
                    'g' -> grab
                    'd' -> dodge
                    else -> shield
                }
                validMoves.add(m)
            }
        } else {
            println("Please enter K,P,G,D or S for each of the three positions in the chain")
        }
        validMoves.forEach {
            moveSum += it.cost
        }
        if (moveSum <= chainLimit) {
            val chain = Chain(validMoves)
            println("Your moves are ${chain.firstPosition.name}, ${chain.secondPosition.name} and ${chain.thirdPosition.name}")
        } else println("Your moves costs exceed the chain limit, try again")
    }
}

/* Create moves to be selected, with built-in name, damage, first and second advantages
    all of this should be good for now
 */

class Chain(moves: ArrayList<Move>) {
    val firstPosition = moves[0]
    val secondPosition = moves[1]
    val thirdPosition = moves[2]
}

fun moveComparison() {

}

data class Move(
    val name: String, val damage: Int, val firstAdv: String, val secondAdv: String, var cost: Int
)

val kick = Move("kick", 25, "punch", "shield", 2)
val grab = Move("grab", 5, "kick", "shield", 2)
val dodge = Move("dodge", 0, "kick", "grab", 1)
val shield = Move("shield", 5, "punch", "dodge", 1)
val punch = Move("punch", 15, "grab", "dodge", 0)

val allMoves = listOf("k", "p", "d", "s", "p")

// Randomly selects a move
fun randomMove(): String = allMoves.random() + allMoves.random() + allMoves.random()
