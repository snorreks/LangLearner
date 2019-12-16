package io.sks.langlearner.android.util

/**
 * Authentication result : success (user details) or error message.
 */
data class ActionResult(
    val success: Boolean = false,
    val error: Int? = null
)
