package com.smartcity.presentation
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.smartcity.presentation.navigation.AppNavigation
import com.smartcity.ui.theme.SmartCityAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartCityAppTheme {
                AppNavigation()
            }
        }
    }
}