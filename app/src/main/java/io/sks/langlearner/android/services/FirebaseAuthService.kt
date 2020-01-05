package io.sks.langlearner.android.services

import android.content.res.Resources
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import io.sks.langlearner.android.model.User
import io.sks.langlearner.android.util.Result
import kotlinx.coroutines.tasks.await


object FirebaseAuthService {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    var currentUser: User? = null

    suspend fun getUserData(): Result<User>{
        return when(val uid = auth.currentUser?.uid){
            null -> Result.Error(Resources.NotFoundException())
            else -> return try {
                val result = db.collection("users").document(uid).get().await()
                val user = result.toObject(User::class.java)!!
                currentUser = user
                Result.Success(user)
            } catch (e: FirebaseException) {
                Result.Error(e)
            }
        }
    }

    suspend fun signUp(email: String, password: String, nativeLang: String, selectedLang: String ): Result<Boolean>{
        return try {
           val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user!!
            db.collection("users").document(user.uid).set(
                    mapOf(
                        "createdAt" to FieldValue.serverTimestamp(),
                        "email" to user.email,
                        "nativeLang" to nativeLang,
                        "selectedLang" to selectedLang
                )
            ).await()
            getUserData()
            Result.Success(true)
        } catch (e: FirebaseException) {
            auth.currentUser?.delete()?.await()
             Result.Error(e)
        }
    }

    suspend fun signIn(email: String, password: String): Result<Boolean>{
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            getUserData()
            Result.Success(true)
        } catch (e: FirebaseException) {
             Result.Error(e)
        }
    }

    suspend fun updateLocales(nativeLocale: String, selectedLocale: String): Result<Boolean> {
        return try {
            db.collection("users").document(auth.currentUser!!.uid).update(
                mapOf(
                    "nativeLang" to nativeLocale,
                    "selectedLang" to selectedLocale,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            ).await()
            currentUser!!.nativeLocale = nativeLocale
            currentUser!!.selectedLocale = selectedLocale
            Result.Success(true)
        } catch (e: FirebaseException) {
            Result.Error(e)
        }
    }

     fun signOut(){
        auth.signOut()
    }
}

