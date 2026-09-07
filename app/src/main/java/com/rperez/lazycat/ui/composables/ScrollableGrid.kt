package com.rperez.lazycat.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rperez.lazycat.R
import com.rperez.lazycat.viewmodel.NekoUiState

@Composable
fun ScrollingGrid(
    uiState: NekoUiState,
    onRetry: () -> Unit,
) {
    when {
        uiState.results.isNotEmpty() -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    uiState.results,
                    key = { result -> result.url }
                ) { result ->
                    IndividualImage(result.url)
                }
            }
        }
        uiState.hasError -> {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = stringResource(R.string.load_error),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                )
                Button(
                    onClick = onRetry,
                    enabled = !uiState.isLoading,
                ) {
                    Text(stringResource(R.string.retry))
                }
            }
        }
        else -> {
            CircularProgressIndicator()
        }
    }
}
