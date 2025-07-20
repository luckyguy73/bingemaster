package com.ash.bingemaster.ui.auth

import ContentWithMessageBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ash.bingemaster.ui.components.GoogleButton
import com.ash.bingemaster.core.util.Alpha
import com.mmk.kmpauth.firebase.google.GoogleButtonUiContainerFirebase
import org.koin.compose.viewmodel.koinViewModel
import rememberMessageBarState


@Composable
fun AuthScreen(
//    navigateToHome: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val viewModel = koinViewModel<AuthViewModel>()
    val messageBarState = rememberMessageBarState()
    var loadingState by remember { mutableStateOf(false) }

    Scaffold { padding ->
        ContentWithMessageBar(
            contentBackgroundColor = Color(0xFFFFFFFF),
            modifier = Modifier
                .padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                ),
            messageBarState = messageBarState,
            errorMaxLines = 2,
            errorContainerColor = Color(0xFFDD0000),
            errorContentColor = Color(0xFFFFFFFF),
            successContainerColor = Color(0xFFEEFF00),
            successContentColor = Color(0xFF000000)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 24.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "BingeMaster",
                        textAlign = TextAlign.Center,
                        fontSize = 40.sp,
                        color = Color(0xFFF24C00)
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(Alpha.HALF),
                        text = "Sign in to continue",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        color = Color(0xFF000000)
                    )
                }
                GoogleButtonUiContainerFirebase(
                    linkAccount = false,
                    onResult = { result ->
                        result.onSuccess { user ->
                            messageBarState.addSuccess("Authentication successful!")
//                            viewModel.createUser(
//                                user = user,
//                                onSuccess = {
//                                    scope.launch {
//                                        messageBarState.addSuccess("Authentication successful!")
////                                        delay(2000)
////                                        navigateToHome()
//                                    }
//                                },
//                                onError = { message -> messageBarState.addError(message) }
//                            )
                            loadingState = false
                        }.onFailure { error ->
                            if (error.message?.contains("A network error") == true) {
                                messageBarState.addError("Internet connection unavailable.")
                            } else if (error.message?.contains("Idtoken is null") == true) {
                                messageBarState.addError("Sign in canceled.")
                            } else {
                                messageBarState.addError(error.message ?: "Unknown")
                            }
                            loadingState = false
                        }
                    }
                ) {
                    GoogleButton(
                        loading = loadingState,
                        onClick = {
                            loadingState = true
                            this@GoogleButtonUiContainerFirebase.onClick()
                        }
                    )
                }
            }
        }
    }
}
