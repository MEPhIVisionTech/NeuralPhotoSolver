package ru.tanexc.photosolver.data.repository

import androidx.compose.ui.graphics.Color
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.t8rin.dynamic.theme.ColorTuple
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.tanexc.photosolver.data.keys.Keys.AMOLED_MODE
import ru.tanexc.photosolver.data.keys.Keys.BORDERS_ENABLED
import ru.tanexc.photosolver.data.keys.Keys.COLOR_TUPLE
import ru.tanexc.photosolver.data.keys.Keys.HOST
import ru.tanexc.photosolver.data.keys.Keys.IS_DARK_MODE
import ru.tanexc.photosolver.data.keys.Keys.PORT
import ru.tanexc.photosolver.data.keys.Keys.USE_DYNAMIC_COLORS
import ru.tanexc.photosolver.domain.repository.SettingsRepository
import ru.tanexc.photosolver.domain.settings.Settings
import ru.tanexc.photosolver.ui.theme.defaultDarkColorTuple
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {
    override fun getSettingsAsFlow(): Flow<Settings> = dataStore.data.map { pref ->
        Settings(
            amoledMode = pref[AMOLED_MODE] ?: false,
            isDarkMode = pref[IS_DARK_MODE] ?: true,
            useDynamicColor = pref[USE_DYNAMIC_COLORS] ?: true,
            bordersEnabled = pref[BORDERS_ENABLED] ?: false,
            colorTuple = pref[COLOR_TUPLE]?.let { colorTuple ->
                val colors = colorTuple.split(" ").map { Color(it.toInt()) }
                ColorTuple(
                    colors[0],
                    colors.getOrNull(1),
                    colors.getOrNull(2),
                    colors.getOrNull(3)
                )
            } ?: defaultDarkColorTuple,
            host = pref[HOST]?: "0.0.0.0",
            port = pref[PORT]?: "8080"
        )
    }

    override suspend fun getSettings(): Settings {
        val pref = dataStore.data.first()
        return Settings(
            amoledMode = pref[AMOLED_MODE] ?: false,
            isDarkMode = pref[IS_DARK_MODE] ?: true,
            useDynamicColor = pref[USE_DYNAMIC_COLORS] ?: true,
            bordersEnabled = pref[BORDERS_ENABLED] ?: false,
            colorTuple = pref[COLOR_TUPLE]?.let { colorTuple ->
                val colors = colorTuple.split(" ").map { Color(it.toInt()) }
                ColorTuple(
                    colors[0],
                    colors.getOrNull(1),
                    colors.getOrNull(2),
                    colors.getOrNull(3)
                )
            } ?: defaultDarkColorTuple,
            host = pref[HOST]?: "0.0.0.0",
            port = pref[PORT]?: "8080"
        )
    }

    override suspend fun updateAmoledMode(value: Boolean) {
        dataStore.edit {
            it[AMOLED_MODE] = !(it[AMOLED_MODE]?:false)
        }
    }

    override suspend fun updateIsDarkMode(value: Boolean) {
        dataStore.edit {
            it[IS_DARK_MODE] = !(it[IS_DARK_MODE]?:false)
        }
    }

    override suspend fun updateUseDynamicColors(value: Boolean) {
        dataStore.edit {
            it[USE_DYNAMIC_COLORS] = !(it[USE_DYNAMIC_COLORS]?:true)
        }
    }

    override suspend fun updateBordersEnabled(value: Boolean) {
        dataStore.edit {
            it[BORDERS_ENABLED] = !(it[BORDERS_ENABLED]?:false)
        }
    }

    override suspend fun updateColorTuple(value: ColorTuple) {
        dataStore.edit {
            it[COLOR_TUPLE] = "${value.primary} ${value.secondary} ${value.tertiary} ${value.surface}"
        }
    }

    override suspend fun updateHost(value: String) {
        dataStore.edit {
            it[HOST] = value
        }
    }

    override suspend fun updatePort(value: String) {
        dataStore.edit {
            it[PORT] = value
        }
    }

}