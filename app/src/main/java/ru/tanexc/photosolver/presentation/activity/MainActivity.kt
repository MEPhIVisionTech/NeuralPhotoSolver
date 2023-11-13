package ru.tanexc.photosolver.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import ru.tanexc.photosolver.presentation.screen.main.MainViewModel
import ru.tanexc.photosolver.presentation.screen.main.PhotoSolverApp
import ru.tanexc.photosolver.util.LocalSettingsProvider

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            CompositionLocalProvider(
                LocalSettingsProvider provides viewModel.settings
            ) {
                rememberSystemUiController().setSystemBarsColor(
                    Color.Transparent,
                    isNavigationBarContrastEnforced = false
                )
                PhotoSolverApp(viewModel)
            }
        }
    }
}
