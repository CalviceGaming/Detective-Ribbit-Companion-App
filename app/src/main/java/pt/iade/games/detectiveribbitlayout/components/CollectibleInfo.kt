package pt.iade.games.detectiveribbitlayout.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.iade.games.detectiveribbitlayout.R
import pt.iade.games.detectiveribbitlayout.models.Collectible

@Composable
fun CollectibleInfo(
    collectible: Collectible,
    onClick: () -> Unit = {}
){
    Box(
        modifier = Modifier
            .background(Color.Gray)
            .width(LocalConfiguration.current.screenWidthDp.dp / 2)
    ) {
        Column(
            modifier = Modifier.padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(
                    text = collectible.name,
                    modifier = Modifier.padding(bottom = 10.dp, top = 10.dp),
                    fontSize = 30.sp
                )
                Button(
                    onClick = onClick,
                    modifier = Modifier.padding(start = 30.dp)
                ) {
                    Text(
                        text = "X",
                        fontSize = 10.sp,
                    )
                }
            }
            Image(
                painter = painterResource(id = collectible.image),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size((collectible.placeholderSize * 60).dp)
                    .padding(bottom = 4.dp)
            )
            Text(text = collectible.description)
        }
    }
}