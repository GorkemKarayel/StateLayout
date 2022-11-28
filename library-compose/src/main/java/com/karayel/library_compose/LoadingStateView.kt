package com.karayel.library_compose

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingStateView(
    modifier: Modifier = Modifier,
    text: String = ""
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (text.isNotEmpty()) {
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(bottom = 16.dp),
                text = text
            )
        }
        CircularProgressIndicator(
            modifier = Modifier.wrapContentSize(),
        )
    }
}
