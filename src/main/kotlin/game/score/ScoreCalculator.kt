package game.score

import game.logic.BowlingRules

class ScoreCalculator(private val bowlingRules: BowlingRules) {

    fun calculate(frameBoard: FrameBoard, numberOfKnockedDownPins: Int): FrameBoard {
        setCurrentRoll(frameBoard, numberOfKnockedDownPins)

        calculateScoreForEachFrame(frameBoard)

        return frameBoard
    }

    fun getCurrentScore(frameBoard: FrameBoard): Int {
        var score = 0

        for (i in 0 until frameBoard.frames.size) {
            score += frameBoard.frames[i].score
        }

        return score
    }

    private fun calculateScoreForEachFrame(frameBoard: FrameBoard) {
        for (i in 0..frameBoard.currentFrame) {
            frameBoard.frames[i].score = frameBoard.frames[i].rolls.sum()

            if (!bowlingRules.isLastFrame(i) && bowlingRules.hasAtLeastOneRoll(frameBoard.frames[i + 1])) {
                if (bowlingRules.isStrike(frameBoard.frames[i])) {
                    calculateStrikeBonus(frameBoard, i)
                } else if (bowlingRules.isSpare(frameBoard.frames[i])) {
                    calculateSpareBonus(frameBoard, i)
                }
            }
        }
    }

    private fun calculateStrikeBonus(frameBoard: FrameBoard, i: Int) {
        if (i <= 7 && bowlingRules.isStrike(frameBoard.frames[i + 1])) {
            if (bowlingRules.hasAtLeastOneRoll(frameBoard.frames[i + 2])) {
                frameBoard.frames[i].score += frameBoard.frames[i + 1].rolls[0] + frameBoard.frames[i + 2].rolls[0]
            } else {
                frameBoard.frames[i].score += frameBoard.frames[i + 1].rolls[0]
            }
        } else if (frameBoard.frames[i + 1].rolls.size >= 2) {
            frameBoard.frames[i].score += frameBoard.frames[i + 1].rolls[0] + frameBoard.frames[i + 1].rolls[1]
        }
    }

    private fun calculateSpareBonus(frameBoard: FrameBoard, i: Int) {
        frameBoard.frames[i].score += frameBoard.frames[i + 1].rolls[0]
    }

    private fun setCurrentRoll(
        frameBoard: FrameBoard,
        numberOfKnockedDownPins: Int
    ) {
        if (bowlingRules.isLastFrame(frameBoard.currentFrame)) {
            setLastFrame(frameBoard, numberOfKnockedDownPins)
        } else {
            setNormalFrame(frameBoard, numberOfKnockedDownPins)
        }
    }

    private fun setLastFrame(frameBoard: FrameBoard, numberOfKnockedDownPins: Int) {
        if (
            frameBoard.currentRoll <= 1 ||
            bowlingRules.isSpare(frameBoard.frames[frameBoard.currentFrame]) ||
            bowlingRules.isStrike(frameBoard.frames[frameBoard.currentFrame])
        ) {
            addCurrentRoll(frameBoard, numberOfKnockedDownPins)
            frameBoard.currentRoll++
        } else {
            frameBoard.gameOver = true
        }

        if (frameBoard.currentRoll >= 3) {
            frameBoard.gameOver = true
        }
    }

    private fun setNormalFrame(frameBoard: FrameBoard, numberOfKnockedDownPins: Int) {
        addCurrentRoll(frameBoard, numberOfKnockedDownPins)

        if (
            bowlingRules.isStrike(frameBoard.frames[frameBoard.currentFrame]) ||
            bowlingRules.isLastRollForNormalFrame(frameBoard.currentRoll)
        ) {
            frameBoard.currentFrame++
            frameBoard.currentRoll = 0
        } else {
            frameBoard.currentRoll++
        }
    }

    private fun addCurrentRoll(frameBoard: FrameBoard, numberOfKnockedDownPins: Int) {
        frameBoard.frames[frameBoard.currentFrame].rolls += numberOfKnockedDownPins
    }
}
