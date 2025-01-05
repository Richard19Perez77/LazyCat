package com.rperez.lazycat.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.rperez.lazycat.viewmodel.NekoViewModel

@Composable
fun CatGrid() {
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