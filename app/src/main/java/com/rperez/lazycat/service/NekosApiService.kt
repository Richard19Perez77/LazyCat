package com.rperez.lazycat.service

import com.rperez.lazycat.data.NekoImageResponse
import retrofit2.http.GET

interface NekosApiService {
    @GET("images/random")
    suspend fun getRandomImage(): NekoImageResponse
}
