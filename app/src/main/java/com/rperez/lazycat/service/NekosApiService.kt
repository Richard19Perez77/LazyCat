package com.rperez.lazycat.service

import com.rperez.lazycat.data.NekoImageResponse
import retrofit2.http.GET

interface NekosApiService {

    @GET("neko?amount=5")
    suspend fun getRandomImages(): NekoImageResponse
}
