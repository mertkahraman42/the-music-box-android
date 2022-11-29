package com.mertkahraman.themusicbox.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// TODO: UI of all Composables here can be improved, firstly by app specific theme colors.

/*
 * Use when displaying loading state for the initial request
 */
@Composable
fun FullscreenSpinner(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

/*
 * Use when paginating at the end of lists
 */
@Composable
fun SpinnerListItem() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

/*
 * Use when there's no results from repository
 */
@Composable
fun EmptyListIndicator(
    modifier: Modifier = Modifier,
    query: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(
                text = "No results for '$query'",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center
            )
        }
        Row {
            Text(
                text = "Try searching for something else.",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.primaryVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ErrorListItem(
    modifier: Modifier = Modifier,
    errorMessage: String,
    onRetry: () -> Unit
) {
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = errorMessage,
            maxLines = 1,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.h6,
            color = Color.Red
        )
        OutlinedButton(onRetry) {
            Text(text = "Retry")
        }
    }
}
