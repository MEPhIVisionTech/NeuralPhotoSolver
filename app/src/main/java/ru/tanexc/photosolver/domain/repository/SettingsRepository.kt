package ru.tanexc.photosolver.domain.repository


import com.t8rin.dynamic.theme.ColorTuple
import kotlinx.coroutines.flow.Flow
import ru.tanexc.photosolver.domain.settings.Settings

interface SettingsRepository {

    fun getSettingsAsFlow(): Flow<Settings>

    suspend fun getSettings(): Settings

    suspend fun updateAmoledMode(value: Boolean)

    suspend fun updateIsDarkMode(value: Boolean)

    suspend fun updateUseDynamicColors(value: Boolean)

    suspend fun updateBordersEnabled(value: Boolean)

    suspend fun updateColorTuple(value: ColorTuple)

    suspend fun updateHost(value: String)

    suspend fun updatePort(value: String)
}