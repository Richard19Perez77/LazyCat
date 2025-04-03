package com.rperez.lazycat.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.rperez.lazycat.data.Results

@Composable
fun ScrollingGrid(results: State<List<Results>>) {

    if (results.value.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier.fillMaxSize()
        ) {
            items(results.value) { result ->
                result.local_uri?.let {
                    IndividualImage(it)
                } ?: run {

                    IndividualImage(result.url)
                }
            }
        }
    } else {
        CircularProgressIndicator()
    }
}