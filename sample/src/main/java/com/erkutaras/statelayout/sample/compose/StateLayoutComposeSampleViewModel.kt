package com.erkutaras.statelayout.sample.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StateLayoutComposeSampleViewModel : ViewModel() {

    private val _movieListFlow = MutableStateFlow<List<Movie>>(emptyList())
    val movieListFlow = _movieListFlow.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000L)
            getMovieList()
        }
    }

    private fun getMovieList() {
        val movieList = MovieListUseCase.createMovieList()
        _movieListFlow.tryEmit(movieList)
    }
}
