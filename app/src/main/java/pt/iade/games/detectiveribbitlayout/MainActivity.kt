package pt.iade.games.detectiveribbitlayout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import pt.iade.games.detectiveribbitlayout.controllers.APIRequest
import pt.iade.games.detectiveribbitlayout.models.Collectible

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

        // Buttons setup
        val evidenceButton: ImageButton = findViewById(R.id.evidenceButton)
        evidenceButton.setOnClickListener {
            val intent = Intent(this, EvidanceActivity::class.java)
            startActivity(intent)
        }
        val collectablesButton: ImageButton = findViewById(R.id.collectablesButton)
        collectablesButton.setOnClickListener {
            val intent = Intent(this, CollectablesActivity::class.java)
            startActivity(intent)
        }
        val progressButton: ImageButton = findViewById(R.id.progressButton)
        progressButton.setOnClickListener {
            val intent = Intent(this, ProgressActivity::class.java)
            startActivity(intent)
        }

        val apiRequests = APIRequest()

        var collectibles: MutableList<Collectible> = mutableListOf()

        apiRequests.GetEvidences(
            onSuccess = {collectiblesRecived ->
                Log.v("MainActivity", collectiblesRecived[0].name)
                apiRequests.saveEvidencesToFile(this, collectiblesRecived)
            },
            onFailure = {}
        )
    }
}
