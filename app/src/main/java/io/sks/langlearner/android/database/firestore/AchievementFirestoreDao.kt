package io.sks.langlearner.android.database.firestore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import io.sks.langlearner.android.model.Achievement
import io.sks.langlearner.android.util.ActionResult
import kotlinx.coroutines.tasks.await


class AchievementFirestoreDao {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun getAllAchievements(): LiveData<List<Achievement>> {
        val achievements: MutableLiveData<List<Achievement>> = MutableLiveData()
        db.collection("users").document(auth.currentUser!!.uid)
            .collection("achievements")
            .addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                if (e != null) {
                    achievements.value = null
                    return@EventListener
                }
                val onlineAchievements: MutableList<Achievement> = mutableListOf()
                for (doc in value!!) {
                    val addressItem = doc.toObject(Achievement::class.java)
                    onlineAchievements.add(addressItem)
                }
                achievements.value = onlineAchievements
            })
        return achievements
    }

    suspend fun updateAchievement(achievement: Achievement): ActionResult {
        return try {
            val achievementUpdateData = if ((achievement.currentCount + 1) == achievement.goalCount)
                mapOf(
                    "currentCount" to FieldValue.increment(1),
                    "achievedAt" to FieldValue.serverTimestamp()
                )
            else
                mapOf(
                    "currentCount" to FieldValue.increment(1)
                )
            db.collection("users").document(auth.currentUser!!.uid)
                .collection("achievements").document(achievement.id).update(
                    achievementUpdateData
                ).await()
            ActionResult(success = true)
        } catch (e: FirebaseException) {
            ActionResult(error = 0)
        }
    }
}

