package pt.iade.games.detectiveribbitlayout.components


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

@Composable
fun CollectiblesComposable() {
    var collect by remember { mutableStateOf<Collectible?>(null) }
    var showPopup by remember { mutableStateOf(false) }

    val collectibles = listOf(
        Collectible(1, "Statue", R.drawable.ribbitstatue, "Found in the mafia Stackhouse.", 1, true),
        Collectible(2, "Locked Item", R.drawable.lock, "Locked collectible.", 1, false)
        // Add more collectibles as needed
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            items(collectibles.size) { index ->
                val collectible = collectibles[index]
                CollectiblePlaceHolder(
                    collectible = collectible,
                    onClick = {
                        if (collectible.isunlocked) {
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
