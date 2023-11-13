package ru.tanexc.notegraph.domain.use_cases.settings

import ru.tanexc.photosolver.domain.repository.SettingsRepository
import javax.inject.Inject

class UpdateSettingsBordersEnabledUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(value: Boolean) = repository.updateBordersEnabled(value)
}