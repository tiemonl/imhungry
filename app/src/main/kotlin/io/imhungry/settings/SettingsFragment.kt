package io.imhungry.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.google.firebase.auth.FirebaseAuth
import io.imhungry.R

class SettingsFragment : PreferenceFragmentCompat() {

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        findPreference(getString(R.string.settings_logout_key))?.let {
            it.summary = getString(R.string.settings_logout_summary_template, firebaseAuth.currentUser?.run { displayName ?: email })
            it.setOnPreferenceClickListener {
                firebaseAuth.signOut()
                activity?.finish()
                true
            }
        }
    }
}