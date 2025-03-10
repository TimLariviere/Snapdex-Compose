package com.kanoyatech.snapdex.di

import androidx.room.Room
import com.kanoyatech.snapdex.data.RoomDataSource
import com.kanoyatech.snapdex.data.SnapdexDatabase
import com.kanoyatech.snapdex.domain.DataSource
import com.kanoyatech.snapdex.ui.pokedex.PokedexViewModel
import com.kanoyatech.snapdex.ui.pokemon_details.PokemonDetailsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

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

    single {
        get<SnapdexDatabase>().pokemonDao
    }

    singleOf(::RoomDataSource).bind<DataSource>()
}

val uiModule = module {
    viewModelOf(::PokedexViewModel)
    viewModel { parameters -> PokemonDetailsViewModel(parameters.get()) }
}