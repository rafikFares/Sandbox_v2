package com.example.sandbox.main.album

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.sandbox.BuildConfig
import com.example.sandbox.core.data.AlbumItem
import com.example.sandbox.core.data.ImageItem
import com.example.sandbox.core.repository.local.LocalRepository
import com.example.sandbox.main.platform.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel(binds = [AlbumViewModel::class])
class AlbumViewModel(private val localRepository: LocalRepository) : BaseViewModel() {

    sealed interface UiState : BaseUiState {
        object Init : UiState
        data class AlbumClick(val items: List<ImageItem>) : UiState
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Init)
    override val uiState: StateFlow<UiState> = _uiState

    val albumPager: Flow<PagingData<AlbumItem>> = localRepository
        .getPagingAlbumItemFlow()
        .cachedIn(viewModelScope)

    override fun log(message: String, exception: Exception?) {
        if (BuildConfig.DEBUG) {
            Log.d("AlbumViewModel", message, exception)
        }
    }

    fun onAlbumClick(albumId: Int) {
        viewModelScope.launch {
            localRepository.retrieveItemsOfAlbum(albumId).fold(
                ::handleFailure,
                ::handleAlbumItems
            )
        }
    }

    private fun handleAlbumItems(items: List<ImageItem>) {
        _uiState.value = UiState.AlbumClick(items)
    }
}
