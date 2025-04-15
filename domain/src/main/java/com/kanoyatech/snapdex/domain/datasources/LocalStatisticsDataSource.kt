package com.kanoyatech.snapdex.domain.datasources

import com.kanoyatech.snapdex.domain.models.Statistic
import com.kanoyatech.snapdex.domain.models.StatisticByType
import com.kanoyatech.snapdex.domain.models.UserId
import kotlinx.coroutines.flow.Flow

interface LocalStatisticsDataSource {
    fun getCompletionRate(userId: UserId): Flow<Statistic>

    fun getCompletionRateByType(userId: UserId): Flow<List<StatisticByType>>
}
