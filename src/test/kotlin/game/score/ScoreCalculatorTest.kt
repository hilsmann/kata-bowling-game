package game.score

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ScoreCalculatorTest {

    private val scoreCalculator = ScoreCalculator()

    @Test
    fun `Sum two Rolls in first Frame`() {
        val frameBoardInput = createInitialFrameBoard()

        var frameBoardOutput = scoreCalculator.calculate(frameBoardInput, 8)
        frameBoardOutput = scoreCalculator.calculate(frameBoardOutput, 1)

        assertEquals(frameBoardOutput.frames[0].score, 9)
    }

    @Test
    fun `Sum Spare in first Frame and bonus points`() {
        val frameBoardInput = createInitialFrameBoard()

        var frameBoardOutput = scoreCalculator.calculate(frameBoardInput, 8)
        frameBoardOutput = scoreCalculator.calculate(frameBoardOutput, 2)
        frameBoardOutput = scoreCalculator.calculate(frameBoardOutput, 5)

        assertEquals(frameBoardOutput.frames[0].score, 15)
    }

    @Test
    fun `Sum two Strikes and bonus points`() {
        val frameBoardInput = createInitialFrameBoard()

        var frameBoardOutput = scoreCalculator.calculate(frameBoardInput, 10)
        frameBoardOutput = scoreCalculator.calculate(frameBoardOutput, 10)
        frameBoardOutput = scoreCalculator.calculate(frameBoardOutput, 4)
        frameBoardOutput = scoreCalculator.calculate(frameBoardOutput, 2)

        assertEquals(frameBoardOutput.frames[0].score, 24)
        assertEquals(frameBoardOutput.frames[1].score, 16)
    }

    @Test
    fun `Roll a strike`() {
        val frameBoardInput = createInitialFrameBoard()

        val frameBoardOutput = scoreCalculator.calculate(frameBoardInput, 10)

        assertEquals(frameBoardOutput.frames[0].rolls[0], 10)
    }

    @Test
    fun `Roll two strikes in a row`() {
        val frameBoardInput = createInitialFrameBoard()

        var frameBoardOutput = scoreCalculator.calculate(frameBoardInput, 10)
        frameBoardOutput = scoreCalculator.calculate(frameBoardOutput, 10)

        assertEquals(frameBoardOutput.frames[0].rolls[0], 10)
        assertEquals(frameBoardOutput.frames[1].rolls[0], 10)
    }

    @Test
    fun `Roll two balls without strikes in a row`() {
        val frameBoardInput = createInitialFrameBoard()

        var frameBoardOutput = scoreCalculator.calculate(frameBoardInput, 9)
        frameBoardOutput = scoreCalculator.calculate(frameBoardOutput, 1)

        assertEquals(frameBoardOutput.frames[0].rolls[0], 9)
        assertEquals(frameBoardOutput.frames[0].rolls[1], 1)
    }

    @Test
    fun `Roll a perfect game`() {
        val frameBoardInput = createInitialFrameBoard()

        var frameBoardOutput = FrameBoard(listOf())

        for (i in 0..11) {
            frameBoardOutput = scoreCalculator.calculate(frameBoardInput, 10)
        }

        assertEquals(frameBoardOutput.frames.size, 10)

        for (i in 0..8) {
            assertEquals(frameBoardOutput.frames[i].rolls[0], 10)
            assertEquals(frameBoardOutput.frames[i].rolls.size, 1)
        }

        assertEquals(frameBoardOutput.frames[9].rolls.size, 3)
        assertEquals(frameBoardOutput.frames[9].rolls[0], 10)
        assertEquals(frameBoardOutput.frames[9].rolls[1], 10)
        assertEquals(frameBoardOutput.frames[9].rolls[2], 10)
        assertEquals(frameBoardOutput.gameOver, true)
    }

    @Test
    fun `Roll a spare and a strike in the last frame`() {
        val frameBoardInput = createInitialFrameBoard()

        for (i in 0..8) {
            scoreCalculator.calculate(frameBoardInput, 10)
        }

        var frameBoardOutput = scoreCalculator.calculate(frameBoardInput, 9)
        frameBoardOutput = scoreCalculator.calculate(frameBoardOutput, 1)
        frameBoardOutput = scoreCalculator.calculate(frameBoardOutput, 10)

        assertEquals(frameBoardOutput.frames.size, 10)

        for (i in 0..8) {
            assertEquals(frameBoardOutput.frames[i].rolls[0], 10)
        }

        assertEquals(frameBoardOutput.frames[9].rolls.size, 3)
        assertEquals(frameBoardOutput.frames[9].rolls[0], 9)
        assertEquals(frameBoardOutput.frames[9].rolls[1], 1)
        assertEquals(frameBoardOutput.frames[9].rolls[2], 10)
    }

    @Test
    fun `Roll no a spare and a strike in the last frame only 2 rolls are allowed`() {
        val frameBoardInput = createInitialFrameBoard()

        for (i in 0..8) {
            scoreCalculator.calculate(frameBoardInput, 10)
        }

        var frameBoardOutput = scoreCalculator.calculate(frameBoardInput, 8)
        frameBoardOutput = scoreCalculator.calculate(frameBoardOutput, 1)
        frameBoardOutput = scoreCalculator.calculate(frameBoardOutput, 10)

        assertEquals(frameBoardOutput.frames.size, 10)

        for (i in 0..8) {
            assertEquals(frameBoardOutput.frames[i].rolls[0], 10)
        }

        assertEquals(frameBoardOutput.frames[9].rolls.size, 2)
        assertEquals(frameBoardOutput.frames[9].rolls[0], 8)
        assertEquals(frameBoardOutput.frames[9].rolls[1], 1)
    }

    @Test
    fun `End score of a perfect game should be 300`() {
        val frameBoardInput = createInitialFrameBoard()

        var frameBoardOutput = FrameBoard(listOf())

        for (i in 0..11) {
            frameBoardOutput = scoreCalculator.calculate(frameBoardInput, 10)
        }

        val score: Int = scoreCalculator.getCurrentScore(frameBoardOutput)

        assertEquals(score, 300)
    }

    private fun createInitialFrameBoard(): FrameBoard {
        val frames = mutableListOf<Frame>()
        for (i in 0..9) {
            frames.add(Frame(listOf(), 0))
        }
        return FrameBoard(frames)
    }
}
