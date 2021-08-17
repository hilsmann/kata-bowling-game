package game.score

data class FrameBoard(
    val frames: List<Frame>,
    var currentFrame: Int = 0,
    var currentRoll: Int = 0,
    var gameOver: Boolean = false
)
