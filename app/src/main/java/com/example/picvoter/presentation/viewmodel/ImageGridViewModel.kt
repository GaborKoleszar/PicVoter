package com.example.picvoter.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.picvoter.core.Constants
import com.example.picvoter.core.Resource
import com.example.picvoter.domain.usecase.SearchForImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageGridViewModel @Inject constructor(
    private val searchForImage: SearchForImageUseCase
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _state = mutableStateOf(ImageGridState())
    val state: State<ImageGridState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null

    fun onSearchImage(query: String, numberOfImages: Int) {
        Log.d(Constants.APP_NAME, "Search started...")
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            searchForImage(query, numberOfImages).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            imageSearchResult = result.data,
                            isLoading = false
                        )
                        Log.d(
                            Constants.APP_NAME,
                            "Response arrived... ${result.message ?: "with no messages"}"
                        )
                        if (result.data?.photos.isNullOrEmpty()) {
                            _eventFlow.emit(
                                UIEvent.ShowSnackbar(
                                    result.message ?: "No images found for $_searchQuery"
                                )
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            imageSearchResult = result.data,
                            isLoading = false
                        )
                        _eventFlow.emit(
                            UIEvent.ShowSnackbar(
                                result.message ?: "Unknown error"
                            )
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            imageSearchResult = result.data,
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }

    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
    }
}