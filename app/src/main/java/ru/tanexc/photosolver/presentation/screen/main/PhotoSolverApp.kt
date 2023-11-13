package ru.tanexc.photosolver.presentation.screen.main

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.olshevski.navigation.reimagined.NavBackHandler
import dev.olshevski.navigation.reimagined.NavController
import dev.olshevski.navigation.reimagined.NavHost
import dev.olshevski.navigation.reimagined.navigate
import dev.olshevski.navigation.reimagined.rememberNavController
import kotlinx.coroutines.launch
import ru.tanexc.photosolver.core.Screen
import ru.tanexc.photosolver.core.State
import ru.tanexc.photosolver.presentation.screen.settings.SettingsScreen
import ru.tanexc.photosolver.ui.theme.PhotoSolverTheme
import ru.tanexc.photosolver.util.LocalSettingsProvider
import ru.tanexc.photosolver.util.Picture
import ru.tanexc.photosolver.util.rememberAppBarState
import ru.tanexc.photosolver.util.widgets.app_bars.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoSolverApp(
    viewModel: MainViewModel
) {
    val activity: Activity = LocalContext.current as Activity
    val navController: NavController<Screen> =
        rememberNavController(startDestination = viewModel.currentScreen)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val settings = LocalSettingsProvider.current
    val topAppBarState = rememberAppBarState(borderEnabled = settings.bordersEnabled)
    val colorScheme = settings.getColorScheme()
    val scope = rememberCoroutineScope()
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            it?.let {
                viewModel.sendPhoto(it, activity)
            }
        }
    )

    LaunchedEffect(viewModel.currentScreen) {
        navController.navigate(viewModel.currentScreen)
        drawerState.close()
    }

    topAppBarState.current.updateTopAppBar(
        navigationIcon = {
            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                Icon(Icons.Outlined.Menu, null)
            }
        }
    )

    PhotoSolverTheme {
        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = true,
            drawerContent = {
                ModalDrawerSheet {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(96.dp))
                        NavigationDrawerItem(
                            icon = { Icon(Icons.Outlined.Image, null) },
                            label = { Text("Обработка фотографии") },
                            selected = viewModel.currentScreen is Screen.ImageLoad,
                            onClick = { viewModel.updateCurrentScreen(Screen.ImageLoad) },
                            shape = RoundedCornerShape(0, 50, 50, 0)
                        )
                        NavigationDrawerItem(
                            icon = { Icon(Icons.Outlined.Settings, null) },
                            label = { Text("Настройки") },
                            selected = viewModel.currentScreen is Screen.Settings,
                            onClick = { viewModel.updateCurrentScreen(Screen.Settings) },
                            shape = RoundedCornerShape(0, 50, 50, 0)
                        )
                    }
                }
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        topAppBarState = topAppBarState,
                        outlineColor = colorScheme.outline
                    )
                }
            ) { paddings ->
                NavBackHandler(navController)
                NavHost(navController) { screen ->
                    when (screen) {
                        is Screen.Settings -> {
                            topAppBarState.current.updateTopAppBar(
                                title = { Text("Настройки") }
                            )
                            SettingsScreen(modifier = Modifier.padding(paddings))
                        }

                        else -> {
                            topAppBarState.current.updateTopAppBar(
                                actions = {
                                    IconButton(
                                        enabled = viewModel.state == State.SUCCESS,
                                        onClick = { }
                                    ) {
                                        Icon(Icons.Outlined.FileDownload, null)
                                    }
                                },
                                title = { Text("${settings.host}:${settings.port}") }
                            )
                            Column(
                                Modifier
                                    .fillMaxSize()
                                    .padding(paddings),
                                verticalArrangement = Arrangement.Center
                            ) {

                                when (viewModel.state) {
                                    State.SUCCESS -> {
                                        viewModel.imageURL?.let {
                                            Picture(
                                                modifier = Modifier
                                                    .fillMaxWidth(0.8f)
                                                    .align(Alignment.CenterHorizontally),
                                                imageURL = it,
                                                contentDescription = null
                                            )
                                        }
                                    }

                                    State.LOADING -> {
                                        CircularProgressIndicator(
                                            modifier = Modifier
                                                .size(56.dp)
                                                .align(Alignment.CenterHorizontally)
                                        )
                                    }

                                    State.ERROR -> {
                                        Column(
                                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Outlined.ErrorOutline,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(56.dp)
                                                    .align(Alignment.CenterHorizontally)
                                            )
                                            Spacer(modifier = Modifier.size(16.dp))
                                            Text(
                                                text = viewModel.error,
                                                Modifier
                                                    .align(Alignment.CenterHorizontally)
                                                    .padding(16.dp), textAlign = TextAlign.Center
                                            )
                                            Spacer(modifier = Modifier.size(8.dp))
                                        }

                                    }

                                    State.NOTSTARTED -> {
                                        Box(
                                            modifier = Modifier
                                                .wrapContentSize()
                                                .align(Alignment.CenterHorizontally)
                                                .clip(RoundedCornerShape(16.dp))
                                                .border(1.dp,
                                                    if (settings.bordersEnabled) colorScheme.outline else Color.Transparent, RoundedCornerShape(16.dp))
                                                .background(colorScheme.secondary.copy(0.2f))

                                                .clickable {
                                                    imagePicker.launch(
                                                        PickVisualMediaRequest(
                                                            ActivityResultContracts.PickVisualMedia.ImageOnly
                                                        )
                                                    )
                                                }
                                        ) {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Icon(
                                                    modifier = Modifier
                                                        .size(220.dp)
                                                        .padding(16.dp),
                                                    imageVector = Icons.Outlined.Image,
                                                    contentDescription = null
                                                )
                                                Spacer(modifier = Modifier.size(16.dp))
                                                Text(text = "Выбрать изображение")
                                                Spacer(modifier = Modifier.size(8.dp))
                                            }
                                        }
                                    }
                                }

                            }
                        }

                    }


                }
            }

        }

    }
}