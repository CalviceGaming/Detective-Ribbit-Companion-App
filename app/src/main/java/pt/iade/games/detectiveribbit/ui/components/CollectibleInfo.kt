package pt.iade.games.detectiveribbit.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.iade.games.detectiveribbit.models.Collectible
import java.net.URI

@Composable

fun CollectibleInfo(
    collectible: Collectible
){
    Card() {
        Column(
            modifier = Modifier.padding(5.dp)
            ){
            Text(text = collectible.name)
            Text(text = collectible.description)
        }
    }
}

@Composable
@Preview
fun CollectibleInfoPreview(){
    CollectibleInfo(collectible = Collectible(
        id = 1,
        name = "Statue",
        image = URI.create("https://i.ytimg.com/vi/2S0XDjzfgO8/maxresdefault.jpg"),
        description = "Statue of Ribbit",
        placeholderSize = 1
    ))
}