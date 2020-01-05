package io.sks.langlearner.android.database.repository
import android.content.Context
import androidx.lifecycle.LiveData
import io.sks.langlearner.android.database.room.HistoryRoomDao
import io.sks.langlearner.android.database.room.LangLearnerRoomDatabase
import io.sks.langlearner.android.model.History


class HistoryRepository(context: Context) {
    private val historyRoomDao: HistoryRoomDao

    init {
        val database = LangLearnerRoomDatabase.getDatabase(context)
        historyRoomDao = database!!.historyDao()
    }

    fun getAllHistories(): LiveData<List<History>> {
        return historyRoomDao.getAllHistories()
    }

    suspend fun insertHistory(history: History) {
        historyRoomDao.insertHistory(history)
    }
    suspend fun deleteHistory(history: History) {
        historyRoomDao.deleteHistory(history)
    }

    suspend fun deleteAllHistories() {
        historyRoomDao.deleteAllHistories()
    }
}