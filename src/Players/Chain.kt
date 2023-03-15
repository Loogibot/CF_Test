package Players

import Moves.Move

class Chain(moves: MutableList<Move>) {
    val first = moves[0]
    val second = moves[1]
    val third = moves[2]

    val totalDamage = first.damage + second.damage + third.damage
}