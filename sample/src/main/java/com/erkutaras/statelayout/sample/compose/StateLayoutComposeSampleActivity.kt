package com.erkutaras.statelayout.sample.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.karayel.library_compose.*

class StateLayoutComposeSampleActivity : ComponentActivity() {

    private val viewModel: StateLayoutComposeSampleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                SampleContent(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun SampleContent(
    modifier: Modifier = Modifier,
    viewModel: StateLayoutComposeSampleViewModel
) {

    val layoutState = rememberContentState(State.Loading(message = "Loading.."))
    val movieListFlow by viewModel.movieListFlow.collectAsState()

    LaunchedEffect(key1 = movieListFlow) {
        if (movieListFlow.isNotEmpty()) {
            layoutState.content = State.Content
        }
    }

    StateComposeLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        layoutState = layoutState,
        contentLayout = {
            LazyColumn(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                state = rememberLazyListState(),
            ) {
                itemsIndexed(items = movieListFlow, key = { _, item -> item.date }) { _, movie ->
                    MovieCard(movie = movie) {
                        layoutState.content = State.LoadingWithContent(type = LoadingType.Progressive)
                    }
                }
            }
        }
    )
}

@Composable
fun MovieCard(movie: Movie, onClicked: () -> Unit) = with(movie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(124.dp)
            .padding(horizontal = 12.dp)
            .clickable { onClicked() },
        shape = RoundedCornerShape(16),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 80.dp, height = 80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = name,
                    modifier = Modifier.padding(top = 2.dp),
                    color = Color.DarkGray
                )
                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    text = date,
                    color = Color.DarkGray
                )
            }
        }
    }
}
