package io.sks.langlearner.android.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class LangCard(
    var id: String = "",
    var type: String = "",
    var text: HashMap<String,Any> = hashMapOf(),
    var languages: List<String> = listOf(),
    var thumbnailUrl: String= "",
    @ServerTimestamp var createdAt: Date = Date(),
    @ServerTimestamp var updatedAt: Date? = null
)