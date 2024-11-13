package pt.iade.games.detectiveribbit.ui.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.iade.games.detectiveribbit.R
import pt.iade.games.detectiveribbit.models.Collectible
import androidx.compose.ui.platform.LocalContext

@Composable
fun CollectiblePlaceHolder(
    collectible: Collectible
){
    val context = LocalContext.current
    if (collectible.isunlocked) {
        Card(
            onClick = {
            }
        ) {
            Column(
                modifier = Modifier.padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = collectible.image),
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size((collectible.placeholderSize * 60).dp)
                        .padding(bottom = 4.dp)
                )
                Text(
                    text = collectible.name,
                    fontSize = (collectible.placeholderSize * 17).sp
                )
            }
        }
    }
    else{
        Card(
            onClick = {
                Toast.makeText(context, "You haven't unlocked this collectible", Toast.LENGTH_SHORT).show()
            }
        ) {
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
                        .padding(bottom = 4.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun CollectiblePlaceHolderPreview(){
    CollectiblePlaceHolder(collectible = Collectible(
        id = 1,
        name = "Statue",
        image = R.drawable.ribbitstatue,
        description = "Statue of Ribbit, " +
                "Found in the mafia Stackhouse.",
        placeholderSize = 1,
        isunlocked = false
    ))
}

