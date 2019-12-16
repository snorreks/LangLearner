package io.sks.langlearner.android.ui.achievements

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.sks.langlearner.android.database.firestore.AchievementFirestoreDao
import io.sks.langlearner.android.database.repository.AchievementRepository
import io.sks.langlearner.android.model.Achievement

class AchievementsViewModel(application: Application) : AndroidViewModel(application) {
    private val achievementRepository = AchievementRepository(AchievementFirestoreDao())
    val achievements: LiveData<List<Achievement>> = achievementRepository.achievements
}