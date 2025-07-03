package com.smartcity.presentation.screen.smartcity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartcity.domain.model.City
import com.smartcity.domain.util.Result
import com.smartcity.domain.util.AppException
import com.smartcity.domain.usecase.SearchCitiesUseCase
import com.smartcity.domain.usecase.LoadCitiesUseCase
import com.smartcity.presentation.utils.dispatcher.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class SmartCityViewModel @Inject constructor(
    private val searchCitiesUseCase: SearchCitiesUseCase,
    private val loadCitiesUseCase: LoadCitiesUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    data class UiState(
        val isLoading: Boolean = false,
        val query: String = "",
        val results: List<City> = emptyList(),
        val error: AppException? = null,
        val isSyncing: Boolean = false,
        val showEmptyState: Boolean = false
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val queryFlow = MutableStateFlow("")

    init {
        observeQuery()
        syncCities()
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

    private fun syncCities() {
        viewModelScope.launch(dispatcherProvider.io()) {
            _uiState.update { it.copy(isSyncing = true, showEmptyState = false) }
            when (val result = loadCitiesUseCase()) {
                is Result.Success -> {
                    _uiState.update { it.copy(isSyncing = false, showEmptyState = false) }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isSyncing = false,
                            error = result.exception,
                            showEmptyState = true
                        )
                    }
                }
            }
        }
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

    fun retrySync() {
        syncCities()
    }
}
