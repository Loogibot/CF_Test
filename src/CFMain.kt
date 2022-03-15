fun main() {

    println("""
        Welcome to Chain Fighter!!!
        $drawPlayerMove is your available move
                
    """.trimIndent())

}

val playerChoice = randomMove("Player")

data class Move(val name: String, val damage: Int, val firstAdv: String, val secondAdv: String) {
}

val kick = Move("kick",25, "punch", "shield")
val grab = Move("grab",5, "kick", "shield")
val dodge = Move("dodge",0, "kick", "grab")
val shield = Move("shield",5, "punch", "dodge")
val punch = Move("punch",15, "grab", "dodge")

val drawPlayerMove = when (playerChoice.name) {
    "kick" -> kick
    "punch" -> punch
    "dodge" -> dodge
    "grab" -> grab
    else -> shield
}
val allMoves = listOf(kick,grab,dodge,shield,punch)

fun randomMove(player: String?): Move {
    return when (player) {
        "Player" -> {
            allMoves.random()
        }
        else -> {
            allMoves.random()
        }
    }
}