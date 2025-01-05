package pt.iade.games.detectiveribbitlayout.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import pt.iade.games.detectiveribbitlayout.R
import pt.iade.games.detectiveribbitlayout.models.Collectible


@Composable
fun CollectiblePlaceHolder(
    modifier: Modifier = Modifier,
    collectible: Collectible,
    onClick: () -> Unit = {}
){
    val customFont = FontFamily(Font(R.font.typewriter_font))
    Box(
        modifier = modifier
            .clickable { onClick() }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        val context = LocalContext.current
        if (collectible.isUnlocked) {
            Card(
                onClick = onClick,
                shape = RectangleShape
            ) {
                Box (
                    contentAlignment = Alignment.Center, // Center all content inside the Box
                ){
                    // Background image
                    Image(
                        painter = painterResource(id = R.drawable.boxone),
                        contentDescription = "Card Background",
                        modifier = Modifier
                            .size((collectible.placeholderSize * 90).dp),
                        contentScale = ContentScale.Crop // Crop to fill the area proportionally
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = collectible.image),
                            contentDescription = "",
                            modifier = Modifier
                                .size((collectible.placeholderSize * 60).dp)
                                .padding(bottom = 4.dp)
                        )
                        Text(

                            //fontFamily = customFont,
                            text = collectible.name,
                            fontSize = (collectible.placeholderSize * 17).sp
                        )
                    }
                }
            }
        } else {
            Card(
                onClick = {
                    Toast.makeText(
                        context,
                        "You haven't unlocked this collectible",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                shape = RectangleShape

            ) {
                Box (
                    contentAlignment = Alignment.Center // Center all content inside the Box
                ) {
                    // Background image
                    Image(
                        painter = painterResource(id = R.drawable.boxone),
                        contentDescription = "Card Background",
                        modifier = Modifier
                            .size((collectible.placeholderSize * 90).dp),
                        contentScale = ContentScale.Crop // Crop to fill the area proportionally
                    )
                    Column(
                        modifier = Modifier.padding(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.lock),
                            contentDescription = "",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .size((collectible.placeholderSize * 60).dp)
                        )
                        Text(
                            text = "",
                            fontSize = (collectible.placeholderSize * 17).sp
                        )
                    }
                }
            }
        }
    }
}