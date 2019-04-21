package io.imhungry.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import io.imhungry.R

class SettingsDetails : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        //var fragment = getFragmentManager()?.findFragmentByTag("Network")
        val fragment = fragmentManager

        when {
            fragment?.findFragmentByTag(getString(R.string.settings_category_restaurant_filters_key)) != null -> setPreferencesFromResource(R.xml.preferences_restaurant_filters, rootKey)
            fragment?.findFragmentByTag(getString(R.string.settings_category_notifications_key)) != null -> setPreferencesFromResource(R.xml.preferences_notifications, rootKey)
            fragment?.findFragmentByTag(getString(R.string.settings_category_general_key)) != null -> setPreferencesFromResource(R.xml.preferences_general, rootKey)
            fragment?.findFragmentByTag(getString(R.string.settings_category_security_key)) != null -> setPreferencesFromResource(R.xml.preferences_security_and_location, rootKey)
            fragment?.findFragmentByTag(getString(R.string.settings_category_account_key)) != null -> setPreferencesFromResource(R.xml.preferences_account, rootKey)
        }

    }




}