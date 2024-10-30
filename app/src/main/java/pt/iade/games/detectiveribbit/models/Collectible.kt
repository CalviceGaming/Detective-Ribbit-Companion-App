package pt.iade.games.detectiveribbit.models

import java.net.URI

data class Collectible(
    val id: Int,
    val name: String,
    val image: URI,
    val description: String,
    val placeholderSize: Int
)
