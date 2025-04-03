package com.rperez.lazycat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rperez.lazycat.data.Results
import com.rperez.lazycat.service.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NekoViewModel : ViewModel() {

    private var _results = MutableStateFlow<List<Results>>(emptyList())
    val results: StateFlow<List<Results>> = _results.asStateFlow()

    private var calling = false

    fun refreshUrlList() {
        viewModelScope.launch {
            try {
                if (!calling) {
                    calling = true
                    _results.value = RetrofitInstance.api.getRandomImages().results
                }
            } catch (_: Exception) {
            } finally {
                calling = false
            }
        }
    }
}
