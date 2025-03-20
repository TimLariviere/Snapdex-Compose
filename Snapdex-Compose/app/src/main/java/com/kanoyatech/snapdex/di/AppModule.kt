package com.kanoyatech.snapdex.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.kanoyatech.snapdex.MainActivityViewModel
import com.kanoyatech.snapdex.data.repositories.PreferencesRepository
import com.kanoyatech.snapdex.data.local.SnapdexDatabase
import com.kanoyatech.snapdex.data.remote.datasources.RemoteUserDataSource
import com.kanoyatech.snapdex.data.remote.datasources.RemoteUserPokemonDataSource
import com.kanoyatech.snapdex.data.repositories.EvolutionChainRepositoryImpl
import com.kanoyatech.snapdex.data.repositories.PokemonRepositoryImpl
import com.kanoyatech.snapdex.data.repositories.UserRepositoryImpl
import com.kanoyatech.snapdex.domain.repositories.UserRepository
import com.kanoyatech.snapdex.domain.PokemonClassifier
import com.kanoyatech.snapdex.domain.UserDataValidator
import com.kanoyatech.snapdex.domain.repositories.EvolutionChainRepository
import com.kanoyatech.snapdex.domain.repositories.PokemonRepository
import com.kanoyatech.snapdex.ui.auth.forgot_password.ForgotPasswordViewModel
import com.kanoyatech.snapdex.ui.auth.login.LoginViewModel
import com.kanoyatech.snapdex.ui.main.pokedex.PokedexViewModel
import com.kanoyatech.snapdex.ui.main.pokemon_detail.PokemonDetailViewModel
import com.kanoyatech.snapdex.ui.main.profile.ProfileViewModel
import com.kanoyatech.snapdex.ui.auth.register.RegisterViewModel
import com.kanoyatech.snapdex.ui.intro.IntroViewModel
import com.kanoyatech.snapdex.ui.main.MainViewModel
import com.kanoyatech.snapdex.ui.main.stats.StatsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val Context.dataStore by preferencesDataStore("settings")

val dataLocalModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            SnapdexDatabase::class.java,
            "snapdex.db"
        )
            .createFromAsset("snapdex.db")
            .build()
    }

    // DAO
    single { get<SnapdexDatabase>().pokemonDao }
    single { get<SnapdexDatabase>().evolutionChainDao }
    single { get<SnapdexDatabase>().userDao }
    single { get<SnapdexDatabase>().userPokemonDao }
}

val dataRemoteModule = module {
    single { Firebase.firestore }

    // Data sources
    singleOf(::RemoteUserDataSource)
    singleOf(::RemoteUserPokemonDataSource)
}

val dataModule = module {
    single<DataStore<Preferences>> { androidContext().dataStore }
    singleOf(::PreferencesRepository)
    singleOf(::UserRepositoryImpl).bind<UserRepository>()
    singleOf(::PokemonRepositoryImpl).bind<PokemonRepository>()
    singleOf(::EvolutionChainRepositoryImpl).bind<EvolutionChainRepository>()
}

val uiModule = module {
    viewModelOf(::MainActivityViewModel)
    viewModelOf(::IntroViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::ForgotPasswordViewModel)
    viewModelOf(::MainViewModel)
    viewModel { parameters -> PokedexViewModel(parameters.get(), get()) }
    viewModel { parameters -> ProfileViewModel(parameters.get(), get()) }
    viewModelOf(::StatsViewModel)
    viewModel { parameters -> PokemonDetailViewModel(parameters.get(), get(), get()) }
}

val authModule = module {
    single { Firebase.auth }
}

val servicesModule = module {
    singleOf(::PokemonClassifier)
}

val domainModule = module {
    singleOf(::UserDataValidator)
}