package pt.iade.games.detectiveribbit.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.iade.games.detectiveribbit.R
import pt.iade.games.detectiveribbit.models.Collectible
import pt.iade.games.detectiveribbit.ui.components.Evidence
import pt.iade.games.detectiveribbit.ui.ui.theme.DetectiveRibbitTheme

class EvidenceBoard : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DetectiveRibbitTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    innerPadding
                    EvidenceBoardVisual()
                }
            }
        }
    }
}

@Composable
fun EvidenceBoardVisual() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // Centers content within the Box
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp), // Space between items
            verticalAlignment = Alignment.CenterVertically      // Align items vertically in the row
        ) {
            Evidence(
                collectible = Collectible(
                    id = 1,
                    name = "Photo",
                    image = R.drawable.flymafiaphoto,
                    description = "Photo of the family of the fly mafia boss.",
                    placeholderSize = 1,
                    isunlocked = true
                )
            )
            Evidence(
                collectible = Collectible(
                    id = 1,
                    name = "Gun",
                    image = R.drawable.gun,
                    description = "Gun used by the fly mafia",
                    placeholderSize = 1,
                    isunlocked = true
                )
            )
            Evidence(
                collectible = Collectible(
                    id = 1,
                    name = "Pipe",
                    image = R.drawable.funnyactivepipe,
                    description = "Pipe from the pipes puzzle",
                    placeholderSize = 1,
                    isunlocked = true
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    DetectiveRibbitTheme {
        EvidenceBoardVisual()
    }
}