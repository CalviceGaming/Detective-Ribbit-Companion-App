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
import pt.iade.games.detectiveribbitlayout.controllers.Saves
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

        val apiRequests = APIRequest()
        val saves = Saves()

        val savedCollectibles = saves.loadCollectablesFromFile(this)
        Log.v("CollectablesActivity", savedCollectibles.toString())

        // Set up ComposeView for collectibles
        val composeView = findViewById<ComposeView>(R.id.composeView)
        composeView.setContent {
            CollectiblesComposable(savedCollectibles!!)
        }


        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //THIS IS NOT TO BE HERE, IT WILL BE AT THE END OF THE MINI GAME
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        val button: ImageButton = findViewById(R.id.addCollectable) // Replace with your button's ID

        button.setOnClickListener {
            // Fetch the saved player
            val savedPlayer = saves.loadPlayerFromFile(this)

            // Create the hard-coded collectible
            val hardCodedCollectable = Collectible(1, "Statue", R.drawable.ribbitstatue, "Found in the mafia Stackhouse.", 1, false)

            // Check if the player ID is valid
            if (savedPlayer!!.id != 0) {
                apiRequests.PostCollectibles(
                    playerId = savedPlayer.id,
                    collectible = hardCodedCollectable,
                    onSuccess = {
                        // Safely modify and save the list of collectibles
                        val updatedCollectibles = saves.loadCollectablesFromFile(this)?.toMutableList() ?: mutableListOf()
                        updatedCollectibles.add(hardCodedCollectable)
                        saves.saveCollectablesToFile(this, updatedCollectibles)
                    },
                    onFailure = {
                        Log.d("CollectablesActivity", "Failed to post collectible.")
                    }
                )
            } else {
                Log.d("CollectablesActivity", "There is no playerId")
            }
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
}
