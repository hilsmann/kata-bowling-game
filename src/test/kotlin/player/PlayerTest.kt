package player

import game.logic.Game
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PlayerTest {

    private val game = mockk<Game>()

    private val player = Player(game)

    @Test
    fun `Start a new Game`() {
        every { game.roll(10) } returns Unit

        player.rollBall()

        verify { game.roll(any()) }
    }

    @Test
    fun `Get the current Score`() {
        every { game.score() } returns 10

        val score = player.getCurrentScore()

        assertEquals(score, 10)
    }
}
