package com.rperez.lazycat.ui.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
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
import com.rperez.lazycat.R

@Composable
fun IndividualImage(url: String) {

    var isLoading by remember { mutableStateOf(true) }


    Box(
        modifier = Modifier
            .padding(4.dp)
            .border(2.dp, Color(0xFFE12885)),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = url,
            contentDescription = "anime image",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f),
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