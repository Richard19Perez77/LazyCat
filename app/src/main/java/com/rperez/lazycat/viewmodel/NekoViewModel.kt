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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NekoUiState(
    val results: List<Results> = emptyList(),
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
)

class NekoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NekoUiState())
    val uiState: StateFlow<NekoUiState> = _uiState.asStateFlow()

    private var calling = false

    fun refreshUrlList(context: Context) {
        viewModelScope.launch {
            if (calling) return@launch
            calling = true
            _uiState.update { it.copy(isLoading = true, hasError = false) }
            try {
                val results = RetrofitInstance.api.getRandomImages().results
                _uiState.value = NekoUiState(
                    results = results,
                    isLoading = false,
                    hasError = false,
                )
                val appContext = context.applicationContext
                results.forEach { SaveImage.saveImageToGallery(appContext, it.url) }
            } catch (_: Exception) {
                _uiState.update { it.copy(isLoading = false, hasError = true) }
            } finally {
                calling = false
            }
        }
    }
}
