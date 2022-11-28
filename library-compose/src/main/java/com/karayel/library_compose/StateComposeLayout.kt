package com.karayel.library_compose

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import kotlinx.parcelize.Parcelize
import kotlin.properties.Delegates

@Composable
fun StateComposeLayout(
    modifier: Modifier = Modifier,
    layoutState: LayoutState,
    loadingLayout: @Composable (message: String) -> Unit = { LoadingStateView(text = "") },
    contentLayout: @Composable () -> Unit = {},
    infoLayout: @Composable (model: InfoModel) -> Unit = { InfoStateView() },
) {

    Box(
        modifier = modifier
    ) {
        when (layoutState.content) {
            is State.Loading -> loadingLayout((layoutState.content as State.Loading).message.orEmpty())
            is State.Info, is State.Error -> infoLayout((layoutState.content as State.Info).model)
            is State.Content -> contentLayout()
            is State.LoadingWithContent -> {
                LoadingWithContentView(
                    layoutState.content as State.LoadingWithContent,
                    contentLayout
                )
            }
            is State.None -> {}
        }
    }
}

@Composable
fun LoadingWithContentView(
    loadingWithContent: State.LoadingWithContent,
    contentLayout: @Composable () -> Unit
) {
    contentLayout()

    if (loadingWithContent.type == LoadingType.Circle) {
        LoadingStateView()
    } else {
        LoadingHorizontalView()
    }
}

@Composable
fun rememberContentState(
    initialState: State = State.None
): LayoutState =
    rememberSaveable(key = initialState.toString(), saver = LayoutStateImpl.Saver) {
        LayoutStateImpl(initialState)
    }

interface LayoutState {
    var content: State
}

class LayoutStateImpl(
    initialState: State = State.None
) : LayoutState {
    private var _content by mutableStateOf(initialState)

    // Derived State
    private var hasUpdatedValue by Delegates.vetoable(_content) { _, oldValue, newValue ->
        if (oldValue != newValue) {
            _content = newValue
        }
        oldValue != newValue
    }

    override var content: State
        get() = _content
        set(value) {
            hasUpdatedValue = value
        }

    companion object {
        val Saver = listSaver(
            save = { listOf(it.content) },
            restore = {
                LayoutStateImpl(initialState = it[0] as State)
            }
        )
    }
}

@Parcelize
data class InfoModel(
    val title: String? = null,
    val description: String? = null,
    val buttonText: String? = null,
    @DrawableRes val image: Int? = null,
) : Parcelable

@Parcelize
sealed class State : Parcelable {
    data class Loading(val message: String? = null) : State()
    object Content : State()
    data class Info(val model: InfoModel) : State()
    data class LoadingWithContent(val type: LoadingType) : State()
    object Error : State()
    object None : State()
}

enum class LoadingType {
    Circle, Progressive
}
