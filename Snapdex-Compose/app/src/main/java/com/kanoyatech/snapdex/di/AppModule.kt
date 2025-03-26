package com.kanoyatech.snapdex.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.kanoyatech.snapdex.MainActivityViewModel
import com.kanoyatech.snapdex.data.classifiers.OpenAIClassifier
import com.kanoyatech.snapdex.data.repositories.PreferencesRepositoryImpl
import com.kanoyatech.snapdex.data.local.SnapdexDatabase
import com.kanoyatech.snapdex.data.remote.datasources.RemoteUserDataSource
import com.kanoyatech.snapdex.data.remote.datasources.RemoteUserPokemonDataSource
import com.kanoyatech.snapdex.data.repositories.EvolutionChainRepositoryImpl
import com.kanoyatech.snapdex.data.repositories.PokemonRepositoryImpl
import com.kanoyatech.snapdex.data.repositories.UserRepositoryImpl
import com.kanoyatech.snapdex.domain.repositories.UserRepository
import com.kanoyatech.snapdex.data.classifiers.TensorflowClassifier
import com.kanoyatech.snapdex.data.repositories.EncryptedPreferencesRepositoryImpl
import com.kanoyatech.snapdex.data.repositories.StatisticsRepositoryImpl
import com.kanoyatech.snapdex.domain.Classifier
import com.kanoyatech.snapdex.domain.ClassifierFactory
import com.kanoyatech.snapdex.domain.UserDataValidator
import com.kanoyatech.snapdex.domain.repositories.EncryptedPreferencesRepository
import com.kanoyatech.snapdex.domain.repositories.EvolutionChainRepository
import com.kanoyatech.snapdex.domain.repositories.PokemonRepository
import com.kanoyatech.snapdex.domain.repositories.PreferencesRepository
import com.kanoyatech.snapdex.domain.repositories.StatisticsRepository
import com.kanoyatech.snapdex.ui.AppLocaleManager
import com.kanoyatech.snapdex.ui.auth.forgot_password.ForgotPasswordViewModel
import com.kanoyatech.snapdex.ui.auth.login.LoginViewModel
import com.kanoyatech.snapdex.ui.main.pokedex.PokedexViewModel
import com.kanoyatech.snapdex.ui.main.pokemon_detail.PokemonDetailViewModel
import com.kanoyatech.snapdex.ui.main.profile.ProfileViewModel
import com.kanoyatech.snapdex.ui.auth.register.RegisterViewModel
import com.kanoyatech.snapdex.ui.intro.IntroViewModel
import com.kanoyatech.snapdex.ui.main.MainViewModel
import com.kanoyatech.snapdex.ui.main.profile.choose_aimodel.ChooseAIModelViewModel
import com.kanoyatech.snapdex.ui.main.profile.new_name.NewNameViewModel
import com.kanoyatech.snapdex.ui.main.profile.new_password.NewPasswordViewModel
import com.kanoyatech.snapdex.ui.main.stats.StatsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.dsl.single

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
    single { get<SnapdexDatabase>().statisticDao }
}

val dataRemoteModule = module {
    single { Firebase.firestore }

    // Data sources
    singleOf(::RemoteUserDataSource)
    singleOf(::RemoteUserPokemonDataSource)
}

val dataModule = module {
    single<DataStore<Preferences>> { androidContext().dataStore }
    singleOf(::OpenAIClassifier)
    singleOf(::TensorflowClassifier)
    singleOf(::PreferencesRepositoryImpl).bind<PreferencesRepository>()
    singleOf(::EncryptedPreferencesRepositoryImpl).bind<EncryptedPreferencesRepository>()
    singleOf(::UserRepositoryImpl).bind<UserRepository>()
    singleOf(::PokemonRepositoryImpl).bind<PokemonRepository>()
    singleOf(::EvolutionChainRepositoryImpl).bind<EvolutionChainRepository>()
    singleOf(::StatisticsRepositoryImpl).bind<StatisticsRepository>()
}

val uiModule = module {
    viewModelOf(::MainActivityViewModel)
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

val authModule = module {
    single { Firebase.auth }
    single { Firebase.analytics }
}

val domainModule = module {
    singleOf(::UserDataValidator)
    singleOf(::ClassifierFactory).bind<Classifier>()
}