package Moves

/* Create moves to be selected, with built-in name, damage,
    first and second advantages, and cost of move all of
    this should be good for now
 */
data class Move(
    val name: String,
    val damage: Int,
    val firstAdv: String,
    val secondAdv: String,
    val cost: Int
)