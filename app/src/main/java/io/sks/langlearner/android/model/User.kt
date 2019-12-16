package io.sks.langlearner.android.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class User(
    var userName: String = "",
    var email: String = "",
    var nativeLocale: String = "en",
    var selectedLocale: String = "nb",
    @ServerTimestamp var createdAt: Date = Date()
){
    fun getNativeText(texts: HashMap<String,Any>): String{
        return texts[nativeLocale].toString()
    }
    fun getSelectedText(texts: HashMap<String,Any>): String{
        return texts[selectedLocale].toString()
    }
}