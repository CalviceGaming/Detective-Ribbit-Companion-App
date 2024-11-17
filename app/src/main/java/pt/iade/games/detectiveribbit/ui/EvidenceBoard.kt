package pt.iade.games.detectiveribbit.ui

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.iade.games.detectiveribbit.R
import pt.iade.games.detectiveribbit.models.Collectible
import pt.iade.games.detectiveribbit.ui.components.CollectibleInfo
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
    val activity = LocalContext.current as? Activity

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // Centers content within the Box

    ) {
        Button(
            onClick = { activity?.finish() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp),
        ) {
            Text("Back")
        }
        Box(
            modifier = Modifier
                .background(color = Color(0xFF8B4513))
                .wrapContentSize()
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
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
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    DetectiveRibbitTheme {
        EvidenceBoardVisual()
    }
}