package io.sks.langlearner.android.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.sks.langlearner.android.database.firestore.AchievementFirestoreDao
import io.sks.langlearner.android.database.firestore.LangCardFirestoreDao
import io.sks.langlearner.android.database.repository.AchievementRepository
import io.sks.langlearner.android.database.repository.HistoryRepository
import io.sks.langlearner.android.database.repository.LangCardRepository
import io.sks.langlearner.android.model.History
import io.sks.langlearner.android.model.LangCard
import io.sks.langlearner.android.services.FirebaseAuthService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val langCardRepository = LangCardRepository(LangCardFirestoreDao())
    private val historyRepository = HistoryRepository(application.applicationContext)
    private val achievementRepository = AchievementRepository(AchievementFirestoreDao())
    private val ioScope = CoroutineScope(Dispatchers.Default)
    val langCards: LiveData<List<LangCard>> = langCardRepository.getLangCards()

    private fun updateAchievement(langCard: LangCard) {
        ioScope.launch {
            achievementRepository.updateAchievement(langCard)

        }
    }

    private fun addHistory(langCard: LangCard, resultText: String) {
        val nativeText = FirebaseAuthService.currentUser!!.getNativeText(langCard.text)
        val selectedText = FirebaseAuthService.currentUser!!.getSelectedText(langCard.text)
        val history = History(nativeText, selectedText, resultText)
        ioScope.launch {
            historyRepository.insertHistory(history)
        }
    }

    fun submit(langCard: LangCard, resultText: String): Boolean {
        val selectedText = FirebaseAuthService.currentUser!!.getSelectedText(langCard.text)
        val success = selectedText.toLowerCase() == resultText.toLowerCase()
        if (success) {
            updateAchievement(langCard)
        }
        addHistory(langCard, resultText)
        return success

    }


}