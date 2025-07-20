package com.ash.bingemaster.di


import com.ash.bingemaster.domain.repository.UserRepository
import com.ash.bingemaster.data.repository.UserRepositoryImpl
import com.ash.bingemaster.ui.auth.AuthViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sharedModule = module {
    single<UserRepository> { UserRepositoryImpl() }
    viewModelOf(::AuthViewModel)
}

expect val targetModule: Module

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null,
) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule, targetModule)
    }
}
