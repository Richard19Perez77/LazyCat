package com.rperez.lazycat.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rperez.lazycat.data.Results
import com.rperez.lazycat.service.RetrofitInstance
import com.rperez.lazycat.utils.SaveImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NekoViewModel : ViewModel() {

    private var _results = MutableStateFlow<List<Results>>(emptyList())
    val results: StateFlow<List<Results>> = _results.asStateFlow()

    private var calling = false

    fun refreshUrlList(context: Context) {
        viewModelScope.launch {
            try {
                if (!calling) {
                    calling = true
                    _results.value = RetrofitInstance.api.getRandomImages().results
                    if (_results.value.isNotEmpty()) {
                        _results.value.forEach {
                            SaveImage.saveImageToGallery(context, it.url)
                        }
                    }
                }
            } catch (_: Exception) {
            } finally {
                calling = false
            }
        }
    }
}
