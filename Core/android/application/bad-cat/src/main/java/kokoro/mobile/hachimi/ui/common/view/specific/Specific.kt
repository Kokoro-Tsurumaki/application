package kokoro.mobile.hachimi.ui.common.view.specific

import android.R.attr.padding
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kokoro.mobile.hachimi.R
import kokoro.mobile.hachimi.common.Modifier.scaffoldModifier
import kokoro.mobile.hachimi.common.nonScaledSp
import kokoro.mobile.hachimi.ui.PreviewContainer
import kokoro.mobile.hachimi.ui.features.welcome.WelcomeView

@Composable
fun HachimiFlag(modifier: Modifier = Modifier) {
    Row(modifier) {
        Image(
            modifier = Modifier.size(50.dp, 50.dp),
            painter = painterResource(id = R.drawable.ic_launcher),
            contentDescription = "はちみ"
        )
        Spacer(Modifier.width(20.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = "耄耋记账",
            fontSize = 20.nonScaledSp,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}



@Preview
@Composable
fun PreView() {
    PreviewContainer {
        HachimiFlag()
    }
}