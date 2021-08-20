package main

import game.logic.BowlingRules
import game.logic.Game
import game.score.ScoreCalculator
import player.Player

fun main() {

    val bowlingRules = BowlingRules()
    val scoreCalculator = ScoreCalculator(bowlingRules)
    val game = Game(scoreCalculator)

    val player = Player(game)

    player.rollBall()
    println("Your current Score is: " + player.getCurrentScore())
}
