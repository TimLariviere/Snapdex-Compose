package com.kanoyatech.snapdex.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.auth.auth
import com.google.firebase.crashlytics.crashlytics
import com.google.firebase.firestore.firestore
import com.kanoyatech.snapdex.data.classifiers.ClassifierFactory
import com.kanoyatech.snapdex.data.classifiers.OpenAIClassifier
import com.kanoyatech.snapdex.data.classifiers.TensorflowClassifier
import com.kanoyatech.snapdex.data.local.SnapdexDatabase
import com.kanoyatech.snapdex.data.remote.datasources.RemoteUserDataSource
import com.kanoyatech.snapdex.data.remote.datasources.RemoteUserPokemonDataSource
import com.kanoyatech.snapdex.data.repositories.EncryptedPreferencesRepositoryImpl
import com.kanoyatech.snapdex.data.repositories.EvolutionChainRepositoryImpl
import com.kanoyatech.snapdex.data.repositories.PokemonRepositoryImpl
import com.kanoyatech.snapdex.data.repositories.PreferencesRepositoryImpl
import com.kanoyatech.snapdex.data.repositories.StatisticsRepositoryImpl
import com.kanoyatech.snapdex.data.repositories.SyncRepositoryImpl
import com.kanoyatech.snapdex.data.repositories.UserRepositoryImpl
import com.kanoyatech.snapdex.domain.Classifier
import com.kanoyatech.snapdex.domain.repositories.EncryptedPreferencesRepository
import com.kanoyatech.snapdex.domain.repositories.EvolutionChainRepository
import com.kanoyatech.snapdex.domain.repositories.PokemonRepository
import com.kanoyatech.snapdex.domain.repositories.PreferencesRepository
import com.kanoyatech.snapdex.domain.repositories.StatisticsRepository
import com.kanoyatech.snapdex.domain.repositories.SyncRepository
import com.kanoyatech.snapdex.domain.repositories.UserRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val Context.dataStore by preferencesDataStore("settings")

val dataModule = module {
    single { Firebase.auth }
    single { Firebase.analytics }
    single { Firebase.crashlytics }

    single {
        Room.databaseBuilder(androidApplication(), SnapdexDatabase::class.java, "snapdex.db")
            .createFromAsset("snapdex.db")
            .build()
    }

    // DAO
    single { get<SnapdexDatabase>().pokemonDao }
    single { get<SnapdexDatabase>().evolutionChainDao }
    single { get<SnapdexDatabase>().userDao }
    single { get<SnapdexDatabase>().userPokemonDao }
    single { get<SnapdexDatabase>().statisticDao }

    single { Firebase.firestore }

    // Data sources
    singleOf(::RemoteUserDataSource)
    singleOf(::RemoteUserPokemonDataSource)

    single<DataStore<Preferences>> { androidContext().dataStore }
    singleOf(::OpenAIClassifier)
    singleOf(::TensorflowClassifier)
    singleOf(::PreferencesRepositoryImpl).bind<PreferencesRepository>()
    singleOf(::EncryptedPreferencesRepositoryImpl).bind<EncryptedPreferencesRepository>()
    singleOf(::UserRepositoryImpl).bind<UserRepository>()
    singleOf(::PokemonRepositoryImpl).bind<PokemonRepository>()
    singleOf(::EvolutionChainRepositoryImpl).bind<EvolutionChainRepository>()
    singleOf(::StatisticsRepositoryImpl).bind<StatisticsRepository>()
    singleOf(::SyncRepositoryImpl).bind<SyncRepository>()
    singleOf(::ClassifierFactory).bind<Classifier>()
}
