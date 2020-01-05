package io.sks.langlearner.android.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import io.sks.langlearner.android.R
import io.sks.langlearner.android.services.FirebaseAuthService
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViewModel()
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val currentUser = FirebaseAuthService.currentUser!!
        spinSelectedLang.setSelection(convertLocaleToIndex(currentUser.selectedLocale))
        spinNativeLang.setSelection(convertLocaleToIndex(currentUser.nativeLocale))
        btnUpdate.setOnClickListener {
            settingsViewModel.update(
                spinNativeLang.selectedItemPosition, spinSelectedLang.selectedItemPosition
            )
        }
    }

    private fun convertLocaleToIndex(locale: String): Int {
        return when (locale) {
            "en" -> 0
            "nb" -> 1
            "nl" -> 2
            else -> 0
        }
    }

    private fun initViewModel() {
        settingsViewModel =
            ViewModelProviders.of(this).get(SettingsViewModel::class.java)
    }
}