package ru.tanexc.photosolver.domain.settings

import androidx.compose.runtime.Composable
import com.t8rin.dynamic.theme.ColorTuple
import com.t8rin.dynamic.theme.getAppColorTuple
import com.t8rin.dynamic.theme.rememberColorScheme
import ru.tanexc.photosolver.ui.theme.defaultDarkColorTuple

data class Settings(
    val amoledMode: Boolean,
    val isDarkMode: Boolean,
    val useDynamicColor: Boolean,
    val bordersEnabled: Boolean,
    val colorTuple: ColorTuple,
    val port: String,
    val host: String
) {

    companion object {
        fun Default() = Settings(
            amoledMode = false,
            isDarkMode = true,
            bordersEnabled = false,
            useDynamicColor = false,
            colorTuple = defaultDarkColorTuple,
            port = "8080",
            host = "0.0.0.0"
        )
    }

    @Composable
    fun getColorScheme() =
        rememberColorScheme(
            isDarkTheme = isDarkMode,
            amoledMode = amoledMode,
            colorTuple = getAppColorTuple(
                defaultColorTuple = colorTuple,
                dynamicColor = useDynamicColor,
                darkTheme = isDarkMode
            )
        )
}