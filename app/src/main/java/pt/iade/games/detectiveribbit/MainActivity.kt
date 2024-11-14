package pt.iade.games.detectiveribbit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pt.iade.games.detectiveribbit.models.Collectible
import pt.iade.games.detectiveribbit.ui.components.CollectibleInfo
import pt.iade.games.detectiveribbit.ui.components.CollectiblePlaceHolder
import pt.iade.games.detectiveribbit.ui.theme.DetectiveRibbitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DetectiveRibbitTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    innerPadding
                    MainView()
                }
            }
        }
    }
}

@Composable
fun MainView() {
    var collect by remember { mutableStateOf<Collectible?>(null) }
    var showPopup by remember { mutableStateOf(false) }

    CollectiblePlaceHolder(
        collectible = Collectible(
            id = 1,
            name = "Statue",
            image = R.drawable.ribbitstatue,
            description = "Statue of Ribbit, " +
                    "Found in the mafia Stackhouse.",
            placeholderSize = 1,
            isunlocked = true
        ),
        onClick = {
            showPopup = true
            collect = Collectible(
                id = 1,
                name = "Statue",
                image = R.drawable.ribbitstatue,
                description = "Statue of Ribbit, " +
                        "Found in the mafia Stackhouse.",
                placeholderSize = 1,
                isunlocked = true,

            )
        }
    )
    if ((collect != null) && showPopup)
        CollectibleInfo(collect!!,
            onClick = {
                showPopup = false
                collect = null
            })
}

@Preview(showBackground = true)
@Composable
fun MainViewPreview() {
    DetectiveRibbitTheme {
        MainView()
    }
}