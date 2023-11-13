package ru.tanexc.photosolver.presentation.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.tanexc.notegraph.domain.use_cases.settings.UpdateSettingsAmoledModeUseCase
import ru.tanexc.notegraph.domain.use_cases.settings.UpdateSettingsBordersEnabledUseCase
import ru.tanexc.notegraph.domain.use_cases.settings.UpdateSettingsIsDarkModeUseCase
import ru.tanexc.notegraph.domain.use_cases.settings.UpdateUseDynamicColorsUseCase
import ru.tanexc.photosolver.domain.settings.UpdateSettingsHostUseCase
import ru.tanexc.photosolver.domain.settings.UpdateSettingsPortUseCase
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val updateSettingsAmoledModeUseCase: UpdateSettingsAmoledModeUseCase,
    private val updateUseDynamicColorsUseCase: UpdateUseDynamicColorsUseCase,
    private val updateSettingsIsDarkModeUseCase: UpdateSettingsIsDarkModeUseCase,
    private val updateSettingsBordersEnabledUseCase: UpdateSettingsBordersEnabledUseCase,
    private val updateSettingsHostUseCase: UpdateSettingsHostUseCase,
    private val updateSettingsPortUseCase: UpdateSettingsPortUseCase
): ViewModel() {

    fun updateAmoledMode(enabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            updateSettingsAmoledModeUseCase(enabled)
        }
    }

    fun updateUseDarkMode(enabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            updateSettingsIsDarkModeUseCase(enabled)
        }
    }
    fun updateUseBorders(enabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            updateSettingsBordersEnabledUseCase(enabled)
        }
    }

    fun updateUseDynamicColors(enabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            updateUseDynamicColorsUseCase(enabled)
        }
    }

    fun updateHost(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            updateSettingsHostUseCase(value)
        }
    }

    fun updatePort(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            updateSettingsPortUseCase(value)
        }
    }

}