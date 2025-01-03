package pt.iade.games.detectiveribbitlayout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import pt.iade.games.detectiveribbitlayout.controllers.APIRequest
import pt.iade.games.detectiveribbitlayout.controllers.Saves

class EvidanceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_evidance)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val rootView: View = findViewById(android.R.id.content)
        rootView.setBackgroundResource(R.drawable.background_evidance)
//back-menu button
        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val viewGroup:ViewGroup = findViewById(R.id.main)

        val saves = Saves()

        val savedCollectibles = saves.loadEvidencesFromFile(this)

        for (i in 0 until savedCollectibles!!.size){
            Log.v("EvidencesActivity", savedCollectibles[i].name)
        }

        for (i in 0 until viewGroup.childCount) {
            val child = viewGroup.getChildAt(i)
            if (child is ImageView && child !is ImageButton) {
                val tagValue = child.tag
                for (k in savedCollectibles.indices){
                    if (tagValue == savedCollectibles[k].name) {
                        // Make the ImageView visible if it matches a certain tag
                        child.visibility = View.VISIBLE
                        Log.d("ImageView Update", "$tagValue made visible")
                    }
                }
            }
        }


    }
}