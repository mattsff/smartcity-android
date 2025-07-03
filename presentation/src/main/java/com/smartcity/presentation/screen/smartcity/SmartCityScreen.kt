package com.smartcity.presentation.screen.smartcity

import androidx.compose.foundation.layout.*
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smartcity.presentation.screen.smartcity.components.SearchBar
import com.smartcity.presentation.screen.smartcity.components.CityMapView
import com.smartcity.presentation.screen.smartcity.components.CityResultsBottomSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmartCityScreen(
    viewModel: SmartCityViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 200.dp,
        sheetContent = {
            CityResultsBottomSheet(
                results = state.results,
                onCityClick = { city ->
                    // move camera to city (callback)
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
                    // Collapse list, trigger map update
                    scope.launch { scaffoldState.bottomSheetState.partialExpand() }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            CityMapView(cities = state.results)

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

