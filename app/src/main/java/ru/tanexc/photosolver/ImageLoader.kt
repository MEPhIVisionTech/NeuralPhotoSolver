package ru.tanexc.photosolver

import android.content.Context
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmapOrNull
import coil.request.ImageRequest

class ImageLoader(private val context: Context): ImageHelper {

    private val loader = coil.ImageLoader(context).newBuilder().build()

    override suspend fun getImageByUri(uri: Uri?): ImageBitmap? {
        val imageRequest = ImageRequest.Builder(context).data(uri).build()
        return loader.execute(imageRequest).drawable?.toBitmapOrNull()?.asImageBitmap()
    }
}