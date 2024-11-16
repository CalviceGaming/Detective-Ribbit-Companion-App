package pt.iade.games.detectiveribbit

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class PuzzlesActivity : AppCompatActivity() {

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