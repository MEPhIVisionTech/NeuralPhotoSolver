package ru.tanexc.photosolver.domain.settings

import ru.tanexc.photosolver.domain.repository.SettingsRepository
import javax.inject.Inject

class UpdateSettingsPortUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(value: String) = repository.updatePort(value)
}