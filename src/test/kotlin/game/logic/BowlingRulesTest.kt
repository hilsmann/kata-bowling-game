package game.logic

import game.score.Frame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse

class BowlingRulesTest {
    private val bowlingRules = BowlingRules()

    @Test
    fun `Frame with 10 knocked down pins in the first roll is a strike`() {
        val frame = Frame(listOf(10), 10)

        val isStrike = bowlingRules.isStrike(frame)

        assertTrue(isStrike)
    }

    @Test
    fun `Frame with 2 rolls is not a strike`() {
        val frame = Frame(listOf(8, 2), 10)

        val isStrike = bowlingRules.isStrike(frame)

        assertFalse(isStrike)
    }

    @Test
    fun `Frame with 8 knocked down pins in the first roll and 2 in the second roll is a spare`() {
        val frame = Frame(listOf(8, 2), 10)

        val isSpare = bowlingRules.isSpare(frame)

        assertTrue(isSpare)
    }

    @Test
    fun `Frame with 1 roll is not a spare`() {
        val frame = Frame(listOf(8), 8)

        val isSpare = bowlingRules.isSpare(frame)

        assertFalse(isSpare)
    }

    @Test
    fun `Frame has 1 Roll`() {
        val frame = Frame(listOf(8), 8)

        val hasARoll = bowlingRules.hasAtLeastOneRoll(frame)

        assertTrue(hasARoll)
    }

    @Test
    fun `Frame has 0 Roll`() {
        val frame = Frame(listOf(), 0)

        val hasRoll = bowlingRules.hasAtLeastOneRoll(frame)

        assertFalse(hasRoll)
    }

    @Test
    fun `Second Roll is Last Roll for normal Frame`() {
        val isLastRollForNormalFrame = bowlingRules.isLastRollForNormalFrame(1)

        assertTrue(isLastRollForNormalFrame)
    }

    @Test
    fun `First Roll is not Last Roll for normal Frame`() {
        val isLastRollForNormalFrame = bowlingRules.isLastRollForNormalFrame(0)

        assertFalse(isLastRollForNormalFrame)
    }
}
