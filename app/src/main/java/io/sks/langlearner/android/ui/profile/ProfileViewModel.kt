package io.sks.langlearner.android.ui.profile
import androidx.lifecycle.ViewModel
import io.sks.langlearner.android.services.FirebaseAuthService


class ProfileViewModel : ViewModel() {
    fun signOut() {
        FirebaseAuthService.signOut()
    }
}