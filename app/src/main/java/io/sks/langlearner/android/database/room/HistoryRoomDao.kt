package io.sks.langlearner.android.database.room

import androidx.lifecycle.LiveData
import androidx.room.*
import io.sks.langlearner.android.model.History

@Dao
interface HistoryRoomDao {
    @Insert
    suspend fun insertHistory(history: History)

    @Query("SELECT * FROM History ORDER BY createdAt")
    fun getAllHistories(): LiveData<List<History>>

    @Query("DELETE FROM History")
    suspend fun deleteAllHistories()

    @Delete
    suspend fun deleteHistory(history: History)
}