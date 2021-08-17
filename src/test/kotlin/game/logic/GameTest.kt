package game.logic

import game.score.ScoreCalculator
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkClass
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GameTest {

    @InjectMockKs
    private val game = Game()

    private val scoreCalculator = mockk<ScoreCalculator>(relaxed = true)

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
