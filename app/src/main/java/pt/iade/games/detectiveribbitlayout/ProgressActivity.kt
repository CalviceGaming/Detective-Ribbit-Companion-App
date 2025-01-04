package pt.iade.games.detectiveribbitlayout

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import com.innoveworkshop.gametest.BowlingGameActivity
import com.innoveworkshop.plinko.PlinkoGameActivity
import pt.iade.games.detectiveribbitlayout.components.RibbitProgressBar

class ProgressActivity : AppCompatActivity() {

    var minigame1 = false
    var startBowlingMinigame = true

    //@RequiresApi(Build.VERSION_CODES.Q)
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

        val plinkoButtonContainer: FrameLayout = findViewById(R.id.PlinkoButtonContainer)
        val bowlingButtonContainer: FrameLayout = findViewById(R.id.BowlingButtonContainer)

        // Initially set buttons to invisible
        plinkoButtonContainer.visibility = View.INVISIBLE
        bowlingButtonContainer.visibility = View.INVISIBLE

        // Set up the ComposeView to display the RibbitProgressBar
        val composeView = findViewById<ComposeView>(R.id.compose_view)
        composeView.setContent {
            RibbitProgressBar()
        }


        val openPlinkoMinigame = Intent(this, PlinkoGameActivity::class.java)
        val plinkoButton: ImageButton = findViewById(R.id.openPlinkoButton)
        plinkoButton.setOnClickListener {
            startActivity(openPlinkoMinigame)
        }


        val openBowlingMinigame = Intent(this, BowlingGameActivity::class.java)
        val bowlingButton: ImageButton = findViewById(R.id.openBowlingButton)
        bowlingButton.setOnClickListener {
            startActivity(openBowlingMinigame)
        }

        //StartMiniGame()
    }

/*
    private fun StartMiniGame(){
        if(minigame1 == true) {
            val intent = Intent(this, PlinkoGameActivity::class.java)
            startActivity(intent)
            minigame1 = false
        }
        if(startBowlingMinigame == true) {
            val intent = Intent(this, BowlingGameActivity::class.java)
            startActivity(intent)
            startBowlingMinigame = false
        }
    }*/
}