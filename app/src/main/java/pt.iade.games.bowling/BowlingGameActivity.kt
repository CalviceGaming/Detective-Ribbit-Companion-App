package com.innoveworkshop.gametest

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.innoveworkshop.gametest.assets.BowlingBall
import com.innoveworkshop.gametest.assets.Pin
import com.innoveworkshop.gametest.engine.GameObject
import com.innoveworkshop.gametest.engine.GameSurface
import com.innoveworkshop.gametest.engine.Vector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.iade.games.detectiveribbitlayout.MainActivity
import pt.iade.games.detectiveribbitlayout.R
import pt.iade.games.detectiveribbitlayout.controllers.APIRequest
import pt.iade.games.detectiveribbitlayout.controllers.Saves
import pt.iade.games.detectiveribbitlayout.models.Collectible
import kotlin.math.absoluteValue
import kotlin.math.sqrt

class BowlingGameActivity : AppCompatActivity() {
    private var gameSurface: GameSurface? = null
    private var controlsLayout: ConstraintLayout? = null
    private var scoreTxt: TextView? = null
    private var game: Game? = null
    private var bowlingBall: BowlingBall? = null
    private var initialTouch: Vector? = null
    private var hasLaunch : Boolean = false

    @SuppressLint("ClickableViewAccessibility", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bowling)
        gameSurface = findViewById<View>(R.id.bowlingSurface) as GameSurface

        game = Game()
        gameSurface!!.setRootGameObject(game)


        controlsLayout = findViewById<View>(R.id.controls_layout) as ConstraintLayout
        controlsLayout!!.setOnTouchListener { _, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialTouch = Vector(event.x, event.y)
                    Log.i("ACTION DOWN", "Touch started at: (${event.x}, ${event.y})")
                }
                MotionEvent.ACTION_UP -> {
                    if (initialTouch != null && !hasLaunch) {
                        val releaseTouch = Vector(event.x, event.y)
                        val forceVector = Vector(
                            releaseTouch.x - initialTouch!!.x,
                            releaseTouch.y - initialTouch!!.y
                        )
                        // Reverse the force vector
                        val reversedForce = forceVector.reverse()
                        Log.i("VECTOR", "Applied force: (${reversedForce.x}, ${reversedForce.y})")

                        // Apply the reversed force to the circle
                        bowlingBall?.applyForce(reversedForce)
                        hasLaunch = true
                    }
                }
            }
            true
        }
    }

    inner class Game : GameObject() {
        private var pins: MutableList<Pin?>? = null
        private var initialPositionX : Float = 0f
        private var initialPositionY : Float = 0f
        private var numberOfTries: Int = 0
        private var hasGivenCollectible = false
        override fun onStart(surface: GameSurface?) {
            super.onStart(surface)
            initialPositionX = (surface!!.width.toFloat())/2f
            initialPositionY = surface.height.toFloat() - 200f


            // Create the circle and store it in the global variable
            bowlingBall = BowlingBall(
                initialPositionX, // Center horizontally
                initialPositionY, // Bottom of the screen, considering the radius
                100f, // Radius of the ball
                Color.rgb(128, 14, 80),
                10f,
                this@BowlingGameActivity
            )
            // Add the circle to the surface
            surface.addGameObject(bowlingBall!!)

            pins = Obstacles(surface);


        }

        override fun onFixedUpdate() {
            super.onFixedUpdate()
            if (bowlingBall!!.hitRightWall() || bowlingBall!!.hitLeftWall() || bowlingBall!!.hitTopWall() ||bowlingBall!!.hitBottomWall()){
                ResetBowlingBall()
            }
            // Check if score has reached 10 and give the collectible if not already done
            if (bowlingBall!!.score >= 10 && !hasGivenCollectible) {
                hasGivenCollectible = true // Ensure it's only given once
                val apiRequests = APIRequest()
                val saves = Saves()
                // Fetch the saved player
                val savedPlayer = saves.loadPlayerFromFile(this@BowlingGameActivity)

                // Create the hard-coded collectible
                val hardCodedCollectable = Collectible(2, "Bowling Pin", R.drawable.bowling_pin, "A Bowling Pin used to kill someone", 1, false)

                // Check if the player ID is valid
                if (savedPlayer!!.id != 0) {
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            // Perform the network call on a background thread
                            apiRequests.postCollectibles(playerId = savedPlayer.id, collectible = hardCodedCollectable)

                            // Safely modify and save the list of collectibles after the network call succeeds
                            val updatedCollectibles = saves.loadCollectablesFromFile(this@BowlingGameActivity)?.toMutableList() ?: mutableListOf()
                            updatedCollectibles.add(hardCodedCollectable)
                            saves.saveCollectablesToFile(this@BowlingGameActivity, updatedCollectibles)

                            // Start the MainActivity after successfully posting the collectible
                            val intent = Intent(this@BowlingGameActivity, MainActivity::class.java)
                            startActivity(intent)
                        } catch (e: Exception) {
                            // Handle failure (log or show user feedback)
                            Log.e("CollectablesActivity", "Failed to post collectible: ${e.message}")
                        }
                    }
                } else {
                    Log.d("CollectablesActivity", "There is no playerId")
                }
            }
            val iterator = pins!!.iterator()
            while (iterator.hasNext()) {
                val pin = iterator.next()
                val a = bowlingBall!!.CollideWithPin(pin!!)
                if (a) {
                    iterator.remove() // Safely remove the pin
                }
            }
        }
        fun ResetBowlingBall(){
            bowlingBall!!.velocity.x = 0f
            bowlingBall!!.velocity.y = 0f
            bowlingBall!!.position.x = initialPositionX
            bowlingBall!!.position.y = initialPositionY
            numberOfTries++
            hasLaunch = false

            if (numberOfTries >= 3) {
                // Destroy the bowling ball and restart the game
                bowlingBall!!.destroy()

                // Restart the activity (this will restart the game)
                val intent = Intent(this@BowlingGameActivity, BowlingGameActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun Obstacles(surface: GameSurface?): MutableList<Pin?> {
        val listOfObstacles = mutableListOf<Pin?>() // Create a mutable list of Circle objects
        var line = 1
        var ballsInLine = 0
        val surfaceWidth = surface!!.width.toFloat()
        val d = 100 // Distance between pins
        val initialY = surface.height.toFloat() * (1 / 4f) // Start from the bottom (1/4 of the screen height)

        while (line <= 4) {
            // Adjust the initial X position for centering each row
            val initialX = (surfaceWidth - ((line - 1) * d)) / 2

            while (ballsInLine < line) {
                // Calculate the X position as before
                val ballX = initialX + (ballsInLine * d)
                // Reverse the Y position by subtracting the row offset from the initial Y
                val ballY = initialY - (sqrt((d * d - ((d / 2) * (d / 2))).toDouble()) * line).toFloat()

                // Create a new Pin object
                val pin = Pin(
                    ballX,
                    ballY,
                    30f, // Radius of the pin
                    Color.WHITE // Pin color
                )

                // Add the Pin to the list and to the GameSurface
                listOfObstacles.add(pin)
                surface.addGameObject(pin)

                ballsInLine++
            }
            ballsInLine = 0 // Reset the ballsInLine for the next row
            line++
        }
        return listOfObstacles
    }
}