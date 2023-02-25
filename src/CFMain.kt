
fun main() {

    val player = Fighter("player", maxHp)
    val opponent = Fighter("opponent", maxHp)

    println(
        """        
        Welcome to CHAIN FIGHTER!!!
        
        An opponent appears before you!
        
        You each have 200 HP. You can play 3 moves each turn, which is called a chain.
        Your chain will fight against the opponent's chain, comparing each position. If 
        the moves are the same, they cancel each other. When the greater number of moves 
        per chain wins against the opposing chain, that chain's total damage is summed and
        applied to the loser's HP. If one position succeeds for each player, the final moves
        are compared.
        
        Each move also has a cost, which limits how many of them you can use in a chain. The 
        chain has a value of 4 points. Kick and Punch cost 2 points, Grab and Dodge cost 1
        and Shield costs 0. The chain's value returns to 4 after each turn.
        
        Lastly, the damage dealt is determined by the position, where first positions gains
        points from second and third regardless of their success. If position 1 fails, 2
        gets from 3, and finally if position 3 is the only successful move to win the chain
        comparison, its damage is the only one applied.
        
        Type 'K' for Kick, 'P' for Punch, 'G' for Grab, 'D' for Dodge and 'S' for Shield,
        in the order you want them to appear in your chain without spaces, like 'KPG' or 
        'SDD'. Choose wisely! Type `start` to begin!
        
    """.trimIndent()
    )

    val gameTurn = GameState()
    val gameStart = readln()

    while (gameStart == "start") {
        gameTurn.turn = true
        println(
            """
            Your current HP is ${player.currentHP}, opponent's current HP is ${opponent.currentHP}.
            What are your 3 moves?:
    """.trimIndent()
        )

        val input = readln()
        println()
        player.inputMove(input)
        opponent.inputMove(randomMoves())

        if (player.currentHP <= 0) println("Your HP is drained, you lose!")
        if (opponent.currentHP <= 0) println("Opponent HP is drained, you win!")
    }
}

enum class Results { CANCEL, YOURMOVEHITS, OPPONENTMOVEHITS, YOUWIN, OPPONENTWINS, YOURCHAINISEFFECTIVE, OPPONENTCHAINISEFFECTIVE }

class GameState(
// controls the flow of the game via turns
    turnStart: Boolean = false,
    moveOpen: Boolean = false, // Make moves available to choose from
//    val moveRun: Boolean = false, // runs moves
//    val result: Results? = null
) {
    var turn = when (turnStart) {
        moveOpen -> true
        else -> {}
    }
}

class Fighter(player: String, hp: Int) {
    private val p = player
    var currentHP = hp

    fun inputMove(input: String) {
        val validMoves = arrayListOf<Move>()
        var moveSum = 0
        val chainLimit = 4

        if (input.length == 3) {
            val pat = Regex("KPGDS", RegexOption.IGNORE_CASE)
            pat.containsMatchIn(input)
            input.lowercase()

            input.forEach {
                val m: Move = when (it) {
                    'k' -> kick
                    'p' -> punch
                    'g' -> grab
                    'd' -> dodge
                    's' -> shield
                    else -> Move("Not A Move", 0, "None", "None", 10)
                }
                validMoves.add(m)
            }
        } else {
            println("Please enter K,P,G,D or S for each of the three positions in the chain")
        }
        validMoves.forEach {
            moveSum += it.cost
        }
        currentChain.addAll(validMoves)
        if (moveSum <= chainLimit) {
            val chain = Chain(validMoves)
            println("$p's moves are ${chain.firstPosition.name}, ${chain.secondPosition.name} and ${chain.thirdPosition.name}")
            println(" ")
            if (currentChain.size == 6) {
                println("${moveComparison(currentChain)}")
                currentHP -= moveComparison(currentChain)

                println("$currentHP")
                currentChain.clear()
                resultsChain.clear()
            }
        } else {
            println("$p's moves costs exceeds the chain's limit, try again")
            currentHP -= moveComparison(currentChain)
        }
    }
}
class Chain(moves: ArrayList<Move>) {
    val firstPosition = moves[0]
    val secondPosition = moves[1]
    val thirdPosition = moves[2]
}

fun moveComparison(current: ArrayList<Move>): Int {
    val pChain = current.subList(0, 3)
    val oChain = current.subList(3, 6)
    var i = 0

    pChain.forEach {
        if (it.name != oChain[i].name) {
            if (it.name in oChain[i].firstAdv || it.name in oChain[i].secondAdv) {
                resultsChain.add(Results.OPPONENTMOVEHITS)
            } else resultsChain.add(Results.YOURMOVEHITS)
        }
        i += 1
    }
    if (resultsChain.contains(Results.YOURMOVEHITS)) {
        pChain.forEach { damageApplied += it.damage }
    }
    if (resultsChain.contains(Results.OPPONENTMOVEHITS)) {
        oChain.forEach { damageApplied += it.damage }
    }
    println("$resultsChain")
    return damageApplied
}

/* Create moves to be selected, with built-in name, damage, first and second advantages, and cost of move
    all of this should be good for now
 */
data class Move(
    val name: String, val damage: Int, val firstAdv: String, val secondAdv: String, val cost: Int
)

var currentChain = arrayListOf<Move>()
var resultsChain = arrayListOf<Results>()

var maxHp = 500
var damageApplied = 0
// var pTotalDamage = 0
// var oTotalDamage = 0

// build moves with a name, the damage it deals, the moves it has advantages over, and it's cost to use in the chain
val kick = Move("kick", 25, "punch", "shield", 2)
val punch = Move("punch", 15, "grab", "dodge", 2)
val grab = Move("grab", 5, "kick", "shield", 1)
val dodge = Move("dodge", 0, "kick", "grab", 1)
val shield = Move("shield", 5, "punch", "dodge", 0)

val allMoves = listOf("k", "p", "d", "s", "g")

// Randomly selects 3 moves for a chain
fun randomMoves(): String = allMoves.random() + allMoves.random() + allMoves.random()