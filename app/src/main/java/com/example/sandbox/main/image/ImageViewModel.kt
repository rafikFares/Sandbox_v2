package com.example.sandbox.main.image

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.sandbox.BuildConfig
import com.example.sandbox.core.pagging.ImageItemPagingSource
import com.example.sandbox.core.pagging.DefaultPagingSource
import com.example.sandbox.core.repository.data.ImageItem
import com.example.sandbox.core.repository.local.LocalRepository
import com.example.sandbox.main.platform.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel(binds = [ImageViewModel::class])
class ImageViewModel(private val localRepository: LocalRepository) : BaseViewModel() {

    override val uiState: StateFlow<BaseUiState?>
        get() = TODO("Not yet implemented")

    private val _imageItemsPagingSource = MutableLiveData<ImageItemPagingSource>()

    private val pagingSourceFactory = {
        val imageItemPagingSource = ImageItemPagingSource(localRepository)
        _imageItemsPagingSource.value = imageItemPagingSource
        imageItemPagingSource
    }

    val imagePager: Flow<PagingData<ImageItem>> = Pager(
        config = PagingConfig(pageSize = DefaultPagingSource.ITEMS_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = pagingSourceFactory
    ).flow.cachedIn(viewModelScope)

    override fun onCreate(owner: LifecycleOwner) {
        viewModelScope.launch(Dispatchers.Default) {
            localRepository.observeDataState().collect {
                _imageItemsPagingSource.value?.invalidate()
            }
        }
    }

    override fun log(message: String, exception: Exception?) {
        if (BuildConfig.DEBUG) {
            Log.d("ImageViewModel", message, exception)
        }
    }
}
