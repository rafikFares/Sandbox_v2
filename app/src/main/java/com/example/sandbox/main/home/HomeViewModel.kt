package com.example.sandbox.main.home

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.sandbox.BuildConfig
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.data.ImageItem
import com.example.sandbox.core.repository.local.LocalRepository
import com.example.sandbox.core.repository.local.pagging.ItemPagingSource
import com.example.sandbox.core.repository.local.pagging.ItemPagingSource.Companion.ITEMS_PER_PAGE
import com.example.sandbox.core.repository.remote.model.ApiItem
import com.example.sandbox.core.usecase.FetchAndStoreItemsUseCase
import com.example.sandbox.main.platform.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

private const val SEARCH_PARAMS = "technical-test.json"

@KoinViewModel(binds = [HomeViewModel::class])
class HomeViewModel(
    private val fetchAndStoreItemsUseCase: FetchAndStoreItemsUseCase,
    private val localRepository: LocalRepository
) : BaseViewModel() {

    sealed interface UiState : BaseUiState {
        object Init : UiState
        object Complete : UiState
        object Loading : UiState
        data class Error(val exception: SandboxException) : UiState
        object ItemClick : UiState
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Init)
    override val uiState: StateFlow<BaseUiState> = _uiState

    private val _pagingSource = MutableLiveData<ItemPagingSource>()

    private val pagingSourceFactory = {
        val itemPagingSource = ItemPagingSource(localRepository)
        _pagingSource.value = itemPagingSource
        itemPagingSource
    }

    val items: Flow<PagingData<ImageItem>> = Pager(
        config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = pagingSourceFactory
    ).flow.cachedIn(viewModelScope)

    override fun onCreate(owner: LifecycleOwner) {
        println(">>>>>>>>>>> onCreate")

        loadData(SEARCH_PARAMS) // default with "text" as string
    }

    @Suppress("SameParameterValue")
    private fun loadData(params: String) {
        updateUiState(UiState.Loading)
        fetchAndStoreItemsUseCase(params, viewModelScope) {
            it.fold(::handleFailure, ::handleSuccess)
        }
    }

    private fun handleSuccess(success: Boolean) {
        log("handleSuccess count : $success")
        updateUiState(UiState.Complete)
        _pagingSource.value?.invalidate()
    }

    override fun handleFailure(failure: SandboxException) {
        super.handleFailure(failure)
        updateUiState(UiState.Error(failure))
    }

    private fun updateUiState(newState: UiState) {
        viewModelScope.launch {
            _uiState.value = newState
        }
    }

    override fun log(message: String, exception: Exception?) {
        if (BuildConfig.DEBUG) {
            Log.d("HomeViewModel", message, exception)
        }
    }
}
