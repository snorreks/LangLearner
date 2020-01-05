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
import io.sks.langlearner.android.R
import io.sks.langlearner.android.ui.main.MainActivity
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
            registerViewModel.registerDataChanged(
                etEmail.text.toString(),
                etPassword.text.toString()
            )
        }
        etPassword.apply {
            afterTextChanged {
                registerViewModel.registerDataChanged(
                    etEmail.text.toString(),
                    etPassword.text.toString()
                )
            }
        }
        btnSignUp.setOnClickListener {
            loading.visibility = View.VISIBLE
            registerViewModel.register(
                etEmail.text.toString(), etPassword.text.toString(),
                spinNativeLang.selectedItemPosition, spinSelectedLang.selectedItemPosition
            )
        }

        btnCancel.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }


    }

    private fun initViewModel() {

        registerViewModel =
            ViewModelProviders.of(this).get(RegisterViewModel::class.java)

        registerViewModel.registerFormState.observe(this@RegisterActivity, Observer {
            val registerState = it ?: return@Observer

            // disable login button unless both username / password is valid
            btnSignUp.isEnabled = registerState.isDataValid

            if (registerState.emailError != null) {
                etEmail.error = getString(registerState.emailError)
            }
            if (registerState.passwordError != null) {
                etPassword.error = getString(registerState.passwordError)
            }
        })

        registerViewModel.registerResult.observe(this@RegisterActivity, Observer {
            val registerResult = it ?: return@Observer
            loading.visibility = View.GONE
            if (registerResult.error != null) {
                showRegisterFailed(registerResult.error)
            }
            if (registerResult.success) {
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