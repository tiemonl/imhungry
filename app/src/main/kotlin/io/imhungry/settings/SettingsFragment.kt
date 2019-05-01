package io.imhungry.settings

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.firebase.auth.FirebaseAuth
import io.imhungry.R
import io.imhungry.login.launchLoginActivity

class SettingsFragment(private val preferenceResourceId: Int) : PreferenceFragmentCompat() {

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(preferenceResourceId, rootKey)

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

        findPreference(getString(R.string.settings_app_theme))?.let {
            it.setOnPreferenceChangeListener { _, _ ->
                activity?.finish()
                activity?.startActivity(activity?.intent)
                true
            }
        }
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        when (preference.key) {

            getString(R.string.settings_category_restaurant_filters_key) -> launchPreferenceActivity(R.xml.preferences_restaurant_filters)
            getString(R.string.settings_category_notifications_key) -> launchPreferenceActivity(R.xml.preferences_notifications)
            getString(R.string.settings_category_general_key) -> launchPreferenceActivity(R.xml.preferences_general)
            getString(R.string.settings_category_security_key) -> launchPreferenceActivity(R.xml.preferences_security_and_location)
            getString(R.string.settings_category_account_key) -> launchPreferenceActivity(R.xml.preferences_account)

            else -> {
                super.onPreferenceTreeClick(preference)
                return false
            }
        }
        return true
    }

    private fun launchPreferenceActivity(resourceId: Int) = activity?.let {
        it.startActivity(SettingsActivity.buildIntentFromPreferences(it, resourceId))
    }
}