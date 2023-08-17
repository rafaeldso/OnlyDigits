package br.com.rafael.onlydigts.component

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.rafael.onlydigits.R
import br.com.rafael.onlydigits.ui.theme.Purple80
import br.com.rafael.onlydigts.viewModel.ClipboardViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    clipboardViewModel: ClipboardViewModel,
    onNavigateToResult: () -> Unit = {}
) {
    ZapContent(
        clipboardViewModel,
        modifier = modifier.fillMaxSize(),
        onNavigateToResult
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZapContent(
    clipboardViewModel: ClipboardViewModel,
    modifier: Modifier = Modifier,
    onNavigateToResult: () -> Unit = {}
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Top
    )
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxSize()
                .padding(all = 20.dp)
        ) {
            TextField(
                value = clipboardViewModel.clipboardText,
                onValueChange = { clipboardViewModel.setZapValue(it) },
                label = { Text(text = stringResource(R.string.od_paste_here)) },
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .fillMaxWidth()
                    .weight(1f)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.End)
            ) {
                TextButton(
                    onClick = { clipboardViewModel.setZapValue("") },
                    Modifier
                        .height(IntrinsicSize.Min)
                        .padding(start = 4.dp),
                    //colors = ButtonDefaults.textButtonColors(contentColor = Color.Black)
                ) {
                    Text(stringResource(R.string.od_clear))
                }
                if (clipboardViewModel.clipboardText.isNotEmpty()) {
                    Button(
                        onClick = {
                                if (clipboardViewModel.clipboardText.isNotBlank()) {
                                    clipboardViewModel.formatText = ""
                                    clipboardViewModel.isError(false)
                                    onNavigateToResult()
                                } else {
                                    Toast.makeText(
                                        context,
                                        context.getString(R.string.od_error_empty),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        },
                        //colors = ButtonDefaults.buttonColors(Color.Black),
                        modifier =
                        Modifier
                            .height(IntrinsicSize.Min)
                            .padding(start = 4.dp)
                    ) {
                        Icon(
                            Icons.Filled.Done,
                            contentDescription = stringResource(R.string.od_digits),
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(stringResource(R.string.od_digits))
                    }
                } else {
                    Button(
                        onClick = {
                            clipboardViewModel.setZapValue(clipboardManager.getText()?.text.orEmpty())
                        },
//                        colors = ButtonDefaults.buttonColors(Color.Black),
                        modifier =
                        Modifier
                            .height(IntrinsicSize.Min)
                            .padding(start = 4.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.od_ic_content_paste),
                            contentDescription = stringResource(R.string.od_paste),
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(stringResource(R.string.od_paste))
                    }
                }

            }


        }
    }


}

/*
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(clipboardViewModel = ClipboardViewModel(), onNavigateToResult = {})
}*/
