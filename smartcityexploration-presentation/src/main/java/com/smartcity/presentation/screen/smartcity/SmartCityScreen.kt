package com.smartcity.presentation.screen.smartcity

import androidx.compose.foundation.layout.*
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
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
    val bottomSheetTopPadding = 150.dp //TODO: Make this dynamic based on screen size

    LaunchedEffect(Unit) {
        scaffoldState.bottomSheetState.expand()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        SmartCityMapContent(
            state = state,
            modifier = Modifier.fillMaxSize(),
            onRetry = viewModel::retrySync,
            onErrorShown = viewModel::errorShown
        )

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = bottomSheetTopPadding)
            ) {
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
                    }
                ) { _ ->

                }
            }
        }

        if (!state.isSyncing) {
            SearchBar(
                query = state.query,
                onQueryChanged = viewModel::onQueryChanged,
                onSearchConfirmed = {
                    viewModel.onSearchConfirmed()
                    scope.launch { scaffoldState.bottomSheetState.partialExpand() }
                },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp)
            )
        }
    }
}
