package pt.iade.games.detectiveribbitlayout.models

data class Collectible(
    val id: Int,
    val name: String,
    val image: Int,
    val description: String,
    val placeholderSize: Int,
    val isUnlocked: Boolean
)
