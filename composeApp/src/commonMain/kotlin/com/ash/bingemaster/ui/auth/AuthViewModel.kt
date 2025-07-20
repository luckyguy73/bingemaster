package com.ash.bingemaster.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ash.bingemaster.domain.repository.UserRepository
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class AuthViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {
    fun createUser(
        user: FirebaseUser?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.createUser(
                user = user,
                onSuccess = onSuccess,
                onError = onError
            )
        }
    }
}
