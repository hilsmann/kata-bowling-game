package game.score

import game.logic.BowlingRules
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ScoreCalculatorTest {

    private val bowlingRules = mockk<BowlingRules>()
    private val scoreCalculator = ScoreCalculator(bowlingRules)

    @Test
    fun `Sum two Rolls in first Frame`() {
        every { bowlingRules.isSpare(any()) } returns false
        every { bowlingRules.isStrike(any()) } returns false
        every { bowlingRules.isLastFrame(any()) } returns false
        every { bowlingRules.hasAtLeastOneRoll(any()) } returns true
        every { bowlingRules.isLastRollForNormalFrame(any()) } returns false

        val frameBoardInput = createInitialFrameBoard()

        var frameBoardOutput = scoreCalculator.calculate(frameBoardInput, 8)
        frameBoardOutput = scoreCalculator.calculate(frameBoardOutput, 1)

        assertEquals(frameBoardOutput.frames[0].score, 9)
    }

    @Test
    fun `Roll a strike`() {
        every { bowlingRules.isStrike(any()) } returns true
        every { bowlingRules.isLastFrame(any()) } returns false
        every { bowlingRules.hasAtLeastOneRoll(any()) } returns false
        every { bowlingRules.isLastRollForNormalFrame(any()) } returns false

        val frameBoardInput = createInitialFrameBoard()

        val frameBoardOutput = scoreCalculator.calculate(frameBoardInput, 10)

        assertEquals(frameBoardOutput.frames[0].rolls[0], 10)
    }

    @Test
    fun `Roll two strikes in a row`() {
        every { bowlingRules.isStrike(any()) } returns true
        every { bowlingRules.isLastFrame(any()) } returns false
        every { bowlingRules.isLastRollForNormalFrame(any()) } returns false
        every { bowlingRules.hasAtLeastOneRoll(Frame(listOf(), 0)) } returns false
        every { bowlingRules.hasAtLeastOneRoll(Frame(listOf(10), 0)) } returns true

        val frameBoardInput = createInitialFrameBoard()

        var frameBoardOutput = scoreCalculator.calculate(frameBoardInput, 10)
        frameBoardOutput = scoreCalculator.calculate(frameBoardOutput, 10)

        assertEquals(frameBoardOutput.frames[0].rolls[0], 10)
        assertEquals(frameBoardOutput.frames[0].score, 20)
        assertEquals(frameBoardOutput.frames[1].rolls[0], 10)
    }

    @Test
    fun `Default current score is 0`() {
        val frameBoardInput = createInitialFrameBoard()

        val score = scoreCalculator.getCurrentScore(frameBoardInput)

        assertEquals(score, 0)
    }

    @Test
    fun `After 1 strike current score is 10`() {
        val frameBoardInput = createInitialFrameBoard()

        every { bowlingRules.isStrike(any()) } returns true
        every { bowlingRules.isLastFrame(any()) } returns false
        every { bowlingRules.hasAtLeastOneRoll(any()) } returns false
        every { bowlingRules.isLastRollForNormalFrame(any()) } returns false

        val frameBoardOutput = scoreCalculator.calculate(frameBoardInput, 10)

        val score = scoreCalculator.getCurrentScore(frameBoardOutput)

        assertEquals(score, 10)
    }

    private fun createInitialFrameBoard(): FrameBoard {
        val frames = mutableListOf<Frame>()
        for (i in 0..9) {
            frames.add(Frame(listOf(), 0))
        }
        return FrameBoard(frames)
    }
}
