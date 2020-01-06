package io.sks.langlearner.android.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.sks.langlearner.android.R
import io.sks.langlearner.android.ui.main.MainActivity
import io.sks.langlearner.android.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initViews()
        initViewModel()
    }

    private fun initViews() {
        etEmail.afterTextChanged {
            loginViewModel.loginDataChanged(
                etEmail.text.toString(),
                etPassword.text.toString()
            )
        }
        etPassword.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    etPassword.text.toString(),
                    etPassword.text.toString()
                )
            }
            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        loading.visibility = View.VISIBLE
                        loginViewModel.login(
                            etEmail.text.toString(),
                            etPassword.text.toString()
                        )
                    }
                }
                false
            }
        }
        btnSignIn.setOnClickListener {
            loading.visibility = View.VISIBLE
            loginViewModel.login(etEmail.text.toString(), etPassword.text.toString())
        }
        btnSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initViewModel() {
        loginViewModel =
            ViewModelProviders.of(this).get(LoginViewModel::class.java)

        loginViewModel.registerFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            btnSignIn.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                etEmail.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                etPassword.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer
            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success) {
                setResult(Activity.RESULT_OK)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
