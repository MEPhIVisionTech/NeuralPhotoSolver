package ru.tanexc.photosolver.domain.settings

import kotlinx.coroutines.flow.Flow
import ru.tanexc.photosolver.domain.repository.SettingsRepository
import javax.inject.Inject

class GetSettingsAsFlowUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Flow<Settings> = repository.getSettingsAsFlow()
}