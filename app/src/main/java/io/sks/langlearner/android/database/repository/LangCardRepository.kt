package io.sks.langlearner.android.database.repository

import androidx.lifecycle.LiveData
import io.sks.langlearner.android.database.firestore.LangCardFirestoreDao
import io.sks.langlearner.android.model.LangCard

class LangCardRepository(private val langCardFirestoreDao: LangCardFirestoreDao) {

    fun getLangCards(): LiveData<List<LangCard>> {
        return langCardFirestoreDao.getLangCards()
    }
}