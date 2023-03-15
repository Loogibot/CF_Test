package Moves

class MoveData {
    companion object M {
        // build moves with a name, the damage it deals, the moves it has advantages over, and it's cost to use in the chain

        val kick = Move("kick", 25, "punch", "shield", 2)
        val punch = Move("punch", 15, "grab", "dodge", 2)
        val grab = Move("grab", 5, "kick", "shield", 1)
        val dodge = Move("dodge", 0, "kick", "grab", 1)
        val shield = Move("shield", 5, "punch", "dodge", 0)
        val noMove = Move("Not A Move", 0, "None", "None", 10)

        val allMoves = listOf("k", "p", "d", "s", "g")
    }
}