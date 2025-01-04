package pt.iade.games.detectiveribbitlayout

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.innoveworkshop.gametest.BowlingGameActivity
import com.innoveworkshop.plinko.PlinkoGameActivity
import pt.iade.games.detectiveribbitlayout.components.RibbitProgressBar

class ProgressActivity : AppCompatActivity(), SensorEventListener {
    private var sensorManager: SensorManager? = null
    private var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f
    var currentSteps = 0

    var minigame1 = false
    var minigame2 = false

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_progress)
        checkAndRequestPermission()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val rootView: View = findViewById(android.R.id.content)
        rootView.setBackgroundResource(R.drawable.background_progress)

        // Configure the back button
        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Set up the ComposeView to display the RibbitProgressBar
        val composeView = findViewById<ComposeView>(R.id.compose_view)
        composeView.setContent {
            RibbitProgressBar(currentSteps) // Your Composable function
        }
    }

    override fun onResume() {
        super.onResume()
        running = true
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null){
            Toast.makeText(this, "No Sensor", Toast.LENGTH_SHORT).show()
        }
        else{
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(running){
            totalSteps = event!!.values[0]
            currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
            if (currentSteps >= 100){
                resetSteps()
                StartMiniGame()
            }
        }
    }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }


    private fun saveData(){
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("key1", previousTotalSteps)
        editor.apply()
    }

    private fun loadData(){
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val savedNumber = sharedPreferences.getFloat("key1", 0f)
        previousTotalSteps = savedNumber
    }

    private fun resetSteps(){
        currentSteps = 0
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun checkAndRequestPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACTIVITY_RECOGNITION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                1
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun StartMiniGame(){
        if(minigame1 == false) {
            val intent = Intent(this, PlinkoGameActivity::class.java)
            startActivity(intent)
            minigame1 = true
        }
        if(minigame2 == false) {
            val intent = Intent(this, BowlingGameActivity::class.java)
            startActivity(intent)
            minigame2 = true
        }
    }
}