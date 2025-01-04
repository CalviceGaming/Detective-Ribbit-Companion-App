package com.innoveworkshop.plinko

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.innoveworkshop.gametest.BowlingGameActivity
import com.innoveworkshop.plinko.assets.Multiplier
import com.innoveworkshop.plinko.assets.PlinkoBall
import com.innoveworkshop.plinko.engine.Circle
import com.innoveworkshop.plinko.engine.GameObject
import com.innoveworkshop.plinko.engine.GameSurface
import com.innoveworkshop.plinko.engine.Vector
import pt.iade.games.detectiveribbitlayout.MainActivity
import pt.iade.games.detectiveribbitlayout.R
import pt.iade.games.detectiveribbitlayout.controllers.APIRequest
import pt.iade.games.detectiveribbitlayout.controllers.Saves
import pt.iade.games.detectiveribbitlayout.models.Collectible
import kotlin.math.sqrt
import kotlin.random.Random

class PlinkoGameActivity : AppCompatActivity() {
    protected var gameSurface: GameSurface? = null
    protected var upButton: Button? = null
    protected var game: Game? = null
    val maxLines = 7
    val numberOfObs = (maxLines*(maxLines+1))/2
    var ListOfObstacles: MutableList<Circle?> = MutableList(numberOfObs) {null}
    var ListOfMultipliers: MutableList<Multiplier?> = MutableList(10) {null}
    var ListOfBallsinJaw: MutableList<PlinkoBall?> = MutableList(50) {null}
    var iBall = 0
    var peso = 25f
    var pesoText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plinko)

        pesoText = findViewById<TextView>(R.id.PesoText)
        gameSurface = findViewById<View>(R.id.gameSurface) as GameSurface
        game = Game()
        gameSurface!!.setRootGameObject(game)

    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                if (iBall == 50){
                    iBall = 0
                }
                val randomValue =
                    Random.nextInt((gameSurface!!.width / 2) - 100, (gameSurface!!.width / 2 + 100))

                ListOfBallsinJaw[iBall] = PlinkoBall(randomValue.toFloat(), 20f, ListOfObstacles, ListOfMultipliers)
                gameSurface!!.addGameObject(ListOfBallsinJaw[iBall]!!)
                iBall++
                peso -= 5f
                if (peso < 0f){
                    val intent = Intent(this@PlinkoGameActivity, PlinkoGameActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
        return true
    }


    fun Obstables(surface: GameSurface?, ListOfObstacles: MutableList<Circle?>):MutableList<Circle?>{
        var line = 1
        var balls = 0
        var ballsInLine = 0
        val initialY = surface!!.height.toFloat() * (1/4f)
        val surfaceWidth = surface.width.toFloat()
        val d = 250f
        //val ballX = initialX + (ballsInLine * d)
        while (line <= maxLines) {
            val initialX = (surfaceWidth - ((line - 1 ) * d))/2
            while (ballsInLine < line) {
                val ballY = initialY + (sqrt((d * d) - ((d / 2) * (d / 2))) * line)
                val ballX = initialX + (ballsInLine * d)

                ListOfObstacles[balls] = Circle(
                    ballX,
                    ballY,
                    30f,
                    Color.BLUE,
                    Vector(0f, 0f)
                )

                surface.addGameObject(ListOfObstacles[balls]!!)
                balls++
                ballsInLine++
            }
            ballsInLine = 0
            line++
        }
        return ListOfObstacles
    }

    fun Multipliers(surface: GameSurface?, ListOfMultipliers: MutableList<Multiplier?>): MutableList<Multiplier?>{
        var i = 0
        var height = 10f
        var width = gameSurface!!.width.toFloat()/10
        while (i < ListOfMultipliers.size){
            var color = Color.YELLOW
            var multiplier = 10f
            if (i < 4 || i > 5){
                multiplier = 2f
                color = Color.MAGENTA
            }
            if (i < 3 || i > 6){
                multiplier = 1f
                color = Color.GREEN
            }
            if (i < 2 || i > 7){
                multiplier = 0.2f
                color = Color.RED
            }
            Log.d("Mult: ", multiplier.toString())
            ListOfMultipliers[i] = Multiplier(Vector((width/2) + width*i,gameSurface!!.height - height/2),width, height, color, multiplier)
            gameSurface!!.addGameObject(ListOfMultipliers[i]!!)
            i++
        }
        return ListOfMultipliers
    }

    inner class Game : GameObject() {
        override fun onStart(surface: GameSurface?) {
            super.onStart(surface)
            ListOfObstacles = Obstables(surface, ListOfObstacles)
            ListOfMultipliers = Multipliers(surface, ListOfMultipliers)
        }

        override fun onFixedUpdate() {
            super.onFixedUpdate()
            pesoText?.setText("${peso.toInt()}$")
            var i = 0
            while (i < ListOfBallsinJaw.size){
                if(ListOfBallsinJaw[i] != null){
                    if (ListOfBallsinJaw[i]!!.isFloored && ListOfBallsinJaw[i]!!.mulaCounted == 1){
                        peso += ListOfBallsinJaw[i]!!.mula
                        ListOfBallsinJaw[i]!!.mulaCounted = 0
                    }
                }
                i++
            }



            // Log a message when peso exceeds $60
            if (peso >= 60f) {
                val apiRequests = APIRequest()
                val saves = Saves()
                // Fetch the saved player
                val savedPlayer = saves.loadPlayerFromFile(this@PlinkoGameActivity)

                // Create the hard-coded collectible
                val hardCodedCollectable = Collectible(1, "Statue", R.drawable.ribbitstatue, "Found in the mafia Stackhouse.", 1, false)

                // Check if the player ID is valid
                if (savedPlayer!!.id != 0) {
                    apiRequests.PostCollectibles(
                        playerId = savedPlayer.id,
                        collectible = hardCodedCollectable,
                        onSuccess = {
                            // Safely modify and save the list of collectibles
                            val updatedCollectibles = saves.loadCollectablesFromFile(this@PlinkoGameActivity)?.toMutableList() ?: mutableListOf()
                            updatedCollectibles.add(hardCodedCollectable)
                            saves.saveCollectablesToFile(this@PlinkoGameActivity, updatedCollectibles)
                            // Start the MainActivity after successfully posting the collectible
                            val intent = Intent(this@PlinkoGameActivity, MainActivity::class.java)
                            startActivity(intent)
                        },
                        onFailure = {
                            Log.d("CollectablesActivity", "Failed to post collectible.")
                        }
                    )
                } else {
                    Log.d("CollectablesActivity", "There is no playerId")
                }
            }
        }
    }
}