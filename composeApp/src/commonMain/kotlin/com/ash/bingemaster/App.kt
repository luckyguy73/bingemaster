package com.ash.bingemaster

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.ash.bingemaster.domain.repository.UserRepository
import com.ash.bingemaster.ui.auth.AuthScreen
import com.ash.bingemaster.core.util.Constants
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    MaterialTheme {
        val userRepository = koinInject<UserRepository>()
        var appReady by remember { mutableStateOf(false) }
        val isUserAuthenticated = remember { userRepository.getCurrentUserId() != null }
//        val startDestination = remember {
//            if (isUserAuthenticated) Screen.HomeGraph
//            else Screen.Auth
//        }

        LaunchedEffect(Unit) {
            GoogleAuthProvider.create(
                credentials = GoogleAuthCredentials(serverId = Constants.WEB_CLIENT_ID)
            )
            appReady = true
        }

        AnimatedVisibility(
            modifier = Modifier.fillMaxSize(),
            visible = appReady
        ) {
            AuthScreen()
//            SetupNavGraph(
////                startDestination = startDestination
//            )
        }
    }
}
