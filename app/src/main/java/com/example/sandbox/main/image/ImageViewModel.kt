package com.example.sandbox.main.image

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.sandbox.BuildConfig
import com.example.sandbox.core.data.ImageItem
import com.example.sandbox.core.repository.local.LocalRepository
import com.example.sandbox.main.platform.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel(binds = [ImageViewModel::class])
class ImageViewModel(private val localRepository: LocalRepository) : BaseViewModel() {

    sealed interface UiState : BaseUiState {
        object ShowImage : UiState
    }

    private val _uiState = MutableSharedFlow<UiState>()
    override val uiState: SharedFlow<UiState> = _uiState

    private val _clickedImageUrl: MutableLiveData<String> = MutableLiveData<String>()
    val clickedImageUrl: LiveData<String> = _clickedImageUrl

    val imagePager: Flow<PagingData<ImageItem>> = localRepository
        .getPagingImageItemFlow()
        .cachedIn(viewModelScope)

    fun onImageItemClick(imageUrl: String) {
        _clickedImageUrl.value = imageUrl
        updateUiState(UiState.ShowImage)
    }

    private fun updateUiState(newState: UiState) {
        viewModelScope.launch {
            _uiState.emit(newState)
        }
    }

    override fun log(message: String, exception: Exception?) {
        if (BuildConfig.DEBUG) {
            Log.d("ImageViewModel", message, exception)
        }
    }
}
