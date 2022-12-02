package com.mertkahraman.themusicbox.ui.artist.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicVideo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mertkahraman.themusicbox.ui.components.HeadlineText
import com.mertkahraman.themusicbox.ui.components.SupportingText
import com.mertkahraman.themusicbox.ui.components.TitleText

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFF,
    widthDp = 450,
    heightDp = 450
)
@Composable
fun WelcomePrompt() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.MusicVideo,
                contentDescription = "",
                tint = MaterialTheme.colors.primary,
                modifier = Modifier
                    .size(60.dp)
                    .padding(bottom = 10.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            TitleText(
                "The Music Box",
                style = MaterialTheme.typography.h3
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 6.dp)
        ) {
            HeadlineText(
                text = "The ultimate source of artists in the music industry. " +
                    "Search for musicians, composers, bands and more.",
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
                maxLines = 4

            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 6.dp)
        ) {
            SupportingText(
                text = "Powered by MusicBrainz API.",
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraLight,
                maxLines = 1

            )
        }
    }
}
