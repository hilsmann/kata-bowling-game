package game.logic

import game.score.ScoreCalculator
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GameTest {

    private val scoreCalculator = mockk<ScoreCalculator>()

    private val game = Game(scoreCalculator)

    @Test
    fun `Start a new Game and get the score`() {
        every { scoreCalculator.getCurrentScore(any()) } returns 10

        val score = game.score()

        assertEquals(score, 10)
    }

    @Test
    fun `Start a new Game and the Game should not be over`() {
        val score = game.isGameOver()

        assertEquals(score, false)
    }
}
