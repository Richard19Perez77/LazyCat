package com.rperez.lazycat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.rperez.lazycat.data.Results
import com.rperez.lazycat.ui.theme.LazyCatTheme
import com.rperez.lazycat.viewmodel.NekoViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyCatTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    val vm: NekoViewModel = NekoViewModel()
                    ScrollingGrid(vm.results, vm::refreshUrlList)
                }
            }
        }
    }
}

@Composable
fun IndividualImage(url: String) {
    val painter = rememberAsyncImagePainter(model = url)
    Box(
        modifier = Modifier
            .size(600.dp)
            .border(1.dp, Color.Red),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = "Random Neko Image",
            contentScale = ContentScale.Fit,
        )
    }
}

@Composable
fun ScrollingGrid(results: MutableState<List<Results>>, getImages: () -> Unit) {

    LaunchedEffect(Unit) {
        getImages()
    }

    if (results.value.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier.fillMaxSize()
        ) {
            items(results.value.count()) { item ->
                IndividualImage(results.value[item].url)
            }
        }
    } else {
        CircularProgressIndicator()
    }
}