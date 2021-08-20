package game.logic

import game.score.Frame

class BowlingRules {

    fun isStrike(frame: Frame) = frame.rolls[0] == 10

    fun isSpare(frame: Frame) = frame.rolls.size >= 2 && frame.rolls[0] + frame.rolls[1] == 10

    fun hasAtLeastOneRoll(frame: Frame) = frame.rolls.isNotEmpty()

    fun isLastRollForNormalFrame(amountOfRolls: Int) = amountOfRolls >= 1

    fun isLastFrame(frameId: Int) = frameId == 9
}
