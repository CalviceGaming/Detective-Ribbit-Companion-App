package pt.iade.games.detectiveribbit

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.iade.games.detectiveribbit.ui.components.RibbitProgressBar
import pt.iade.games.detectiveribbit.ui.theme.DetectiveRibbitTheme
import androidx.compose.ui.platform.LocalContext
import pt.iade.games.detectiveribbit.ui.EvidenceBoard


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
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally, // Center content horizontally
        verticalArrangement = Arrangement.Center,          // Center content vertically
        modifier = Modifier.fillMaxSize()
    ) {

        RibbitProgressBar();


        Spacer(modifier = Modifier.height(35.dp)) // Add space between the progress bar and the button


        // Button to go to the Collectables page
        Button(
            onClick = {
                // Create an Intent to start the Collectables activity
                val intent = Intent(context, Collectables::class.java)
                // Start the new activity
                context.startActivity(intent)
            }
        ) {
            Text(text = "Collectables")
        }



        // Button to go to the Puzzle page
        Button(
            onClick = {
                // Create an Intent to start the Collectables activity
                val intent = Intent(context, Puzzles::class.java)
                // Start the new activity
                context.startActivity(intent)
            }
        ) {
            Text(text = "Puzzle")
        }

        // Button to go to the Evidence Board page
        Button(
            onClick = {
                // Create an Intent to start the Collectables activity
                val intent = Intent(context, EvidenceBoard::class.java)
                // Start the new activity
                context.startActivity(intent)
            }
        ) {
            Text(text = "Evidence Board")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainViewPreview() {
    DetectiveRibbitTheme {
        MainView()
    }
}