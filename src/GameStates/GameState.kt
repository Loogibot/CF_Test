package GameStates

class GameState(
// controls the flow of the game via turns
    turnStart: Boolean = false,
    moveOpen: Boolean = false, // Make moves available to choose from
//    val moveRun: Boolean = false, // runs moves
//    val result: Results? = null
) {
    var turn = when (turnStart) {
        moveOpen -> true
        else -> false
    }
}