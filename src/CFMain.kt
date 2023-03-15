fun main() {

    val player = Fighter("player", maxHp)
    val opponent = Fighter("opponent", maxHp)

    println(
        """        
        Welcome to CHAIN FIGHTER!!!
        
        An opponent appears before you!
        
        You each have 200 HP. You can play 3 moves each turn, which is called a chain.
        Your chain will fight against the opponent's chain, comparing each . If 
        the moves are the same, they cancel each other. When the greater number of moves 
        per chain wins against the opposing chain, that chain's total damage is summed and
        applied to the loser's HP. If one  succeeds for each player, the final moves
        are compared.
        
        Each move also has a cost, which limits how many of them you can use in a chain. The 
        chain has a value of 4 points. Kick and Punch cost 2 points, Grab and Dodge cost 1
        and Shield costs 0. The chain's value returns to 4 after each turn.
        
        Lastly, the damage dealt is determined by the , where first s gains
        points from second and third regardless of their success. If  1 fails, 2
        gets from 3, and finally if  3 is the only successful move to win the chain
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
            Your current HP is ${player.currentHP - appliedDamage.first}, opponent's current HP is ${opponent.currentHP - appliedDamage.second}.
            What are your 3 moves?:
    """.trimIndent()
        )
        val input = readln()
        player.inputMove(input)
        opponent.inputMove(randomMoves())

        if (player.currentHP <= 0) {
            println("Your HP is drained, you lose!")
            break
        }
        if (opponent.currentHP <= 0) {
            println("Opponent HP is drained, you win!")
            break
        }
    }
}

enum class Results {
    CANCEL,
    YOURMOVEHITS,
    OPPONENTMOVEHITS,
    YOUWIN,
    OPPONENTWINS,
    YOURCHAINISEFFECTIVE,
    OPPONENTCHAINISEFFECTIVE
}

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

class Fighter(player: String, hp: Int) {
    private val p = player
    var currentHP = hp

    fun inputMove(input: String) {
        val validMoves = ArrayList<Move>()
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
            println("Please enter K,P,G,D or S for each of the three s in the chain")
        }
        validMoves.forEach {
            moveSum += it.cost
        }
        allFightersChain.addAll(validMoves)
        if (moveSum <= chainLimit) {
            val c = Chain(validMoves)
            println("$p's moves are ${c.first.name}, ${c.second.name} and ${c.third.name}")
            println(" ")
            if (allFightersChain.size == 6) {
                moveComparison(allFightersChain)
                allFightersChain.clear()
            }
        } else {
            println("$p's moves costs exceeds the chain's limit, try again")
            allFightersChain.clear()
            resultsChain.clear()
        }
    }
}

fun moveComparison(current: ArrayList<Move>) {
    val pChain = current.subList(0, 3)
    val oChain = current.subList(3, 6)
    var i = 0
    val results = Results.CANCEL
    resultsChain.clear()
    pChain.forEach {
        if (it.name != oChain[i].name) {
            if (it.name in oChain[i].firstAdv || it.name in oChain[i].secondAdv) {
                resultsChain.add(Results.OPPONENTMOVEHITS)
                println("Opponent's ${oChain[i].name} overcomes Your ${it.name}")
            } else {
                resultsChain.add(Results.YOURMOVEHITS)
                println("Your ${it.name} overcomes Opponent's ${oChain[i].name}")
            }
        } else
            println("Your ${it.name} cancels Opponent's ${oChain[i].name}, NO RESULT!")
        i += 1
    }
    val resultsMax =
        resultsChain.groupingBy { it.name }.eachCount().filterValues { it > 1 }.keys.maxOrNull()

    appliedDamage = when (resultsMax) {
        Results.YOURMOVEHITS.toString() -> Triple(
            appliedDamage.first,
            appliedDamage.second + Chain(pChain).totalDamage,
            Results.YOURMOVEHITS.toString() + ", Opponent takes " + Chain(pChain).totalDamage + "!"
        )

        Results.OPPONENTMOVEHITS.toString() -> Triple(
            appliedDamage.first + Chain(oChain).totalDamage,
            appliedDamage.second,
            Results.OPPONENTMOVEHITS.toString() + ", You take " + Chain(oChain).totalDamage + "!"
        )

        else -> Triple(appliedDamage.first, appliedDamage.second, Results.CANCEL.toString() + ", No one takes on damage!")
    }

    println(" ")
    println(appliedDamage.third)
//    println("O's chain total dmg ${Chain(oChain).totalDamage} | p's chain total dmg ${Chain(pChain).totalDamage} ")
//    println("$resultsMax $resultsChain | damage so far: ${appliedDamage.first} to player | ${appliedDamage.second} to opponent")
    println(" ")
}

class Chain(moves: MutableList<Move>) {
    val first = moves[0]
    val second = moves[1]
    val third = moves[2]

    val totalDamage = first.damage + second.damage + third.damage
}

/* Create moves to be selected, with built-in name, damage, first and second advantages, and cost of move
    all of this should be good for now
 */
data class Move(
    val name: String,
    val damage: Int,
    val firstAdv: String,
    val secondAdv: String,
    val cost: Int
)

var allFightersChain = arrayListOf<Move>()
var resultsChain = arrayListOf<Results>()

var maxHp = 200
var appliedDamage = Triple(0, 0, "")

// build moves with a name, the damage it deals, the moves it has advantages over, and it's cost to use in the chain
val kick = Move("kick", 25, "punch", "shield", 2)
val punch = Move("punch", 15, "grab", "dodge", 2)
val grab = Move("grab", 5, "kick", "shield", 1)
val dodge = Move("dodge", 0, "kick", "grab", 1)
val shield = Move("shield", 5, "punch", "dodge", 0)

val allMoves = listOf("k", "p", "d", "s", "g")

// Randomly selects 3 moves for a chain
fun randomMoves(): String = allMoves.random() + allMoves.random() + allMoves.random()
