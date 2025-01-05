

package pt.iade.games.detectiveribbitlayout.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.paint
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.iade.games.detectiveribbitlayout.R
import pt.iade.games.detectiveribbitlayout.models.Collectible

@Composable
fun CollectibleInfo(
    collectible: Collectible,
    onClick: () -> Unit = {}
) {
    val backgroundPainter: Painter = painterResource(id = R.drawable.popup_background)
    val customFont = FontFamily(Font(R.font.typewriter_font))
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Box(
        modifier = Modifier
            .paint(painter = backgroundPainter)
            .width(LocalConfiguration.current.screenWidthDp.dp / 2)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(

                modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp), // Add padding here
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = collectible.name,
                    fontSize = 30.sp,
                    fontFamily = customFont,
                    modifier = Modifier.weight(1f)
                )
                TextButton(
                    onClick = onClick,
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Text(
                        text = "X",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = collectible.image),
                contentDescription = null,
                modifier = Modifier
                    .size((collectible.placeholderSize * 80).dp)
                    .align(Alignment.CenterHorizontally)
                    .size(width = screenWidth / 3, height = (screenWidth / 3) * collectible.placeholderSize)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = collectible.description,
                fontFamily = customFont,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(15.dp)  // Adds 15 dp padding on all sides
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}
