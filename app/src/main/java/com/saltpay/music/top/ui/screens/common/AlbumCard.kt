package com.saltpay.music.top.ui.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.saltpay.music.top.R
import com.saltpay.music.top.domian.model.Album
import com.saltpay.music.top.domian.model.Artist
import com.saltpay.music.top.domian.model.Category
import com.saltpay.music.top.domian.model.Price
import com.saltpay.music.top.ui.theme.MainApplicationTheme

@Composable
fun AlbumCard(
    album: Album,
    handleFavorite: (Album) -> Unit,
    navigateToDetail: ((String) -> Unit)?,
) {

    val drawableRes = if (album.isFavorite) {
        painterResource(R.drawable.ic_favorite_red)
    } else {
        painterResource(R.drawable.ic_favorite)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(navigateToDetail != null) {
                navigateToDetail?.let { it(album.id) }
            }
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = album.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(20))
            )
            Image(
                painter = drawableRes,
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Red),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        handleFavorite(album)
                    }
            )
            Text(text = album.name, Modifier.padding(start = 8.dp))
        }
    }
}

@Preview
@Composable
private fun AlbumCardPreview() {
    val album = Album(
        "1", Artist("album name", "href"), "name", "imageUrl", 10,
        Price(150f, "usd"), Category(1, "term", "label", "schemeUrl"), null
    )

    MainApplicationTheme {
        AlbumCard(album = album,
            handleFavorite = {},
            navigateToDetail = {})
    }

}