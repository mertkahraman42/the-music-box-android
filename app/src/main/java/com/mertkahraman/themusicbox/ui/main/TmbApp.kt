package com.mertkahraman.themusicbox.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.mertkahraman.themusicbox.nav.TmbNavHost

@Composable
fun TmbApp() {
    val navController = rememberNavController()

    Scaffold { innerPadding ->
        TmbNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
        )
    }
}
