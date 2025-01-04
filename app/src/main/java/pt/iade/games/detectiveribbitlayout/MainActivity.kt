package pt.iade.games.detectiveribbitlayout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import pt.iade.games.detectiveribbitlayout.controllers.APIRequest
import pt.iade.games.detectiveribbitlayout.controllers.Saves
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setting background
        val rootView: View = findViewById(android.R.id.content)
        rootView.setBackgroundResource(R.drawable.homepage_background)

        // Buttons
        val progressButton: ImageButton = findViewById(R.id.progressButton)
        val evidenceButton: ImageButton = findViewById(R.id.evidenceButton)
        val collectablesButton: ImageButton = findViewById(R.id.collectablesButton)
        val sendButtonContainer: FrameLayout = findViewById(R.id.sendButtonContainer)

        val sendButton: ImageButton = findViewById(R.id.sendButton)
        val textInput: EditText = findViewById(R.id.codeInput)
        val saves = Saves()
        // API call to get player data
        val apiRequests = APIRequest()


        sendButton.setOnClickListener {
            val userInput = textInput.text.toString()
            Log.d("MainActivity", "User input: $userInput")

            CoroutineScope(Dispatchers.Main).launch {
                val player = apiRequests.getPlayerId(userInput.toInt())
                if (player != null) {
                    saves.savePlayerToFile(this@MainActivity, player)
                    progressButton.visibility = View.VISIBLE
                    evidenceButton.visibility = View.VISIBLE
                    collectablesButton.visibility = View.VISIBLE
                    textInput.visibility = View.GONE
                    sendButtonContainer.visibility = View.GONE

                    val evidences = apiRequests.getEvidences(player.id)
                    saves.saveEvidencesToFile(this@MainActivity, evidences)
                } else {
                    Log.e("MainActivity", "Failed to fetch player")
                }
            }
        }

        // Check if player exists
        val savedPlayer = saves.loadPlayerFromFile(this)  // Check if player is saved

        if (savedPlayer != null) {
            // If player exists, show the evidence and collectables buttons, hide the EditText and Send button
            progressButton.visibility = View.VISIBLE
            evidenceButton.visibility = View.VISIBLE
            collectablesButton.visibility = View.VISIBLE
            textInput.visibility = View.GONE
            sendButtonContainer.visibility = View.GONE


            CoroutineScope(Dispatchers.Main).launch {
                try {
                    // Perform the network call on a background thread
                    val evidencesReceived = apiRequests.getEvidences(playerId = savedPlayer.id)

                    // Handle success on the main thread
                    Log.v("MainActivity", evidencesReceived.toString())
                    saves.saveEvidencesToFile(this@MainActivity, evidencesReceived)
                } catch (e: Exception) {
                    // Handle failure here (e.g., log error or show user feedback)
                    Log.e("MainActivity", "Failed to fetch evidences: ${e.message}")
                }
            }

        } else {
            // If player doesn't exist, hide the evidence and collectables buttons
            progressButton.visibility = View.GONE
            evidenceButton.visibility = View.GONE
            collectablesButton.visibility = View.GONE
        }

        progressButton.setOnClickListener {
            val intent = Intent(this, ProgressActivity::class.java)
            startActivity(intent)
        }

        // Set up buttons' click listeners
        evidenceButton.setOnClickListener {
            val intent = Intent(this, EvidanceActivity::class.java)
            startActivity(intent)
        }

        collectablesButton.setOnClickListener {
            val intent = Intent(this, CollectablesActivity::class.java)
            startActivity(intent)
        }
    }
}
