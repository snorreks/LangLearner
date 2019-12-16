package io.sks.langlearner.android.ui.register

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.sks.langlearner.android.R
import io.sks.langlearner.android.services.FirebaseAuthService
import io.sks.langlearner.android.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val defaultScope = CoroutineScope(Dispatchers.Main)

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

    fun register(username: String, password: String, nativeLangIndex: Int, selectedLangIndex: Int) {
        defaultScope.launch {
            val result = FirebaseAuthService.signUp(
                username,
                password,
                convertIndexToLocale(nativeLangIndex),
                convertIndexToLocale(selectedLangIndex)
            )
            if (result is Result.Success) {
                _registerResult.value =
                    RegisterResult(success = true)
            } else {
                _registerResult.value = RegisterResult(error = R.string.register_failed)
            }
        }
    }

    private fun convertIndexToLocale(index: Int): String {
        return when (index) {
            0 -> "en"
            1 -> "nb"
            2 -> "nl"
            else -> "en"
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _registerForm.value = RegisterFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
        } else {
            _registerForm.value = RegisterFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
