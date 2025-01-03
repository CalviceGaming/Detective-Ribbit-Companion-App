package pt.iade.games.detectiveribbitlayout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import pt.iade.games.detectiveribbitlayout.components.CollectiblesComposable
import pt.iade.games.detectiveribbitlayout.controllers.APIRequest
import pt.iade.games.detectiveribbitlayout.models.Collectible

class CollectablesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_collectables)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //setting background
        val rootView: View = findViewById(android.R.id.content)
        rootView.setBackgroundResource(R.drawable.homepage_background)

        // Handle back button
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Set up ComposeView for collectibles
        val composeView = findViewById<ComposeView>(R.id.composeView)
        composeView.setContent {
            CollectiblesComposable()
        }

        val apiRequests = APIRequest()

        apiRequests.PostCollectibles(
            playerId = 1,
            collectible = Collectible(1, "Statue", R.drawable.ribbitstatue, "Found in the mafia Stackhouse.", 1, false),
            onSuccess = {},
            onFailure = {}
        )
    }
}
