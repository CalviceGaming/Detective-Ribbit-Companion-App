package pt.iade.games.detectiveribbit.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.iade.games.detectiveribbit.R
import pt.iade.games.detectiveribbit.ui.theme.PurpleGrey40


@Composable

fun RibbitProgressBar(

){
    var currentProgress = 0.4f
    val size = LocalConfiguration.current
    val test = size.screenWidthDp
    var ribbitImageProgress = (10+((size.screenWidthDp-152) * currentProgress)).dp



    Column{
        //Text(text = "$test")
        Image(
            painter = painterResource(R.drawable.ribbit),
            contentDescription = null,
            modifier = Modifier
                .padding(start = ribbitImageProgress)
                .width(20.dp)
        )
        LinearProgressIndicator(
            progress = { currentProgress },
            color = PurpleGrey40,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressBarPreview(

){
    RibbitProgressBar()
}