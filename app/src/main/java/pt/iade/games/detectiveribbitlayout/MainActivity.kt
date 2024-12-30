package pt.iade.games.detectiveribbitlayout

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
//setting background
        val rootView: View = findViewById(android.R.id.content)
        rootView.setBackgroundResource(R.drawable.homepage_background)
//<editor-fold desc="Buttons setup">
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
            val intent = Intent(this, ProgressActivity ::class.java)
            startActivity(intent)
        }
//</editor-fold>
    }
}