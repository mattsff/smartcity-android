package com.smartcity.presentation.screen.smartcity.components
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smartcity.domain.model.City

@Composable
fun CityResultsBottomSheet(
    results: List<City>,
    onCityClick: (City) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(results) { city ->
            ListItem(
                headlineContent = { Text("${city.name}, ${city.country}") },
                supportingContent = { Text("Lat: ${city.lat}, Lon: ${city.lon}") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCityClick(city) }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
            HorizontalDivider()
        }
    }
}

