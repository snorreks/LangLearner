package io.sks.langlearner.android.ui.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.sks.langlearner.android.database.repository.HistoryRepository
import io.sks.langlearner.android.model.History
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val historyRepository = HistoryRepository(application.applicationContext)
    val histories: LiveData<List<History>> = historyRepository.getAllHistories()
    fun deleteHistory(history: History) {
        ioScope.launch {
            historyRepository.deleteHistory(history)
        }
    }
    fun deleteAllHistories() {
        ioScope.launch {
            historyRepository.deleteAllHistories()
        }
    }
}
