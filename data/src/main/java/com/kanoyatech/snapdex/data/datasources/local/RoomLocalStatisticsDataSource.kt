package com.kanoyatech.snapdex.data.datasources.local

import com.kanoyatech.snapdex.data.datasources.local.dao.StatisticDao
import com.kanoyatech.snapdex.domain.datasources.LocalStatisticsDataSource
import com.kanoyatech.snapdex.domain.models.PokemonType
import com.kanoyatech.snapdex.domain.models.Statistic
import com.kanoyatech.snapdex.domain.models.StatisticByType
import com.kanoyatech.snapdex.domain.models.UserId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalStatisticsDataSource(private val statisticDao: StatisticDao) :
    LocalStatisticsDataSource {
    override fun getCompletionRate(userId: UserId): Flow<Statistic> {
        return statisticDao.getCompletionRate(userId).map {
            Statistic(it.totalPokemonCount, it.caughtPokemonCount)
        }
    }

    override fun getCompletionRateByType(userId: UserId): Flow<List<StatisticByType>> {
        return statisticDao.getCompletionRateByType(userId).map { types ->
            types.map {
                StatisticByType(
                    type = PokemonType.Companion.fromInt(it.type),
                    statistic = Statistic(it.totalPokemonCount, it.caughtPokemonCount),
                )
            }
        }
    }
}
