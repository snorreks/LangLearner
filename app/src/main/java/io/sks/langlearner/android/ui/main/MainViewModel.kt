package io.sks.langlearner.android.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.sks.langlearner.android.database.repository.HistoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val historyRepository = HistoryRepository(application.applicationContext)

    fun deleteAllHistories() {
        ioScope.launch {
            historyRepository.deleteAllHistories()
        }
    }
}
