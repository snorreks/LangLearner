package io.sks.langlearner.android.ui.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.sks.langlearner.android.MainActivity
import io.sks.langlearner.android.R
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initViews()
        initViewModel()
    }
    private fun initViews() {
        etEmail.afterTextChanged {
            registerViewModel.loginDataChanged(
                etEmail.text.toString(),
                etPassword.text.toString()
                )
        }
        etPassword.apply {
            afterTextChanged {
                registerViewModel.loginDataChanged(
                    etEmail.text.toString(),
                    etPassword.text.toString()
                )
            }
        }
        btnSignUp.setOnClickListener {
            loading.visibility = View.VISIBLE
            registerViewModel.register(etEmail.text.toString(), etPassword.text.toString(),
                spinNativeLang.selectedItemPosition ,spinSelectedLang.selectedItemPosition
                )
        }
    }
    private fun initViewModel() {

        registerViewModel =
            ViewModelProviders.of(this).get(RegisterViewModel::class.java)

        registerViewModel.registerFormState.observe(this@RegisterActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            btnSignUp.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                etEmail.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                etPassword.error = getString(loginState.passwordError)
            }
        })

        registerViewModel.registerResult.observe(this@RegisterActivity, Observer {
            val loginResult = it ?: return@Observer
            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showRegisterFailed(loginResult.error)
            }
            if (loginResult.success) {
                setResult(Activity.RESULT_OK)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })
    }

    private fun showRegisterFailed(@StringRes errorString: Int) {
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