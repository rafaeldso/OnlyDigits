package br.com.rafael.onlydigts.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

private const val SEPARATOR =  " "
class ClipboardViewModel : ViewModel() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    var clipboardText by mutableStateOf("")

    var isShowLoading by mutableStateOf(false)

    var formatText by mutableStateOf("")

    var isError by mutableStateOf(false)

    fun setZapValue(text: String) {
        if (text.lowercase() != "null")
            clipboardText = text
    }

    fun isShowLoading(isShow: Boolean) {
        isShowLoading = isShow
    }

    fun isError(isErrorValue: Boolean) {
        isError = isErrorValue
    }

    fun filterText() {
        //scope.launch {
            val text = clipboardText
            try {
                if (text.isNotBlank()) {
                    formatText = text.split(SEPARATOR).map { it.filter { it2-> it2.isDigit() } }.joinToString(SEPARATOR)
                }
            } catch (exception : Exception){
                isError = true
                formatText = clipboardText
            }
        //}
    }
}

