package com.mertkahraman.themusicbox.ui.searchartist

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    searchState: SearchUIState,
    onSearchEvent: (SearchUIEvent) -> Unit
) {
    val localFocus = LocalFocusManager.current
    val focusRequester = FocusRequester()
    var searchQuery by remember { mutableStateOf(searchState.searchQuery) }

    TextField(
        value = searchState.searchQuery,
        onValueChange = { value ->
            if (value.isNotEmpty()) {
                searchQuery = value
                onSearchEvent(SearchUIEvent.SearchValueChanged(searchQuery))
            } else {
                onSearchEvent(SearchUIEvent.SearchCancelled)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            if (searchState.searchQuery.isEmpty()) {
                LeadingIconButton(
                    icon = Icons.Default.Search,
                    onClick = {
                        focusRequester.requestFocus()
                        onSearchEvent(SearchUIEvent.SearchValueChanged(searchQuery))
                    },
                )
            } else {
                LeadingIconButton(
                    icon = Icons.Default.Close,
                    onClick = {
                        searchQuery = ""
                        localFocus.clearFocus()
                        onSearchEvent(SearchUIEvent.SearchCancelled)
                    },
                )
            }
        },
        singleLine = true,
        shape = RectangleShape,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            backgroundColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
private fun LeadingIconButton(
    icon: ImageVector,
    onClick: () -> Unit
) {
    IconButton(onClick) {
        Icon(
            icon,
            contentDescription = "",
            modifier = Modifier
                .padding(15.dp)
                .size(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchViewPreview() {
    val searchState = remember { SearchUIState("", false) }
    SearchBar(searchState) { }
}
