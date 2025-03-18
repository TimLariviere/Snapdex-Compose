package com.kanoyatech.snapdex.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.kanoyatech.snapdex.MainViewModel
import com.kanoyatech.snapdex.data.repositories.PreferencesRepository
import com.kanoyatech.snapdex.data.RoomDataSource
import com.kanoyatech.snapdex.data.SnapdexDatabase
import com.kanoyatech.snapdex.data.repositories.UserRepository
import com.kanoyatech.snapdex.domain.DataSource
import com.kanoyatech.snapdex.services.PokemonClassifier
import com.kanoyatech.snapdex.ui.auth.forgot_password.ForgotPasswordViewModel
import com.kanoyatech.snapdex.ui.auth.login.LoginViewModel
import com.kanoyatech.snapdex.ui.main.pokedex.PokedexViewModel
import com.kanoyatech.snapdex.ui.main.pokemon_detail.PokemonDetailViewModel
import com.kanoyatech.snapdex.ui.main.profile.ProfileViewModel
import com.kanoyatech.snapdex.ui.auth.register.RegisterViewModel
import com.kanoyatech.snapdex.ui.intro.IntroViewModel
import com.kanoyatech.snapdex.ui.main.stats.StatsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val Context.dataStore by preferencesDataStore("settings")

val dataModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            SnapdexDatabase::class.java,
            "snapdex.db"
        )
            .createFromAsset("snapdex.db")
            .build()
    }

    single { get<SnapdexDatabase>().pokemonDao }
    single { get<SnapdexDatabase>().evolutionChainDao }
    single { get<SnapdexDatabase>().userDao }
    single { get<SnapdexDatabase>().userPokemonDao }

    singleOf(::RoomDataSource).bind<DataSource>()

    single<DataStore<Preferences>> { androidContext().dataStore }
    singleOf(::PreferencesRepository)

    single { Firebase.firestore }
    singleOf(::UserRepository)
}

val uiModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::IntroViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::ForgotPasswordViewModel)
    viewModelOf(::PokedexViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::StatsViewModel)
    viewModel { parameters -> PokemonDetailViewModel(get(), parameters.get()) }
}

val authModule = module {
    single { Firebase.auth }
}

val servicesModule = module {
    singleOf(::PokemonClassifier)
}