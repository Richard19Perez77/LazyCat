package com.rperez.lazycat.ui.composables

import android.content.ContentValues
import android.content.Context
import android.provider.MediaStore
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rperez.lazycat.R
import com.rperez.lazycat.ui.composables.SaveImage.Companion.saveImageToGallery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

@Composable
fun IndividualImage(url: String) {

    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }


    LaunchedEffect(url) {
        saveImageToGallery(context, url)
    }

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
                .aspectRatio(1f),
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

class SaveImage {
    companion object {
        suspend fun saveImageToGallery(context: Context, url: String): Boolean {
            return withContext(Dispatchers.IO) {
                try {
                    val client = OkHttpClient()
                    val request = Request.Builder().url(url).build()
                    val response = client.newCall(request).execute()

                    val inputStream = response.body?.byteStream() ?: return@withContext false
                    val fileName = "lazycat_${url.hashCode()}.jpg" // hash-based name to prevent duplicates

                    val resolver = context.contentResolver
                    val collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

                    // Check if the file already exists
                    val existing = resolver.query(
                        collection,
                        arrayOf(MediaStore.Images.Media.DISPLAY_NAME),
                        "${MediaStore.Images.Media.DISPLAY_NAME}=?",
                        arrayOf(fileName),
                        null
                    )
                    existing?.use {
                        if (it.moveToFirst()) return@withContext true // File already exists
                    }

                    val values = ContentValues().apply {
                        put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/LazyCat")
                        put(MediaStore.Images.Media.IS_PENDING, 1)
                    }

                    val uri = resolver.insert(collection, values)
                        ?: return@withContext false

                    resolver.openOutputStream(uri)?.use { output ->
                        inputStream.copyTo(output)
                    }

                    values.clear()
                    values.put(MediaStore.Images.Media.IS_PENDING, 0)
                    resolver.update(uri, values, null, null)

                    true
                } catch (e: Exception) {
                    false
                }
            }
        }
    }
}
