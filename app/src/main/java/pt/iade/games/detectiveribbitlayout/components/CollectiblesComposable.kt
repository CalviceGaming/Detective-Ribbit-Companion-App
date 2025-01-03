package pt.iade.games.detectiveribbitlayout.components


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pt.iade.games.detectiveribbitlayout.R
import pt.iade.games.detectiveribbitlayout.models.Collectible
import pt.iade.games.detectiveribbitlayout.models.Evidence

@Composable
fun CollectiblesComposable(
    collectiblesFromSave: List<Collectible>? // Mark as nullable
) {
    // Check if the list is null or empty
    val collectibles = collectiblesFromSave?.takeIf { it.isNotEmpty() } ?: emptyList()

    var collect by remember { mutableStateOf<Collectible?>(null) }
    var showPopup by remember { mutableStateOf(false) }

    val predefinedCollectibles = listOf(
        Collectible(1, "Frog Statue", R.drawable.ribbitstatue, "A good looking statue that makes the apartment look better", 1, false),
        Collectible(2, "Something", R.drawable.ribbit, "Something Something that Something", 1, false)
        // Add more collectibles as needed
    )

    // Update the predefined collectibles based on the saved ones
    for (collectible in predefinedCollectibles) {
        if (collectibles.any { it.id == collectible.id }) {
            collectible.isUnlocked = true
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            items(predefinedCollectibles.size) { index ->
                val collectible = predefinedCollectibles[index]
                CollectiblePlaceHolder(
                    collectible = collectible,
                    onClick = {
                        if (collectible.isUnlocked) {
                            collect = collectible
                            showPopup = true
                        }
                    }
                )
            }
        }

        if (collect != null && showPopup) {
            CollectibleInfo(
                collectible = collect!!,
                onClick = {
                    showPopup = false
                    collect = null
                }
            )
        }
    }
}

