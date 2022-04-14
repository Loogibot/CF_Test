fun main() {

    val playerChoice1 = Fighter(200, "pos1")
    val playerChoice2 = Fighter(200, "pos2")
    val opponentChoice = Fighter(200, "pos3")

    val drawPlayerMove1 = when (playerChoice1.move) {
        kick -> kick.name
        punch -> punch.name
        dodge -> dodge.name
        grab -> grab.name
        else -> shield.name
    }
    val drawPlayerMove2 = when (playerChoice2.move) {
        kick -> kick.name
        punch -> punch.name
        dodge -> dodge.name
        grab -> grab.name
        else -> shield.name
    }
    val drawOpponentChoice = when (opponentChoice.move) {
        kick -> kick.name
        punch -> punch.name
        dodge -> dodge.name
        grab -> grab.name
        else -> shield.name
    }

    println("""
        
        Welcome to Chain Fighter!!!
        An opponent appears before you. You each have 200 HP.
        Type 'K' for Kick, 'P' for Punch, 'G' for Grab, 'D' for Dodge and 'S' for Shield.
        Only two moves will be available at each turn. Choose wisely! 
        Type start to begin!
        
    """.trimIndent())

    val gameStart = readln()

    while (gameStart == "start") {

        println("""
            $drawPlayerMove1 and $drawPlayerMove2 are your available move    
    """.trimIndent())
    break
    }
}

class Results {
}

class GameState { // controls the flow of the game via turns
    val turnStart: Boolean = false
    val moveOpen: Boolean = false // Make moves available to choose from
    val moveRun: Boolean = false // runs moves
    val result: Results? = null
}

open class Fighter(val hp: Int, private val position: String) {

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

    val move = whoIsPlaying(position)
}

/* Create moves to be selected, with built-in name, damage, first and second advantages
    all of this should be good for now
 */

data class Move(val name: String,
                val damage: Int,
                val firstAdv: String,
                val secondAdv: String) {
}

val kick = Move("kick",25, "punch", "shield")
val grab = Move("grab",5, "kick", "shield")
val dodge = Move("dodge",0, "kick", "grab")
val shield = Move("shield",5, "punch", "dodge")
val punch = Move("punch",15, "grab", "dodge")

val allMoves = listOf(kick,grab,dodge,shield,punch)

// Randomly selects a move, might need to change to accommodate
fun randomMove(player: String): Move {
    return when (player) {
        "move1" -> allMoves.random()
        "move2" -> allMoves.random()
        else -> allMoves.random()
    }
}