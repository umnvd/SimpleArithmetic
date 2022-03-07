package com.demo.simplearithmetic.presentation

import android.os.Bundle
import android.util.Log
import androidx.preference.MultiSelectListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SeekBarPreference
import com.demo.simplearithmetic.R
import com.demo.simplearithmetic.di.AppComponent.Companion.MAX_VALUE_KEY
import com.demo.simplearithmetic.di.AppComponent.Companion.OPERATIONS_KEY
import com.demo.simplearithmetic.extensions.setSummaryFromSelection
import kotlin.math.roundToInt

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val operationsPref = findPreference<MultiSelectListPreference>(OPERATIONS_KEY)
        operationsPref?.setSummaryFromSelection(operationsPref.values)

        operationsPref?.setOnPreferenceChangeListener { preference, newValue ->
            try {
                preference as MultiSelectListPreference
                newValue as Set<String>
                if (newValue.isEmpty()) {
                    false
                } else {
                    preference.setSummaryFromSelection(newValue)
                    true
                }
            } catch (e: TypeCastException) {
                Log.e("SettingsFragment", e.message.toString(), e)
                false
            }
        }

        val maxValuePref = findPreference<SeekBarPreference>(MAX_VALUE_KEY)

        maxValuePref?.setOnPreferenceChangeListener { preference, newValue ->
            try {
                preference as SeekBarPreference
                newValue as Int
                val discreteValue = (newValue.toFloat() / 100).roundToInt() * 100
                preference.value = discreteValue
            } catch (e: TypeCastException) {
                Log.e("SettingsFragment", e.message.toString(), e)
            }
            false
        }


    }
}

