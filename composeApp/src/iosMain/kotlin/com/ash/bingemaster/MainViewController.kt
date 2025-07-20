package com.ash.bingemaster

import androidx.compose.ui.window.ComposeUIViewController
import com.ash.bingemaster.di.initializeKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initializeKoin() }
) { App() }
