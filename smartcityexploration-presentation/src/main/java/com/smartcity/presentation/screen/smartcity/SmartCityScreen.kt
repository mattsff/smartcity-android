package com.smartcity.presentation.screen.smartcity

import androidx.compose.foundation.layout.*
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smartcity.presentation.screen.smartcity.components.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmartCityScreen(
    viewModel: SmartCityViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scaffoldState.bottomSheetState.expand()
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 200.dp,
        sheetContent = {
            CityResultsBottomSheet(
                results = state.results,
                onCityClick = { city ->
                    viewModel.onCitySelected(city)
                    scope.launch { scaffoldState.bottomSheetState.partialExpand() }
                }
            )
        },
        topBar = {
            SearchBar(
                query = state.query,
                onQueryChanged = viewModel::onQueryChanged,
                isLoading = state.isLoading,
                onSearchConfirmed = {
                    viewModel.onSearchConfirmed()
                    scope.launch { scaffoldState.bottomSheetState.partialExpand() }
                }
            )
        }
    ) { innerPadding ->
        SmartCityMapContent(
            state = state,
            modifier = Modifier.padding(innerPadding),
            onRetry = viewModel::retrySync,
            onErrorShown = viewModel::errorShown
        )
    }
}

