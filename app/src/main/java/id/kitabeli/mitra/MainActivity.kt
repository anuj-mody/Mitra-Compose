package id.kitabeli.mitra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import id.kitabeli.mitra.navigation.MitraAppNavigation
import id.kitabeli.mitra.ui.theme.NewMitraAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewMitraAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.surface) {
                    MitraAppNavigation()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(
        text = "Helloooo $name!",
        color = MaterialTheme.colors.primary
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NewMitraAppTheme {
        Column() {
            Greeting("Android")
            Greeting("Android")
            Greeting("Android")
        }

    }
}