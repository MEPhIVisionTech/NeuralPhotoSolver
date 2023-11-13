package ru.tanexc.photosolver.presentation.screen.main

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Retrofit
import ru.tanexc.notegraph.domain.use_cases.settings.GetSettingsUseCase
import ru.tanexc.photosolver.core.Screen
import ru.tanexc.photosolver.core.State
import ru.tanexc.photosolver.core.WAITING_TIME_MLS
import ru.tanexc.photosolver.core.isIPValid
import ru.tanexc.photosolver.core.isPortValid
import ru.tanexc.photosolver.domain.ImageApi
import ru.tanexc.photosolver.domain.ImageHelper
import ru.tanexc.photosolver.domain.settings.GetSettingsAsFlowUseCase
import ru.tanexc.photosolver.domain.settings.Settings
import ru.tanexc.photosolver.util.ContextUtils.getFile
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loader: ImageHelper,
    getSettingsAsFlowUseCase: GetSettingsAsFlowUseCase,
    getSettingsUseCase: GetSettingsUseCase,
) : ViewModel() {
    private val _currentScreen: MutableState<Screen> = mutableStateOf(Screen.ImageLoad)
    val currentScreen: Screen by _currentScreen

    private val _settings: MutableState<Settings> = mutableStateOf(Settings.Default())
    val settings: Settings by _settings

    private val _state: MutableState<State> = mutableStateOf(State.NOTSTARTED)
    val state: State by _state

    private val _imageURL: MutableState<String?> = mutableStateOf(null)
    val imageURL by _imageURL

    private val _error: MutableState<String> = mutableStateOf("")
    val error by _error

    private var _imageApi: MutableState<ImageApi?> = mutableStateOf(null)
    private val imageApi by _imageApi

    private var _retrofit: MutableState<Retrofit?> = mutableStateOf(null)
    private val retrofit by _retrofit

    init {
        runBlocking {
            _settings.value = getSettingsUseCase()
            _retrofit.value = Retrofit.Builder()
                .baseUrl("http://${if (isIPValid(settings.host)) settings.host else "0.0.0.0"}:${if (isPortValid(settings.port)) settings.port else "8080"}")
                .build()
            _imageApi.value = retrofit!!.create(ImageApi::class.java)
        }
        getSettingsAsFlowUseCase().onEach {
            _settings.value = it
            _retrofit.value = Retrofit.Builder()
                .baseUrl("http://${if (isIPValid(settings.host)) settings.host else "0.0.0.0"}:${if (isPortValid(settings.port)) settings.port else "8080"}")
                .build()
            _imageApi.value = retrofit!!.create(ImageApi::class.java)
        }.launchIn(viewModelScope)
    }

    fun sendPhoto(uri: Uri, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = State.LOADING
            runCatching {
                val pickedImage: ImageBitmap? = loader.getImageByUri(uri)
                pickedImage?.let {
                    val file = context.getFile(uri.toString())
                    val requestFile = file!!.asRequestBody("image/jpg".toMediaTypeOrNull())
                    val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
                    val imageId = UUID.randomUUID().toString()
                    _imageURL.value = "http://${settings.host}:${settings.port}/${imageId}.jpg"

                    imageApi!!.getCock().execute()
                    imageApi!!.sendImage(
                        imageId,
                        body
                    ).execute()
                }
            }.onFailure {
                _error.value = it.message.toString()
                _state.value = State.ERROR
            }
            delay(WAITING_TIME_MLS)
        }.invokeOnCompletion {
            if (_state.value == State.LOADING) _state.value = State.SUCCESS
        }
    }

    fun closeImage() {
        _state.value = State.LOADING
        _imageURL.value = ""
    }

    fun updateCurrentScreen(screen: Screen) {
        _currentScreen.value = screen
    }

}