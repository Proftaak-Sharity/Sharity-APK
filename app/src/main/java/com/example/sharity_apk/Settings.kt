package com.example.sharity_apk

import android.os.Bundle
import android.widget.Toast
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import java.util.*

class Settings : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        // language
        val listPreference =
            findPreference<Preference>(getString(R.string.Language_settings)) as ListPreference?
        listPreference?.setOnPreferenceChangeListener { preference, newValue ->
            if (preference is ListPreference) {
                val index = preference.findIndexOfValue(newValue.toString())
                val language = preference.entryValues[index]
                if (language == "dutch") {
                    Toast.makeText(activity,language, Toast.LENGTH_LONG).show()
                    val lang = "nl"
                    val locale = Locale(lang)
                    Locale.setDefault(locale)

                if (language == "english") {
                    Toast.makeText(activity,language, Toast.LENGTH_LONG).show()
                    val lang = "en"
                    val locale = Locale(lang)
                    Locale.setDefault(locale)
                }

                }
            }
            true
        }
    }

}