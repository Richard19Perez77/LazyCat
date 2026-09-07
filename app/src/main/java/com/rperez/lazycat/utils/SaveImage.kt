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

        private val client = OkHttpClient()

        suspend fun saveImageToGallery(context: Context, url: String) {
            withContext(Dispatchers.IO) {
                try {
                    val request = Request.Builder().url(url).build()
                    client.newCall(request).execute().use { response ->
                        if (!response.isSuccessful) return@withContext
                        val inputStream = response.body?.byteStream() ?: return@withContext
                        val fileName = "lazycat_${url.hashCode()}.jpg"

                        val resolver = context.contentResolver
                        val collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

                        val existing = resolver.query(
                            collection,
                            arrayOf(MediaStore.Images.Media.DISPLAY_NAME),
                            "${MediaStore.Images.Media.DISPLAY_NAME}=?",
                            arrayOf(fileName),
                            null
                        )
                        existing?.use {
                            if (it.moveToFirst()) return@withContext
                        }

                        val values = ContentValues().apply {
                            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/LazyCat")
                            put(MediaStore.Images.Media.IS_PENDING, 1)
                        }

                        val uri = resolver.insert(collection, values) ?: return@withContext
                        try {
                            val output = resolver.openOutputStream(uri)
                            if (output == null) {
                                resolver.delete(uri, null, null)
                                return@withContext
                            }
                            output.use { inputStream.copyTo(it) }

                            values.clear()
                            values.put(MediaStore.Images.Media.IS_PENDING, 0)
                            resolver.update(uri, values, null, null)
                        } catch (_: Exception) {
                            resolver.delete(uri, null, null)
                        }
                    }
                } catch (_: Exception) {
                }
            }
        }
    }
}
