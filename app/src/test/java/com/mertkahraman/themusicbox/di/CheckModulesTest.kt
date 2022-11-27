package com.mertkahraman.themusicbox.di

import com.mertkahraman.themusicbox.TmbApp
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.check.checkModules

/*
 * Test to verify configuration modules
 */
class CheckModulesTest : KoinTest {

    @Test
    fun `Test Koin Modules`() {
        startKoin {
            androidContext(TmbApp())
            modules(listOf(appModule(), networkingModule()))
        }.checkModules()

        stopKoin()
    }
}
