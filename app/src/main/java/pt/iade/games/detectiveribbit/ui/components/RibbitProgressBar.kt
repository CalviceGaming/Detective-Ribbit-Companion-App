package pt.iade.games.detectiveribbit.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.iade.games.detectiveribbit.R
import pt.iade.games.detectiveribbit.ui.theme.PurpleGrey40


@Composable
fun RibbitProgressBar() {
    // State to manage progress
    val currentProgress = remember { mutableStateOf(0f) }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    // Image size and vertical offset (modifiable)
    val ribbitImageSize = 40.dp
    val ribbitImageVerticalOffset = (-20).dp // Adjust image height above the bar

    // Dynamically calculate the image's horizontal position
    val ribbitImageProgress = (screenWidth - 40.dp) * currentProgress.value - (ribbitImageSize / 2)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Box containing the progress bar and image
        Box(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .height(100.dp) // Ensure sufficient height for both image and progress bar
        ) {
            // Progress bar
            LinearProgressIndicator(
                progress = currentProgress.value.coerceIn(0f, 1f), // Clamp progress between 0 and 1
                color = PurpleGrey40,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
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

@Preview(showBackground = true)
@Composable
fun ProgressBarPreview(

){
    RibbitProgressBar()
}