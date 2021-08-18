package id.kitabeli.mitra.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    btnText: String,
    enabled: Boolean = true,
    showProgress: Boolean = false,
    onClick: () -> Unit
) {

    if (showProgress) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier.size(30.dp, 30.dp),
                color = MaterialTheme.colors.primary
            )
        }

    } else {
        Button(
            modifier = modifier,
            onClick = onClick,
            enabled = enabled,
        ) {
            Text(
                text = btnText,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            )
        }
    }
}