package com.mertkahraman.themusicbox.ui.releasegroup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mertkahraman.themusicbox.data.model.ReleaseGroup
import com.mertkahraman.themusicbox.ui.components.HeadlineText
import com.mertkahraman.themusicbox.ui.components.SupportingText

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReleaseGroupItem(
    releaseGroup: ReleaseGroup,
    onItemClick: (ReleaseGroup) -> Unit = {},
) {
    Row(
        modifier = Modifier
            .clickable { onItemClick(releaseGroup) }
    ) {
        ListItem(
            text = {
                SupportingText(
                    text = releaseGroup.title,
                    color = MaterialTheme.colors.onBackground,
                )
            },
            secondaryText = {
                HeadlineText(
                    text = releaseGroup.firstReleaseDate,
                    fontWeight = FontWeight.Light,
                    style = MaterialTheme.typography.subtitle2
                )
            },
            trailing = {
                releaseGroup.primaryType?.let {
                    Row() {
                        ReleaseGroupTypeIndicator(it)
                    }
                }
            }
        )
    }
}

@Composable
private fun ReleaseGroupTypeIndicator(type: ReleaseGroup.PrimaryType) {
    val icon =
        if (type == ReleaseGroup.PrimaryType.ALBUM)
            Icons.Default.QueueMusic
        else
            Icons.Default.Album

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.Bottom) {
            HeadlineText(
                text = type.value,
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.4f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Light,
                style = MaterialTheme.typography.caption,
                maxLines = 1
            )
        }
        Row(
            verticalAlignment = Alignment.Bottom,
        ) {
            Icon(
                icon,
                contentDescription = "",
                tint = MaterialTheme.colors.onBackground.copy(alpha = 0.8f),
                modifier = Modifier
                    .size(18.dp)
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFF,
    widthDp = 300
)
@Composable
fun ReleaseGroupItemPreview() {
    val releaseGroup = ReleaseGroup(
        "",
        "The Beatles with Tony Sheridan and Guests live in Manchester.",
        "1964-02-03",
        ReleaseGroup.PrimaryType.ALBUM
    )
    ReleaseGroupItem(releaseGroup = releaseGroup)
}
