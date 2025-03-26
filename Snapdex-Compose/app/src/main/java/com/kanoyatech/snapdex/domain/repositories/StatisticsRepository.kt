package com.kanoyatech.snapdex.domain.repositories

import com.kanoyatech.snapdex.domain.models.Statistic
import com.kanoyatech.snapdex.domain.models.StatisticByType
import com.kanoyatech.snapdex.domain.models.UserId
import kotlinx.coroutines.flow.Flow

interface StatisticsRepository {
    fun getCompletionRate(userId: UserId): Flow<Statistic>
    fun getCompletionRateByType(userId: UserId): Flow<List<StatisticByType>>
}