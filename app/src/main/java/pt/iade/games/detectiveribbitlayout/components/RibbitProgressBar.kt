package pt.iade.games.detectiveribbitlayout.components

import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import pt.iade.games.detectiveribbitlayout.ProgressActivity
import pt.iade.games.detectiveribbitlayout.R
import pt.iade.games.detectiveribbitlayout.controllers.Saves

@Composable
fun RibbitProgressBar() {
    val context = LocalContext.current

    val saves = Saves()
    // Load evidence data
    val evidences = saves.loadEvidencesFromFile(context)

    // Calculate progress based on the number of evidences
    val totalEvidences = 5 // or any other number based on your game logic
    val progress = evidences?.size?.toFloat()?.div(totalEvidences) ?: 0f
    // State to manage progress
    val currentProgress = remember { mutableStateOf(progress.coerceIn(0f, 1f)) }


    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val PurpleGrey40 = colorResource(id = R.color.purple)

    // Image size and vertical offset (modifiable)
    val ribbitImageSize = 40.dp
    val ribbitImageVerticalOffset = (-20).dp // Adjust image height above the bar

    // Dynamically calculate the image's horizontal position
    val ribbitImageProgress = (screenWidth - 40.dp) * currentProgress.value - (ribbitImageSize / 2)

    val plinkoButtonContainer: FrameLayout = (context as ProgressActivity).findViewById(R.id.PlinkoButtonContainer)
    val bowlingButtonContainer: FrameLayout = (context as ProgressActivity).findViewById(R.id.BowlingButtonContainer)

    // Initially hide the buttons
    plinkoButtonContainer.visibility = View.INVISIBLE
    bowlingButtonContainer.visibility = View.INVISIBLE

    // Load collectables from file
    val collectables = saves.loadCollectablesFromFile(context)

    val collectibleId1 = collectables?.find { it.id == 1 }
    val collectibleId2 = collectables?.find { it.id == 2 }


    // Check if the collectible with id 1 is over 50% progress
    val isCollectible1Over50 = collectibleId1?.isUnlocked == false && currentProgress.value >= 0.5f
    val isCollectible2Over1 = collectibleId2?.isUnlocked == false && currentProgress.value >= 1.0f

    // Make buttons visible when the progress reaches certain values
    if (currentProgress.value >= 0.5f && !isCollectible1Over50) {
        plinkoButtonContainer.visibility = View.VISIBLE
    }
    if (currentProgress.value >= 1.0f && !isCollectible2Over1) {
        bowlingButtonContainer.visibility = View.VISIBLE
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Box containing the progress bar and image
        Box(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .height(100.dp) // Ensure sufficient height for both image and progress bar
        ) {
            // Progress bar
            LinearProgressIndicator(
                progress = {
                    currentProgress.value.coerceIn(0f, 1f) // Clamp progress between 0 and 1
                },
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                color = PurpleGrey40,
            )

            // Ribbit image
            Image(
                painter = painterResource(R.drawable.ribbit),
                contentDescription = null,
                modifier = Modifier
                    .size(ribbitImageSize) // Adjust image size here
                    .offset(x = ribbitImageProgress, y = ribbitImageVerticalOffset) // Adjust position
                    .align(Alignment.CenterStart) // Start position relative to the Box
            )
        }

        // "Increase Progress" button
        Button(
            onClick = {
                // Increment progress by 0.1 (clamped between 0 and 1)
                if (currentProgress.value < 1.0f) {
                    currentProgress.value += 0.1f
                }
            }
        ) {
            Text(text = "Increase Progress")
        }
    }
}