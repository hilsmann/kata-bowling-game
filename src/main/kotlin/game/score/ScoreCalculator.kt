package game.score

class ScoreCalculator {

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

            if (isNotLastFrame(i) && hasAtLeastOneRoll(frameBoard.frames[i + 1])) {
                if (isStrike(frameBoard.frames[i])) {
                    calculateStrikeBonus(frameBoard, i)
                } else if (isSpare(frameBoard.frames[i])) {
                    calculateSpareBonus(frameBoard, i)
                }
            }
        }
    }

    private fun calculateStrikeBonus(frameBoard: FrameBoard, i: Int) {
        if (i <= 7 && hasAtLeastOneRoll(frameBoard.frames[i + 2]) && isStrike(frameBoard.frames[i + 1])) {
            frameBoard.frames[i].score += frameBoard.frames[i + 1].rolls[0] + frameBoard.frames[i + 2].rolls[0]
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
        if (isLastFrame(frameBoard)) {
            setLastFrame(frameBoard, numberOfKnockedDownPins)
        } else {
            setNormalFrame(frameBoard, numberOfKnockedDownPins)
        }
    }

    private fun setLastFrame(frameBoard: FrameBoard, numberOfKnockedDownPins: Int) {
        if (
            frameBoard.currentRoll <= 1 ||
            isSpare(frameBoard.frames[frameBoard.currentFrame]) ||
            isStrike(frameBoard.frames[frameBoard.currentFrame])
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

        if (isStrike(frameBoard.frames[frameBoard.currentFrame]) || isLastRollForNormalFrame(frameBoard.currentRoll)) {
            frameBoard.currentFrame++
            frameBoard.currentRoll = 0
        } else {
            frameBoard.currentRoll++
        }
    }

    private fun addCurrentRoll(frameBoard: FrameBoard, numberOfKnockedDownPins: Int) {
        frameBoard.frames[frameBoard.currentFrame].rolls += numberOfKnockedDownPins
    }

    private fun isLastRollForNormalFrame(amountOfRolls: Int) = amountOfRolls >= 1

    private fun isStrike(frame: Frame) = frame.rolls[0] == 10

    private fun isSpare(frame: Frame) = frame.rolls.size >= 2 && frame.rolls[0] + frame.rolls[1] == 10

    private fun hasAtLeastOneRoll(frame: Frame) = frame.rolls.isNotEmpty()

    private fun isNotLastFrame(frameId: Int) = frameId <= 8

    private fun isLastFrame(frameBoard: FrameBoard) = frameBoard.currentFrame == 9
}
