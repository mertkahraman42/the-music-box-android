package com.mertkahraman.themusicbox.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.mertkahraman.themusicbox.repo.Repository
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val repo: Repository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // TODO: Add composables
        }
    }
}
