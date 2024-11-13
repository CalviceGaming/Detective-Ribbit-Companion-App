package pt.iade.games.detectiveribbit.models


data class Collectible(
    val id: Int,
    val name: String,
    val image: Int,
    val description: String,
    val placeholderSize: Int,
    val isunlocked: Boolean
)
