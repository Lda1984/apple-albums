package com.saltpay.music.top.ui.screens.detail.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saltpay.music.top.R
import com.saltpay.music.top.domian.model.Album
import com.saltpay.music.top.domian.model.Artist
import com.saltpay.music.top.domian.model.Category
import com.saltpay.music.top.domian.model.Price
import com.saltpay.music.top.ui.screens.common.AlbumCard
import com.saltpay.music.top.ui.screens.common.UpButtonWithText

@Composable
fun DetailAlbumContent(
    album: Album,
    onHandleFavorite: (Album) -> Unit,
    onBack: () -> Unit
) {
    Column {
        UpButtonWithText(onBack, stringResource(id = R.string.album))
        AlbumCard(album = album,
            handleFavorite = onHandleFavorite,
            navigateToDetail = null)
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.details_placeholder),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(all = 8.dp)
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun DetailAlbumContentPreview() {
    val simpleEntity = Album(
        "1", Artist("album name", "href"), "name", "imageUrl", 10,
        Price(150f, "usd"), Category(1, "term", "label", "schemeUrl"), null
    )

    DetailAlbumContent(simpleEntity, {}, {})
}
