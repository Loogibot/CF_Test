fun main() {

    val playerChoice = randomMove("Player")

    val drawPlayerMove = when (playerChoice.name) {
        "kick" -> kick.name
        "punch" -> punch.name
        "dodge" -> dodge.name
        "grab" -> grab.name
        else -> shield.name
    }

    println("""
        Welcome to Chain Fighter!!!
        $drawPlayerMove is your available move
                
    """.trimIndent())

}

class Results {

}

class GameState { // controls the flow of the game via turns
    val turnStart: Boolean = false
    val moveOpen: Boolean = false // makes moves available to choose from
    val moveRun: Boolean = false // runs moves
    val result: Results
        get() {
            TODO()
        }
}

open class Fighter(val hp: Int, val state: GameState) {

}

data class Move(val name: String, val damage: Int, val firstAdv: String, val secondAdv: String) {
}

val kick = Move("kick",25, "punch", "shield")
val grab = Move("grab",5, "kick", "shield")
val dodge = Move("dodge",0, "kick", "grab")
val shield = Move("shield",5, "punch", "dodge")
val punch = Move("punch",15, "grab", "dodge")

val allMoves = listOf(kick,grab,dodge,shield,punch)

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