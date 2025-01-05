package com.rperez.lazycat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rperez.lazycat.data.Results
import com.rperez.lazycat.ui.theme.LazyCatTheme
import com.rperez.lazycat.viewmodel.NekoViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyCatTheme {
                val vm: NekoViewModel = NekoViewModel()
                Scaffold(
                    floatingActionButton = {
                        ExtendedFloatingActionButton(
                            onClick = {
                                vm.refreshUrlList()
                            }
                        ) {}
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        ScrollingGrid(vm.results, vm::refreshUrlList)
                    }
                }
            }
        }
    }
}

@Composable
fun IndividualImage(url: String) {

    var isLoading by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .size(600.dp)
            .border(1.dp, Color.Red),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = url,
            contentDescription = "anime image",
            modifier = Modifier.fillMaxWidth(),
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.error),
            onLoading = {
                isLoading = true
            },
            onSuccess = {
                isLoading = false
            }
        )

        if (isLoading) {
            CircularProgressIndicator()
        }
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