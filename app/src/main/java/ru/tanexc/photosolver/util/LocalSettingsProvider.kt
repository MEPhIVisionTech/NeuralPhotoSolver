package ru.tanexc.photosolver.util

import androidx.compose.runtime.compositionLocalOf
import ru.tanexc.photosolver.domain.settings.Settings

val LocalSettingsProvider = compositionLocalOf<Settings> { error("Settings not presented") }