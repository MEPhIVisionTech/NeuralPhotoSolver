package ru.tanexc.photosolver

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.core.graphics.drawable.toIcon
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import ru.tanexc.photosolver.ContextUtils.getFile
import ru.tanexc.photosolver.core.HOST
import ru.tanexc.photosolver.core.PORT
import ru.tanexc.photosolver.core.State
import ru.tanexc.photosolver.core.WAITING_TIME_MLS
import java.io.File
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loader: ImageHelper,
    private val imageApi: ImageApi
) : ViewModel() {

    private val _state: MutableState<State> = mutableStateOf(State.NOTSTARTED)
    val state: State by _state

    private val _imageURL: MutableState<String?> = mutableStateOf(null)
    val imageURL by _imageURL

    private val _error: MutableState<String> = mutableStateOf("")
    val error by _error

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
                    _imageURL.value = "http://${HOST}:${PORT}/${imageId}.jpg"

                    imageApi.getCock().execute()
                    imageApi.sendImage(
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

}