fun main() {

    val playerChoice1 = Fighter(200, "pos1")
    val playerChoice2 = Fighter(200, "pos2")
    val opponentChoice = Fighter(200, "pos3")

    println(
        """
        
        Welcome to CHAIN FIGHTER!!!
        
        An opponent appears before you!
        
        You each have 200 HP. You can play 3 moves during your turn, which is called a chain.
        Your chain will be pitted against the opponent's chain, comparing each position. 
        When the greater number of moves per chain wins against the opposing chain, 
        that chain's total damage is summed and applied to the loser's HP.
        
        Each move also has a cost, which limits how many of them you can use in a chain. The 
        chain costs 4 points to use. Kick and Punch cost 2 points, Grab and Dodge cost 1
        and Shield costs 1. The chain costs return to 4 after each turn.
        
        Type 'K' for Kick, 'P' for Punch, 'G' for Grab, 'D' for Dodge and 'S' for Shield, 
        in the order you want them to appear in your chain. Choose wisely! 
        Type `start` to begin!
        
    """.trimIndent()
    )

    val gameTurn = GameState()

    val gameStart = readln()

    while (gameStart == "start") {
        gameTurn.turn = true

        println(
            """
            Your current HP is ${playerChoice1.currentHP()}, opponent current HP is ${opponentChoice.currentHP()}.
    """.trimIndent()
        )

        break
    }
}

class Results {}

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

class Fighter(private val hp: Int, private val position: String) {
    // implement game state
    // val gameState = GameState()
    // gameState.turn

    /* this class NEEDS this method, otherwise an init block or secondary constructor is needed,
    or you'll get expecting member declaration
    */

    private fun whoIsPlaying(position: String): Move {
        return when (position) {
            "pos1" -> randomMove("move1")
            "pos2" -> randomMove("move2")
            else -> randomMove("Opponent")
        }
    }

    private val move = whoIsPlaying(position)

    fun currentHP() = hp

    fun drawMove(): String {
        return when (move) {
            kick -> kick.name
            punch -> punch.name
            dodge -> dodge.name
            grab -> grab.name
            else -> shield.name
        }
    }
}

/* Create moves to be selected, with built-in name, damage, first and second advantages
    all of this should be good for now
 */

data class Move(
    val name: String, val damage: Int, val firstAdv: String, val secondAdv: String, val cost: Int
) {}

val kick = Move("kick", 25, "punch", "shield", 2)
val grab = Move("grab", 5, "kick", "shield", 2)
val dodge = Move("dodge", 0, "kick", "grab", 1)
val shield = Move("shield", 5, "punch", "dodge", 1)
val punch = Move("punch", 15, "grab", "dodge", 0)

val allMoves = listOf(kick, grab, dodge, shield, punch)

// Randomly selects a move, might need to change to accommodate
fun randomMove(player: String): Move {
    return when (player) {
        "move1" -> allMoves.random()
        "move2" -> allMoves.random()
        else -> allMoves.random()
    }
}