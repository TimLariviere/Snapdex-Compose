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
import com.kanoyatech.snapdex.data.datasources.local.RoomLocalEvolutionChainDataSource
import com.kanoyatech.snapdex.data.datasources.local.RoomLocalPokemonDataSource
import com.kanoyatech.snapdex.data.datasources.local.RoomLocalStatisticsDataSource
import com.kanoyatech.snapdex.data.datasources.local.RoomLocalUserDataSource
import com.kanoyatech.snapdex.data.datasources.local.RoomLocalUserPokemonDataSource
import com.kanoyatech.snapdex.data.datasources.local.SnapdexDatabase
import com.kanoyatech.snapdex.data.datasources.remote.FirebaseRemoteUserDataSource
import com.kanoyatech.snapdex.data.datasources.remote.FirebaseRemoteUserPokemonDataSource
import com.kanoyatech.snapdex.data.datasources.remote.dao.RemoteUserDao
import com.kanoyatech.snapdex.data.datasources.remote.dao.RemoteUserPokemonDao
import com.kanoyatech.snapdex.data.preferences.DataPreferencesStore
import com.kanoyatech.snapdex.data.providers.FirebaseAnalyticsTracker
import com.kanoyatech.snapdex.data.providers.FirebaseAuthProvider
import com.kanoyatech.snapdex.data.providers.FirebaseCrashReporter
import com.kanoyatech.snapdex.domain.Classifier
import com.kanoyatech.snapdex.domain.datasources.LocalEvolutionChainDataSource
import com.kanoyatech.snapdex.domain.datasources.LocalPokemonDataSource
import com.kanoyatech.snapdex.domain.datasources.LocalStatisticsDataSource
import com.kanoyatech.snapdex.domain.datasources.LocalUserDataSource
import com.kanoyatech.snapdex.domain.datasources.LocalUserPokemonDataSource
import com.kanoyatech.snapdex.domain.datasources.RemoteUserDataSource
import com.kanoyatech.snapdex.domain.datasources.RemoteUserPokemonDataSource
import com.kanoyatech.snapdex.domain.preferences.PreferencesStore
import com.kanoyatech.snapdex.domain.providers.AnalyticsTracker
import com.kanoyatech.snapdex.domain.providers.AuthProvider
import com.kanoyatech.snapdex.domain.providers.CrashReporter
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val Context.dataStore by preferencesDataStore("settings")

val dataModule = module {
    // Classifier
    singleOf(::ClassifierFactory).bind<Classifier>()
    singleOf(::OpenAIClassifier)
    singleOf(::TensorflowClassifier)

    // Local data sources
    single {
        Room.databaseBuilder(androidApplication(), SnapdexDatabase::class.java, "snapdex.db")
            .createFromAsset("snapdex.db")
            .build()
    }
    single { get<SnapdexDatabase>().pokemonDao }
    single { get<SnapdexDatabase>().evolutionChainDao }
    single { get<SnapdexDatabase>().userDao }
    single { get<SnapdexDatabase>().userPokemonDao }
    single { get<SnapdexDatabase>().statisticDao }
    singleOf(::RoomLocalEvolutionChainDataSource).bind<LocalEvolutionChainDataSource>()
    singleOf(::RoomLocalPokemonDataSource).bind<LocalPokemonDataSource>()
    singleOf(::RoomLocalStatisticsDataSource).bind<LocalStatisticsDataSource>()
    singleOf(::RoomLocalUserDataSource).bind<LocalUserDataSource>()
    singleOf(::RoomLocalUserPokemonDataSource).bind<LocalUserPokemonDataSource>()

    // Remote data sources
    single { Firebase.firestore }
    singleOf(::RemoteUserDao)
    singleOf(::RemoteUserPokemonDao)
    singleOf(::FirebaseRemoteUserDataSource).bind<RemoteUserDataSource>()
    singleOf(::FirebaseRemoteUserPokemonDataSource).bind<RemoteUserPokemonDataSource>()

    // Preferences
    single<DataStore<Preferences>> { androidContext().dataStore }
    singleOf(::DataPreferencesStore).bind<PreferencesStore>()

    // Providers
    single { Firebase.analytics }
    singleOf(::FirebaseAnalyticsTracker).bind<AnalyticsTracker>()

    single { Firebase.auth }
    singleOf(::FirebaseAuthProvider).bind<AuthProvider>()

    single { Firebase.crashlytics }
    singleOf(::FirebaseCrashReporter).bind<CrashReporter>()
}
