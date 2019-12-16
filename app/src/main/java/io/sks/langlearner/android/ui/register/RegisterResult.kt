package io.sks.langlearner.android.ui.register

/**
 * Authentication result : success (user details) or error message.
 */
data class RegisterResult(
    val success: Boolean = false,
    val error: Int? = null
)
