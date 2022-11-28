package com.karayel.library_compose

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun InfoStateView(
    modifier: Modifier = Modifier,
    model: InfoModel = InfoModel(),
    retryButtonClicked: (() -> Unit)? = null,
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(64.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (model.image != null) {
            Image(painter = painterResource(id = model.image), contentDescription = null)
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = model.title.orEmpty())
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = model.description.orEmpty())
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            modifier = Modifier.clickable { retryButtonClicked?.invoke() },
            text = model.buttonText.orEmpty()
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun InfoStateViewPreview() {
    val infoModel = InfoModel(
        "Oops...",
        "Sorry!... Something goes wrong :(",
        "Try Again"
    )
    MaterialTheme {
        InfoStateView(model = infoModel) {
            Log.d("Snap", "Retry Button Clicked")
        }
    }
}
