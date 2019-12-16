package io.sks.langlearner.android.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.sks.langlearner.android.database.firestore.AchievementFirestoreDao
import io.sks.langlearner.android.database.firestore.LangCardFirestoreDao
import io.sks.langlearner.android.database.repository.AchievementRepository
import io.sks.langlearner.android.database.repository.HistoryRepository
import io.sks.langlearner.android.database.repository.LangCardRepository
import io.sks.langlearner.android.model.LangCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val langCardRepository = LangCardRepository(LangCardFirestoreDao())
    private val historyRepository = HistoryRepository(application.applicationContext)
    private val achievementRepository = AchievementRepository(AchievementFirestoreDao())
    private val ioScope = CoroutineScope(Dispatchers.IO)
    val langCards: LiveData<List<LangCard>> = langCardRepository.getLangCards()

     fun updateAchievment(langCard: LangCard) {
        ioScope.launch {
            achievementRepository.updateAchievement(langCard)

        }
    }
     fun addHistory(langCard: LangCard, resultText: String) {
        ioScope.launch {
            historyRepository.insertHistory(langCard,resultText)
        }
    }
}