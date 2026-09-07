package com.rperez.lazycat.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rperez.lazycat.R
import com.rperez.lazycat.viewmodel.NekoViewModel

private val FabActive = Color(0xFFE12885)
private val FabDisabled = Color(0xFF137A7F)
private val GrayscaleFilter = ColorFilter.colorMatrix(
    ColorMatrix().apply { setToSaturation(0f) }
)

@Composable
fun CatGrid(nekoViewModel: NekoViewModel = viewModel()) {
    val context = LocalContext.current
    val uiState by nekoViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        nekoViewModel.refreshUrlList(context.applicationContext)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (!uiState.isLoading) {
                        nekoViewModel.refreshUrlList(context.applicationContext)
                    }
                },
                containerColor = if (uiState.isLoading) FabDisabled else FabActive,
            ) {
                Image(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(id = R.drawable.miku2),
                    contentDescription = stringResource(R.string.fab_refresh),
                    colorFilter = if (uiState.isLoading) GrayscaleFilter else null,
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
                uiState = uiState,
                onRetry = { nekoViewModel.refreshUrlList(context.applicationContext) },
            )
        }
    }
}
