package com.kanoyatech.snapdex.di

import com.kanoyatech.snapdex.MainActivityViewModel
import com.kanoyatech.snapdex.domain.UserDataValidator
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::MainActivityViewModel)
    singleOf(::UserDataValidator)
}
