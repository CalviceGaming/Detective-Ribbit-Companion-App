package pt.iade.games.detectiveribbit.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.iade.games.detectiveribbit.models.Collectible
import java.net.URI

@Composable
fun CollectiblePlaceHolder(
    collectible: Collectible
){
    Card(
        onClick = {
        }
    ){
        Column (modifier = Modifier.padding(5.dp)){
            Button(
                onClick = {}
            ) {Text(text = collectible.name)}
        }
    }
}

@Preview
@Composable
fun CollectiblePlaceHolderPreview(){
    CollectibleInfo(collectible = Collectible(
        id = 1,
        name = "Statue",
        image = URI.create("https://i.ytimg.com/vi/2S0XDjzfgO8/maxresdefault.jpg"),
        description = "Statue of Ribbit, " +
                "Found in the mafia Stackhouse.",
        placeholderSize = 1
    ))
}

@Composable
fun CollectibleInfo(
    collectible: Collectible
){
    Card() {
        Column(
            modifier = Modifier.padding(5.dp)
        ){
            Row {
                Text(
                    text = collectible.name,
                    modifier = Modifier.padding(bottom = 10.dp, top = 10.dp)
                        .weight(weight = 0.70f),
                    fontSize = 30.sp
                )
                Button(onClick = {},
                    ) { Text(text = "Close",
                        fontSize =10.sp,
                    )}
            }
            Text(text = collectible.description,
                minLines = 2,
                maxLines = 2)
        }
    }
}