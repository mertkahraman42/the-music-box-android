package com.mertkahraman.themusicbox.ui.main

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.mertkahraman.themusicbox.ui.searchartist.SearchArtist

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TmbApp() {
    Scaffold {
        // TODO: Wrap inside nav host
        SearchArtist()
    }
}
