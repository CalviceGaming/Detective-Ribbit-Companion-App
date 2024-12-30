package pt.iade.games.detectiveribbitlayout

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import pt.iade.games.detectiveribbitlayout.components.RibbitProgressBar

class ProgressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_progress)

        val rootView: View = findViewById(android.R.id.content)
        rootView.setBackgroundResource(R.drawable.background_progress)

        // Configure the back button
        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Set up the ComposeView to display the RibbitProgressBar
        val composeView = findViewById<ComposeView>(R.id.compose_view)
        composeView.setContent {
            RibbitProgressBar() // Your Composable function
        }
    }
}