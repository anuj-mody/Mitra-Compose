package id.kitabeli.mitra.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.kitabeli.mitra.ui.theme.BLACK
import id.kitabeli.mitra.ui.theme.appFontFamily

@Composable
fun AppTextField(
    modifier: Modifier? = Modifier,
    placeHolder: String,
    value: String,
    keyboardOptions: KeyboardOptions? = null,
    maxLines: Int? = null,
    onPressDone: (() -> Unit)? = null,
    onValueChange: (String) -> Unit
) {
    val colors = TextFieldDefaults.textFieldColors(
        focusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        backgroundColor = Color(0xFFF7F7F8),

        )
    TextField(
        modifier = modifier ?: Modifier,
        placeholder = {
            Text(
                text = placeHolder,
                fontSize = 20.sp,
                fontFamily = appFontFamily
            )
        },
        keyboardOptions = keyboardOptions ?: KeyboardOptions(keyboardType = KeyboardType.Text),
        value = value,
        onValueChange = {
            onValueChange.invoke(it)
        },
        textStyle = TextStyle(fontFamily = appFontFamily, color = BLACK, fontSize = 20.sp),
        colors = colors,
        shape = RoundedCornerShape(0.dp),
        maxLines = maxLines ?: 1,
        singleLine = maxLines == 1,
        keyboardActions = KeyboardActions(onDone = {
            onPressDone?.invoke()
        })

    )
}