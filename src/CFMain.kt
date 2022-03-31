fun main() {

    val playerChoice = Fighter(200, true)

    println("""
        Welcome to Chain Fighter!!!
        Please enter a name for your player:
                
    """.trimIndent())

    val drawPlayerMove = when (playerChoice.name) {
        kick -> kick.name
        punch -> punch.name
        dodge -> dodge.name
        grab -> grab.name
        else -> shield.name
    }

    println("""
        
        $drawPlayerMove is your available move
    """.trimIndent())

}

class Results {
}

class GameState { // controls the flow of the game via turns
    val turnStart: Boolean = false
    val moveOpen: Boolean = false // makes moves available to choose from
    val moveRun: Boolean = false // runs moves
    val result: Results? = null
}

open class Fighter(val hp: Int = 200, val human: Boolean) {

    /* this class NEEDS this method, otherwise an init block or secondary constructor is needed,
    or you'll get expecting member declaration
    */

    fun whoIsPlaying(human: Boolean): Move {
        return if (human) {
            randomMove("Player")
        } else {
            randomMove("Opponent")
        }
    }

    val name = whoIsPlaying(true)
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

// Randomly selects a move, might need to change
fun randomMove(player: String): Move {
    return when (player) {
        "Player" -> {
            allMoves.random()
        }
        else -> {
            allMoves.random()
        }
    }
}