package io.sks.langlearner.android

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.sks.langlearner.android.services.FirebaseAuthService.getUserData
import io.sks.langlearner.android.ui.login.LoginActivity
import io.sks.langlearner.android.ui.main.MainActivity
import io.sks.langlearner.android.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainEmptyActivity : AppCompatActivity() {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAuth()
    }

    private fun checkAuth(){
        ioScope.launch {
            val activityIntent: Intent = if (getUserData() is Result.Success) {
                    Intent(applicationContext, MainActivity::class.java)
                } else {
                    Intent(applicationContext, LoginActivity::class.java)
                }
            startActivity(activityIntent)
            finish()
        }
    }
}