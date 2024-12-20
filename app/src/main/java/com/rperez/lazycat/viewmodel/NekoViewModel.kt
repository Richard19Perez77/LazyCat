package com.rperez.lazycat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rperez.lazycat.data.NekoImageResponse
import com.rperez.lazycat.service.RetrofitInstance
import kotlinx.coroutines.launch

class NekoViewModel() : ViewModel() {

    var calling = false
    var response = NekoImageResponse(emptyList(), 0)

    fun refreshImage(): String {
        if (response.items.isNotEmpty()) {
            var list = response.items.toMutableList()
            var item = list.removeAt(0)
            response.items = list
            return item.image_url
        } else {
            fetchImages()
        }
        return ""
    }

    private fun fetchImages() {
        viewModelScope.launch {
            try {
                if (!calling) {
                    calling = true
                    response = RetrofitInstance.api.getRandomImage()
                }
            } catch (_: Exception) {
            } finally {
                calling = false
            }
        }
    }
}
