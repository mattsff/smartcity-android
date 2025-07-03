package com.smartcity.presentation.screen.smartcity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartcity.domain.model.City
import com.smartcity.domain.util.Result
import com.smartcity.domain.util.AppException
import com.smartcity.domain.usecase.SearchCitiesUseCase
import com.smartcity.presentation.utils.dispatcher.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject
import kotlinx.coroutines.flow.*

@HiltViewModel
class SmartCityViewModel @Inject constructor(
    private val searchCitiesUseCase: SearchCitiesUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    data class UiState(
        val isLoading: Boolean = false,
        val query: String = "",
        val results: List<City> = emptyList(),
        val error: AppException? = null,
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val queryFlow = MutableStateFlow("")

    init {
        observeQuery()
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun observeQuery() {
        queryFlow
            .debounce(400)
            .distinctUntilChanged()
            .filter { it.length >= 2 }
            .flatMapLatest { query ->
                searchCitiesUseCase(query)
                    .onStart {
                        _uiState.update { it.copy(isLoading = true, error = null) }
                    }
                    .catch { e ->
                        val exception = if (e is AppException) e else AppException.UnknownError
                        emit(Result.Error(exception))
                    }
            }
            .flowOn(dispatcherProvider.io())
            .onEach { result ->
                _uiState.update { currentState ->
                    when (result) {
                        is Result.Success -> currentState.copy(
                            isLoading = false,
                            results = result.data,
                            error = null
                        )

                        is Result.Error -> currentState.copy(
                            isLoading = false,
                            results = emptyList(),
                            error = result.exception
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun onQueryChanged(query: String) {
        _uiState.update { it.copy(query = query) }
        queryFlow.value = query
        if (query.length < 2) {
            _uiState.update { it.copy(results = emptyList(), error = null, isLoading = false) }
        }
    }

    fun errorShown() {
        _uiState.update { it.copy(error = null) }
    }
}