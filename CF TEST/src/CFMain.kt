fun main(args: Array<String>) {

}

val playerChoice = MoveAvailable(1)
val playerOption = playerChoice.randomMove(1)

val drawPlayerMove = when (playerOption.name) {
    "kick" -> KICK
    "punch" -> PUNCH
    "dodge" -> DODGE
    "grab" -> GRAB
    else -> SHIELD
}

data class Move(val name: String, val damage: Int, val firstAdv: String, val secondAdv: String) {
}

val kick = Move("kick",25, "punch", "shield")
val grab = Move("grab",5, "kick", "shield")
val dodge = Move("dodge",0, "kick", "grab")
val shield = Move("shield",5, "punch", "dodge")
val punch = Move("punch",15, "grab", "dodge")

class MoveAvailable(movePos: Int) {

    val allMoves = listOf(kick,grab,dodge,shield,punch)

    fun randomMove(movePos: Int): Move {
        return when (movePos) {
            1 -> allMoves.random()
            2 -> allMoves.random()
            else -> allMoves.random()
        }
    }

}
