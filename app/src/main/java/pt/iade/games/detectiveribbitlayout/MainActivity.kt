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
import androidx.fragment.app.FragmentContainer
import com.innoveworkshop.gametest.BowlingGameActivity
import com.innoveworkshop.plinko.PlinkoGameActivity
import pt.iade.games.detectiveribbitlayout.controllers.APIRequest
import pt.iade.games.detectiveribbitlayout.controllers.Saves
import pt.iade.games.detectiveribbitlayout.models.Player

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


            apiRequests.GetPlayerId(
                code = userInput.toInt(),
                onSuccess = { playerReceived ->
                    Log.v("MainActivity", playerReceived.toString())
                    saves.savePlayerToFile(this, playerReceived)
                    // Check again after saving player
                    progressButton.visibility = View.VISIBLE
                    evidenceButton.visibility = View.VISIBLE
                    collectablesButton.visibility = View.VISIBLE
                    // Hide the EditText and Send button after player is saved
                    textInput.visibility = View.GONE
                    sendButtonContainer.visibility = View.GONE
                    apiRequests.GetEvidences(
                        playerId = playerReceived.id,
                        onSuccess = {collectiblesReceived ->
                            Log.v("MainActivity", collectiblesReceived.toString())
                            saves.saveEvidencesToFile(this, collectiblesReceived)
                        },
                        onFailure = {}
                    )
                },
                onFailure = {}
            )
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

            apiRequests.GetEvidences(
                playerId = savedPlayer.id,
                onSuccess = {collectiblesReceived ->
                    Log.v("MainActivity", collectiblesReceived.toString())
                    saves.saveEvidencesToFile(this, collectiblesReceived)
                },
                onFailure = {}
            )
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
