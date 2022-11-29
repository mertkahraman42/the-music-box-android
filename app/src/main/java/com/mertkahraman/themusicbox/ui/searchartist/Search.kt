package com.mertkahraman.themusicbox.ui.searchartist

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchView(
    searchState: SearchUIState,
    onSearchEvent: (SearchUIEvent) -> Unit
) {
    var searchQuery by remember { mutableStateOf(searchState.searchQuery) }
    TextField(
        value = searchState.searchQuery,
        onValueChange = { value ->
            if (value.isNotEmpty()) {
                searchQuery = value
                onSearchEvent(SearchUIEvent.SearchValueChanged(searchQuery))
            } else {
                onSearchEvent(SearchUIEvent.SearchCleared)
            }
        },
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            if (searchState.searchQuery.isEmpty()) {
                SearchIconButton(
                    icon = Icons.Default.Search,
                    onClick = {
                        onSearchEvent(SearchUIEvent.SearchValueChanged(searchQuery))
                    },
                )
            } else {
                SearchIconButton(
                    icon = Icons.Default.Close,
                    onClick = {
                        searchQuery = ""
                        onSearchEvent(SearchUIEvent.SearchCleared)
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
private fun SearchIconButton(
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
    SearchView(searchState) { }
}
