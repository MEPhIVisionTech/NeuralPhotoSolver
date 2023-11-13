package ru.tanexc.photosolver.presentation.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.outlined.ImageSearch
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tanexc.notegraph.presentation.ui.shapes.firstPreferenceShape
import ru.tanexc.notegraph.presentation.ui.shapes.lastPreferenceShape
import ru.tanexc.notegraph.presentation.ui.widgets.cards.PreferenceCard
import ru.tanexc.photosolver.ui.theme.Typography
import ru.tanexc.photosolver.util.LocalSettingsProvider

@Composable
fun SettingsScreen(
    modifier: Modifier
) {
    val settings = LocalSettingsProvider.current
    val colorScheme = settings.getColorScheme()
    val viewModel: SettingsViewModel = hiltViewModel()

    var host: String by remember { mutableStateOf(settings.host)}
    var port: String by remember { mutableStateOf(settings.port)}

    LazyColumn(modifier, contentPadding = PaddingValues(22.dp)) {
        item {
            PreferenceCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorScheme.secondaryContainer),
                borderEnabled = settings.bordersEnabled,
                borderColor = colorScheme.outline,
                shape = firstPreferenceShape(),
                header = {
                    Icon(
                        imageVector = Icons.Outlined.ImageSearch,
                        contentDescription = null,
                        modifier = Modifier.align(CenterVertically)

                    )
                    Spacer(modifier = Modifier.size(22.dp))
                    Text(
                        "Обработка фото",
                        Modifier
                            .weight(1f)
                            .align(CenterVertically)
                    )
                }
            ) {
                Spacer(Modifier.size(16.dp))
                Row {
                    OutlinedTextField(
                        modifier = Modifier.weight(0.5f),
                        value = host,
                        onValueChange = {
                            host = it
                            viewModel.updateHost(it) },
                        label = {
                            Text("host")
                        }
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    OutlinedTextField(
                        modifier = Modifier.weight(0.5f),
                        value = port,
                        onValueChange = {
                            port = it
                            viewModel.updatePort(it)
                        },
                        label = {
                            Text("port")
                        }
                    )
                }

            }
        }
        item {
            Spacer(modifier = Modifier.size(4.dp))
            PreferenceCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorScheme.secondaryContainer),
                borderEnabled = settings.bordersEnabled,
                borderColor = colorScheme.outline,
                shape = lastPreferenceShape(),
                header = {
                    Icon(
                        imageVector = Icons.Filled.Style,
                        contentDescription = null,
                        modifier = Modifier.align(CenterVertically)

                    )
                    Spacer(modifier = Modifier.size(22.dp))
                    Text(
                        text = "Стиль",
                        modifier = Modifier
                            .weight(1f)
                            .align(CenterVertically),
                        style = Typography.bodyLarge.copy(fontWeight = FontWeight.ExtraBold)
                    )
                }
            ) {
                Row {
                    Text(
                        "Amoled режим",
                        modifier = Modifier
                            .weight(1f)
                            .align(CenterVertically)
                    )
                    Switch(
                        checked = settings.amoledMode,
                        onCheckedChange = { viewModel.updateAmoledMode(!settings.amoledMode) },
                        modifier = Modifier
                            .align(CenterVertically)
                    )
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(colorScheme.outline.copy(0.2f), CircleShape)
                )

                Row {
                    Text(
                        "Динамические цвета",
                        modifier = Modifier
                            .weight(1f)
                            .align(CenterVertically)
                    )
                    Switch(
                        checked = settings.useDynamicColor,
                        onCheckedChange = { viewModel.updateUseDynamicColors(!settings.useDynamicColor) },
                        modifier = Modifier
                            .align(CenterVertically)
                    )
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(colorScheme.outline.copy(0.2f), CircleShape)
                )

                Row {
                    Text(
                        "Тёмная тема", modifier = Modifier
                            .weight(1f)
                            .align(CenterVertically)
                    )
                    Switch(
                        checked = settings.isDarkMode,
                        onCheckedChange = { viewModel.updateUseDarkMode(!settings.isDarkMode) },
                        modifier = Modifier
                            .align(CenterVertically)
                    )
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(colorScheme.outline.copy(0.2f), CircleShape)
                )

                Row {
                    Text(
                        "Обводка",
                        modifier = Modifier
                            .weight(1f)
                            .align(CenterVertically)
                    )
                    Switch(
                        checked = settings.bordersEnabled,
                        onCheckedChange = { viewModel.updateUseBorders(!settings.bordersEnabled) },
                        modifier = Modifier
                            .align(CenterVertically)
                    )
                }
            }
        }
    }
}