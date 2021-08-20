package game.logic

import game.score.Frame
import game.score.FrameBoard
import game.score.ScoreCalculator

class Game(private val scoreCalculator: ScoreCalculator) {

    private var frameBoard: FrameBoard

    init {
        val frames = mutableListOf<Frame>()
        for (i in 0..9) {
            frames.add(Frame(listOf(), 0))
        }
        frameBoard = FrameBoard(frames)
    }

    fun roll(numberOfKnockedDownPins: Int) {
        frameBoard = scoreCalculator.calculate(frameBoard, numberOfKnockedDownPins)
    }

    fun score(): Int = scoreCalculator.getCurrentScore(frameBoard)

    fun isGameOver(): Boolean = frameBoard.gameOver
}
