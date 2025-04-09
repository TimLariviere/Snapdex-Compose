package com.kanoyatech.snapdex.ui.di

import com.kanoyatech.snapdex.ui.AppLocaleManager
import com.kanoyatech.snapdex.ui.auth.forgot_password.ForgotPasswordViewModel
import com.kanoyatech.snapdex.ui.auth.login.LoginViewModel
import com.kanoyatech.snapdex.ui.auth.register.RegisterViewModel
import com.kanoyatech.snapdex.ui.intro.IntroViewModel
import com.kanoyatech.snapdex.ui.main.MainViewModel
import com.kanoyatech.snapdex.ui.main.pokedex.PokedexViewModel
import com.kanoyatech.snapdex.ui.main.pokemon_detail.PokemonDetailViewModel
import com.kanoyatech.snapdex.ui.main.profile.ProfileViewModel
import com.kanoyatech.snapdex.ui.main.profile.choose_aimodel.ChooseAIModelViewModel
import com.kanoyatech.snapdex.ui.main.profile.new_name.NewNameViewModel
import com.kanoyatech.snapdex.ui.main.profile.new_password.NewPasswordViewModel
import com.kanoyatech.snapdex.ui.main.stats.StatsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::IntroViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::ForgotPasswordViewModel)
    viewModelOf(::MainViewModel)
    viewModel { parameters -> PokedexViewModel(parameters.get(), get(), get(), get()) }
    viewModel { parameters -> ProfileViewModel(parameters.get(), get(), get(), androidApplication(), get(), get()) }
    viewModelOf(::StatsViewModel)
    viewModel { parameters -> PokemonDetailViewModel(parameters.get(), get(), get()) }
    viewModel { parameters -> NewNameViewModel(parameters.get(), get(), get()) }
    viewModelOf(::NewPasswordViewModel)
    viewModelOf(::ChooseAIModelViewModel)
    singleOf(::AppLocaleManager)
}