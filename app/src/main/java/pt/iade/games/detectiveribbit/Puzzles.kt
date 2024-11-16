package pt.iade.games.detectiveribbit

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import pt.iade.games.detectiveribbit.ui.theme.DetectiveRibbitTheme

class Puzzles : ComponentActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puzzles)

        // listeners
        findViewById<View>(R.id.image1).setOnTouchListener(DragTouchListener())
        findViewById<View>(R.id.image2).setOnTouchListener(DragTouchListener())
        findViewById<View>(R.id.image3).setOnTouchListener(DragTouchListener())
        findViewById<View>(R.id.image4).setOnTouchListener(DragTouchListener())

    }

    inner class DragTouchListener : View.OnTouchListener {
        private var dX = 0f
        private var dY = 0f

        override fun onTouch(view: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    dX = view.x - event.rawX
                    dY = view.y - event.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    view.animate()
                        .x(event.rawX + dX)
                        .y(event.rawY + dY)
                        .setDuration(0)
                        .start()
                }
            }
            return true
        }
    }
}

@Composable
fun Button() {
    val context = LocalContext.current

    // Button to increase progress
    Button(
        onClick = {
            // Create an Intent to start the Collectables activity
            val intent = Intent(context, MainActivity::class.java)
            // Start the new activity
            context.startActivity(intent)
        }
    ) {
        Text(text = "Back")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}