package com.karayel.library_compose.core

import androidx.lifecycle.viewModelScope
import com.karayel.library_compose.core.StatefulViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun StatefulViewModel<*, *>.launch(
    notifyProgress: Boolean = true,
    notifyError: Boolean = true,
    onError: (exception: Exception) -> Unit = {},
    onCompleted: () -> Unit = {},
    block: suspend CoroutineScope.() -> Unit
) = viewModelScope.launch {
    if (notifyProgress) {
        setProgress(showProgress = true)
    }

    try {
        block()
    } catch (exception: Exception) {
        onError(exception)
        if (notifyError) {
            setError(exception = exception)
        } else {
            // Send Analytics
        }
    } finally {
        onCompleted()
        if (notifyProgress) {
            setProgress(showProgress = false)
        }
    }
}