package ru.tanexc.photosolver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material.icons.outlined.Filter
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.imageLoader
import coil.size.Size
import dagger.hilt.android.AndroidEntryPoint
import ru.tanexc.photosolver.core.State
import ru.tanexc.photosolver.ui.theme.PhotoSolverTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val colorScheme = if (isSystemInDarkTheme()) {
                darkColorScheme()
            } else {
                lightColorScheme()
            }
            val imagePicker = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia(),
                onResult = {
                    it?.let {
                        viewModel.sendPhoto(it, this)
                    }
                }
            )
            PhotoSolverTheme {
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            navigationIcon = {
                                IconButton(
                                    enabled = viewModel.state == State.SUCCESS,
                                    onClick = { viewModel.closeImage() }
                                ) {
                                    Icon(Icons.Outlined.Close, null)
                                }
                            },
                            actions = {
                                IconButton(
                                    enabled = viewModel.state == State.SUCCESS,
                                    onClick = {  }
                                ) {
                                    Icon(Icons.Outlined.FileDownload, null)
                                }
                            },
                            title = {}
                        )
                    }
                ) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(it),
                        verticalArrangement = Arrangement.Center
                    ) {

                        when (viewModel.state) {
                            State.SUCCESS -> {
                                viewModel.imageURL?.let {
                                    Picture(
                                        modifier = Modifier
                                            .fillMaxWidth(0.8f)
                                            .align(CenterHorizontally),
                                        imageURL = it,
                                        contentDescription = null
                                    )
                                }
                            }

                            State.LOADING -> {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(56.dp)
                                        .align(CenterHorizontally)
                                )
                            }

                            State.ERROR -> {
                                Column(
                                    modifier = Modifier.align(CenterHorizontally)
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.ErrorOutline,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(56.dp)
                                            .align(CenterHorizontally)
                                    )
                                    Spacer(modifier = Modifier.size(16.dp))
                                    Text(
                                        text = viewModel.error,
                                        Modifier
                                            .align(CenterHorizontally)
                                            .padding(16.dp), textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.size(8.dp))
                                }

                            }

                            State.NOTSTARTED -> {
                                Box(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .align(CenterHorizontally)
                                        .clip(RoundedCornerShape(16.dp))
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
                                        horizontalAlignment = CenterHorizontally
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
