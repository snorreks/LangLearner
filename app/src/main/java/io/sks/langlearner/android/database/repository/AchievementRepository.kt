package io.sks.langlearner.android.database.repository

import androidx.lifecycle.LiveData
import io.sks.langlearner.android.database.firestore.AchievementFirestoreDao
import io.sks.langlearner.android.model.Achievement
import io.sks.langlearner.android.model.LangCard

class AchievementRepository(private val achievementFirestoreDao: AchievementFirestoreDao) {

    val achievements: LiveData<List<Achievement>>
        get() {
            return achievementFirestoreDao.getAllAchievements()
        }

    suspend fun updateAchievement(langCard: LangCard) {
        val selectedAchievements =
            achievementFirestoreDao.getAllAchievementsFuture()
                .filter { achievement -> achievement.category == langCard.type || achievement.category == "common" }
        selectedAchievements.forEach { achievement ->
            achievementFirestoreDao.updateAchievement(achievement)
        }
    }
}