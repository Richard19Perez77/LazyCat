package com.rperez.lazycat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.rperez.lazycat.ui.theme.LazyCatTheme
import com.rperez.lazycat.viewmodel.NekoViewModel
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LazyCatTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ScrollingGrid(Modifier.padding(innerPadding))
//                    FullScreenImage(
//                        modifier = Modifier.padding(innerPadding),
//                    )
                }
            }
        }
    }
}

@Composable
fun FullScreenImage(modifier: Modifier = Modifier) {
    val vm: NekoViewModel = NekoViewModel()
    val url = remember { mutableStateOf<String>("") }
    val painter = rememberAsyncImagePainter(model = url.value)

    LaunchedEffect(Unit) {
        while (true) {
            url.value = vm.refreshImage()
            delay(5000L)
        }
    }

    var x = Image(
        painter = painter,
        contentDescription = "Random Neko Image",
        contentScale = ContentScale.Crop,
        modifier = modifier.fillMaxSize()
    )

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (painter.state) {
            AsyncImagePainter.State.Empty -> CircularProgressIndicator()
            is AsyncImagePainter.State.Error -> CircularProgressIndicator()
            is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
            is AsyncImagePainter.State.Success -> x
        }
    }
}

@Composable
fun ScrollingGrid(modifier: Modifier) {
    val itemsList = (0..15).toList()

    val itemModifier = Modifier
        .border(1.dp, Color.Blue)
        .width(80.dp)
        .wrapContentSize()

    LazyHorizontalGrid(
        rows = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(itemsList) {
            Text("Item is $it", modifier = itemModifier)
        }
        item {
            Text("Single item", modifier = itemModifier)
        }
    }
}