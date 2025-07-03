package com.smartcity.presentation.screen.smartcity.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.smartcity.presentation.screen.smartcity.SmartCityViewModel

@Composable
fun SmartCityMapContent(
    state: SmartCityViewModel.UiState,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
    onErrorShown: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        CityMapView(
            cities = state.mapCities,
            selectedCity = state.selectedCity
        )

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (state.isSyncing) {
            SyncingIndicator()
        }

        if (state.showEmptyState) {
            EmptyStateView(onRetry = onRetry)
        }

        state.error?.let { error ->
            LaunchedEffect(error) {
                onErrorShown()
            }
        }
    }
}
