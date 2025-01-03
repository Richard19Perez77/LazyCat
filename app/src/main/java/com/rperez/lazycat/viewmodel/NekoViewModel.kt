package com.rperez.lazycat.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rperez.lazycat.data.Results
import com.rperez.lazycat.service.RetrofitInstance
import kotlinx.coroutines.launch

class NekoViewModel() : ViewModel() {

    var calling = false

    private var _results = mutableStateOf(listOf<Results>())
    val results
        get() = _results

    fun refreshUrlList() {
        viewModelScope.launch {
            try {
                if (!calling) {
                    calling = true
                    _results.value = RetrofitInstance.api.getRandomImages().results
                    Log.d("_response.value", "_response.value")
                }
            } catch (e: Exception) {
                Log.d("Exception", e.message.toString())
            } finally {
                calling = false
            }
        }
    }
}
