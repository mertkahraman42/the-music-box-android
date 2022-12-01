package com.mertkahraman.themusicbox.ui.releasegroup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mertkahraman.themusicbox.data.model.ReleaseGroup

// TODO: Revise UI
@Composable
fun ReleaseGroupItem(
    releaseGroup: ReleaseGroup,
    onItemClick: (ReleaseGroup) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .clickable { onItemClick(releaseGroup) }
    ) {
        Row(
            modifier = Modifier
                .padding(start = 20.dp, top = 4.dp, bottom = 4.dp, end = 4.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = releaseGroup.title)
        }

        Row(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = releaseGroup.firstReleaseDate)
        }
    }
}
