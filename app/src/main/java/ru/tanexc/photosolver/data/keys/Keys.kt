package ru.tanexc.photosolver.data.keys

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Keys {
    val AMOLED_MODE = booleanPreferencesKey("amoled_mode")
    val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
    val BORDERS_ENABLED = booleanPreferencesKey("borders_enabled")
    val USE_DYNAMIC_COLORS = booleanPreferencesKey("use_dynamic_colors")
    val COLOR_TUPLE = stringPreferencesKey("color_tuple")
    val HOST = stringPreferencesKey("host")
    val PORT = stringPreferencesKey("port")

    val LOCAL_USER_ID = stringPreferencesKey("local_user_id")
}