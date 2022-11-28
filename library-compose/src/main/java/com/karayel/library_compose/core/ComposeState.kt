package com.karayel.library_compose.core

data class ComposeState<T : UiState>(
    val uiState: T,
    val progress: Boolean = false,
    val errorMessage: String? = null
)

/**
 * Marks a class/object as a UiState
 */
interface UiState

/**
 * Marks a class/object as an Event
 */
interface Event {

    /**
     * Can be used for a [StatefulViewModel] with no events.
     */
    companion object NoEvent : Event
}
