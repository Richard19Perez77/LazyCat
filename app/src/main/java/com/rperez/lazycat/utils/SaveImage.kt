package com.rperez.lazycat.utils

import android.content.ContentValues
import android.content.Context
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class SaveImage {
    companion object {
        suspend fun saveImageToGallery(context: Context, url: String) {
            return withContext(Dispatchers.IO) {
                try {
                    val client = OkHttpClient()
                    val request = Request.Builder().url(url).build()
                    val response = client.newCall(request).execute()

                    val inputStream = response.body?.byteStream() ?: return@withContext
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
                        if (it.moveToFirst()) return@withContext // File already exists
                    }

                    val values = ContentValues().apply {
                        put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/LazyCat")
                        put(MediaStore.Images.Media.IS_PENDING, 1)
                    }

                    val uri = resolver.insert(collection, values)
                        ?: return@withContext

                    resolver.openOutputStream(uri)?.use { output ->
                        inputStream.copyTo(output)
                    }

                    values.clear()
                    values.put(MediaStore.Images.Media.IS_PENDING, 0)
                    resolver.update(uri, values, null, null)
                } catch (e: Exception) { }
            }
        }
    }
}
