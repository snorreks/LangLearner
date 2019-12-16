package io.sks.langlearner.android.database.firestore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import io.sks.langlearner.android.model.LangCard


class LangCardFirestoreDao {
    private val db = FirebaseFirestore.getInstance()

    fun getLangCards(): LiveData<List<LangCard>> {
        val langCards : MutableLiveData<List<LangCard>> = MutableLiveData()
        db.collection("langCards").addSnapshotListener(EventListener<QuerySnapshot>{ value, e ->
            if (e != null) {
                langCards.value = null
                return@EventListener
            }
            val onlineLangCards : MutableList<LangCard> = mutableListOf()
            for (doc in value!!) {
                val addressItem = doc.toObject(LangCard::class.java)
                onlineLangCards.add(addressItem)
            }
            langCards.value = onlineLangCards
        })
        return langCards
    }
}