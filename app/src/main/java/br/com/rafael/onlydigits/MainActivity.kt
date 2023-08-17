package br.com.rafael.onlydigits

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.rafael.onlydigits.ui.theme.OnlyDigitsTheme
import br.com.rafael.onlydigts.component.OnlyDigitsApp
import br.com.rafael.onlydigts.viewModel.ClipboardViewModel


class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<ClipboardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.action != null &&
            intent.action.equals(Intent.ACTION_PROCESS_TEXT) && intent.type.equals("text/plain")
        ) {
            val inputData = intent.getStringExtra(Intent.EXTRA_PROCESS_TEXT)
            inputData?.let { viewModel.setZapValue(it) }
            viewModel.filterText()
            val isReadOnly = intent.getBooleanExtra(
                Intent.EXTRA_PROCESS_TEXT_READONLY, false
            )
            if (!isReadOnly){
                intent.putExtra(Intent.EXTRA_PROCESS_TEXT, viewModel.formatText)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
        /*MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder().setTestDeviceIds(listOf("ABCDEF012345")).build()
        )

        // Initialize the Google Mobile Ads SDK.
        MobileAds.initialize(this)*/
        setContent {
            OnlyDigitsTheme(dynamicColor = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OnlyDigitsApp(viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OnlyDigitsTheme {
        Greeting("Android")
    }
}