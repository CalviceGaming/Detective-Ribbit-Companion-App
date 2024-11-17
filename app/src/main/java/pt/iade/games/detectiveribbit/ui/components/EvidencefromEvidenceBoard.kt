package pt.iade.games.detectiveribbit.ui.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.iade.games.detectiveribbit.R
import pt.iade.games.detectiveribbit.models.Collectible

@Composable
fun Evidence(
    collectible: Collectible,
    onClick: () -> Unit = {}
){
    if (collectible.isunlocked) {
        Card(
            onClick = onClick,
            modifier = Modifier.background(Color.Transparent)
        ) {
            Image(
                painter = painterResource(id = collectible.image),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size((collectible.placeholderSize * 60).dp)
            )
        }
    }
}

@Preview
@Composable
fun EvidencePreview(){
    Column {
        Evidence(
            collectible = Collectible(
                id = 1,
                name = "Photo",
                image = R.drawable.flymafiaphoto,
                description = "Photo of the family of the fly mafia boss.",
                placeholderSize = 1,
                isunlocked = true
            )
        )
        Evidence(
            collectible = Collectible(
                id = 1,
                name = "Gun",
                image = R.drawable.gun,
                description = "Gun used by the fly mafia",
                placeholderSize = 1,
                isunlocked = true
            )
        )
        Evidence(
            collectible = Collectible(
                id = 1,
                name = "Pipe",
                image = R.drawable.funnyactivepipe,
                description = "Pipe from the pipes puzzle",
                placeholderSize = 1,
                isunlocked = true
            )
        )
    }
}