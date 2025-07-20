package com.ash.bingemaster.domain.repository

import com.ash.bingemaster.core.util.RequestState
import dev.gitlive.firebase.auth.FirebaseUser

interface UserRepository {
    fun getCurrentUserId(): String?
    suspend fun createUser(
        user: FirebaseUser?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    )

    suspend fun signOut(): RequestState<Unit>
}
