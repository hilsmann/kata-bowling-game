package main

import game.logic.Game
import player.Player

fun main() {
    val game = Game()

    val player = Player(game)

    player.rollBall()
    println("Your current Score is: " + player.getCurrentScore())
}
