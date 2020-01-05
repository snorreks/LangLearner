package io.sks.langlearner.android.ui.settings

import androidx.lifecycle.ViewModel
import io.sks.langlearner.android.services.FirebaseAuthService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {

    private val defaultScope = CoroutineScope(Dispatchers.Main)


    fun update(nativeLangIndex: Int, selectedLangIndex: Int) {
        defaultScope.launch {
            FirebaseAuthService.updateLocales(
                convertIndexToLocale(nativeLangIndex),
                convertIndexToLocale(selectedLangIndex)
            )
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

}