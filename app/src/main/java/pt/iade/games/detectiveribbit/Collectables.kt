package pt.iade.games.detectiveribbit

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Button
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.iade.games.detectiveribbit.models.Collectible
import pt.iade.games.detectiveribbit.ui.components.CollectibleInfo
import pt.iade.games.detectiveribbit.ui.components.CollectiblePlaceHolder
import pt.iade.games.detectiveribbit.ui.theme.DetectiveRibbitTheme

class Collectables : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DetectiveRibbitTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    innerPadding
                    CollectiblesPreview()
                }
            }
        }
    }
}

@Composable
fun Collectibles() {
    val context = LocalContext.current

    var collect by remember { mutableStateOf<Collectible?>(null) }
    var showPopup by remember { mutableStateOf(false) }

    // Create a list of collectibles with only one unlocked
    val collectibles = listOf(
        Collectible(
            id = 1,
            name = "Statue",
            image = R.drawable.ribbitstatue,
            description = "Statue of Ribbit, " +
                    "Found in the mafia Stackhouse.",
            placeholderSize = 1,
            isunlocked = true
        ),
        Collectible(
            id = 2,
            name = "aaaaaaaaaa",
            image = R.drawable.ribbitstatue,
            description = "bbbbbbbbbb " +
                    "bbbbbbbbbbbb",
            placeholderSize = 1,
            isunlocked = false
        ),
        Collectible(
            id = 3,
            name = "Statue",
            image = R.drawable.ribbitstatue,
            description = "Statue of Ribbit, " +
                    "Found in the mafia Stackhouse.",
            placeholderSize = 1,
            isunlocked = false
        ),
        Collectible(
            id = 4,
            name = "Statue",
            image = R.drawable.ribbitstatue,
            description = "Statue of Ribbit, " +
                    "Found in the mafia Stackhouse.",
            placeholderSize = 1,
            isunlocked = false
        ),
        Collectible(
            id = 5,
            name = "Statue",
            image = R.drawable.ribbitstatue,
            description = "Statue of Ribbit, " +
                    "Found in the mafia Stackhouse.",
            placeholderSize = 1,
            isunlocked = false
        )
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Display collectibles in a grid
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
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
        }
    }

    Button(
        onClick = {
            // Create an Intent to start the Collectables activity
            val intent = Intent(context, MainActivity::class.java)
            // Start the new activity
            context.startActivity(intent)
        }
    ) {
        Text(text = "Back")
    }

    if (collect != null && showPopup) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
        CollectibleInfo(collect!!, onClick = {
            showPopup = false
            collect = null
        })
            }
    }
}


@Preview(showBackground = true)
@Composable
fun CollectiblesPreview() {
    DetectiveRibbitTheme {
        Collectibles()
    }
}