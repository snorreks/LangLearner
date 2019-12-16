package io.sks.langlearner.android.database.repository
import android.content.Context
import androidx.lifecycle.LiveData
import io.sks.langlearner.android.database.room.HistoryRoomDao
import io.sks.langlearner.android.database.room.LangLearnerRoomDatabase
import io.sks.langlearner.android.model.History
import io.sks.langlearner.android.model.LangCard
import io.sks.langlearner.android.services.FirebaseAuthService.currentUser
import java.util.*


class HistoryRepository(context: Context) {
    private val historyRoomDao: HistoryRoomDao

    init {
        val database = LangLearnerRoomDatabase.getDatabase(context)
        historyRoomDao = database!!.historyDao()
    }

    fun getAllHistories(): LiveData<List<History>> {
        return historyRoomDao.getAllHistories()
    }

    suspend fun insertHistory(langCard: LangCard, resultText: String) {
        val historyId: String = UUID.randomUUID().toString()
        val nativeText = currentUser!!.getNativeText(langCard.text)
        val selectedText = currentUser!!.getNativeText(langCard.text)
        val history = History(historyId,nativeText,selectedText,resultText)
        historyRoomDao.insertHistory(history)
    }
    suspend fun deleteHistory(history: History) {
        historyRoomDao.deleteHistory(history)
    }

    suspend fun deleteAllHistories() {
        historyRoomDao.deleteAllHistories()
    }
}