package player

import game.logic.Game

class Player(val game: Game) {

    fun rollBall() {
        game.roll(10)
        println("You rolled an 10")
    }

    fun getCurrentScore(): Int {
        return game.score()
    }
}
