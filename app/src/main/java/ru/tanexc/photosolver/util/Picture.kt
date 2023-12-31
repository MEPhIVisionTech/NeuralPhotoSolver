package ru.tanexc.photosolver.util

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent

@Composable
fun Picture(
    modifier: Modifier = Modifier,
    imageURL: String,
    imageCache: Bitmap? = null,
    contentDescription: String?
) {
    if (imageCache == null) {
        SubcomposeAsyncImage(
            modifier = modifier,
            model = imageURL,
            contentDescription = contentDescription,
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Loading ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(8.dp)
                        )
                    }

                is AsyncImagePainter.State.Success ->
                    SubcomposeAsyncImageContent(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.FillWidth
                    )

                else ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Icon(
                            modifier = Modifier.align(CenterHorizontally),
                            imageVector = Icons.Default.Error,
                            contentDescription = null,
                        )
                    }
            }
        }
    } else {

        Image(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth(),
            bitmap = imageCache.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

    }
}