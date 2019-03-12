package io.imhungry.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.google.firebase.auth.FirebaseAuth
import io.imhungry.R
import io.imhungry.login.launchLoginActivity

class SettingsFragment : PreferenceFragmentCompat() {

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        findPreference(getString(R.string.settings_user_state))?.let {
            val user = firebaseAuth.currentUser
            if (user != null) {
                it.title = getString(R.string.settings_title_logout)
                it.summary =
                    getString(R.string.settings_summary_template_loggedin, user.displayName ?: user.email)
                it.setOnPreferenceClickListener {
                    firebaseAuth.signOut()
                    activity?.finish()
                    true
                }
            } else {
                it.title = getString(R.string.settings_title_login)
                it.summary = getString(R.string.settings_summary_loggedout)
                it.setOnPreferenceClickListener {
                    activity?.launchLoginActivity()
                    true
                }
            }
        }
    }
}