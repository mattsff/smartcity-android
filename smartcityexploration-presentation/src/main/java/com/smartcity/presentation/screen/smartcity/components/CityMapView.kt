package com.smartcity.presentation.screen.smartcity.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.smartcity.domain.model.City

@Composable
fun CityMapView(
    cities: List<City>,
    selectedCity: City? = null,
    modifier: Modifier = Modifier,
) {
    val defaultPosition = selectedCity?.let {
        LatLng(it.lat, it.lon)
    } ?: if (cities.isNotEmpty()) {
        LatLng(cities.first().lat, cities.first().lon)
    } else {
        LatLng(0.0, 0.0)
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultPosition,
            if (selectedCity != null || cities.size == 1) 10f else 2f)
    }

    LaunchedEffect(selectedCity) {
        selectedCity?.let {
            val newPosition = LatLng(it.lat, it.lon)
            cameraPositionState.position = CameraPosition.fromLatLngZoom(newPosition, 10f)
        }
    }

    LaunchedEffect(cities) {
        if (cities.isNotEmpty() && selectedCity == null) {
            val zoom = if (cities.size == 1) 10f else 2f
            val position = LatLng(cities.first().lat, cities.first().lon)
            cameraPositionState.position = CameraPosition.fromLatLngZoom(position, zoom)
        }
    }

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        cities.forEach { city ->
            com.google.maps.android.compose.Marker(
                state = MarkerState(position = LatLng(city.lat, city.lon)),
                title = city.name
            )
        }
    }
}

