package com.rperez.lazycat.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.rperez.lazycat.R
import com.rperez.lazycat.viewmodel.NekoViewModel

@Composable
fun CatGrid() {
    val vm = NekoViewModel()
    vm.refreshUrlList()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    vm.refreshUrlList()
                },
                containerColor = Color(0xFF137A7F),
            ) {
                Image(
                    modifier = Modifier.size(48.dp).padding(0.dp),
                    painter = painterResource(id = R.drawable.miku2),
                    contentDescription = "fab button icon"
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            ScrollingGrid(
                vm.results.collectAsState()
            )
        }
    }
}