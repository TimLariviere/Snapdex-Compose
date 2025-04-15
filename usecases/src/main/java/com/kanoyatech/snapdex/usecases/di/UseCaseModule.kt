package com.kanoyatech.snapdex.usecases.di

import com.kanoyatech.snapdex.usecases.AuthService
import com.kanoyatech.snapdex.usecases.PokemonService
import com.kanoyatech.snapdex.usecases.SyncService
import com.kanoyatech.snapdex.usecases.UserService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::AuthService)
    singleOf(::PokemonService)
    singleOf(::SyncService)
    singleOf(::UserService)
}
