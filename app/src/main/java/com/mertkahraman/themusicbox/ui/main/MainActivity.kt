package com.mertkahraman.themusicbox.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.mertkahraman.themusicbox.ui.searchartist.SearchArtist

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchArtist()
        }
    }
}
