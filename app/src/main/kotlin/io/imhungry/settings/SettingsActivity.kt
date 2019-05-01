package io.imhungry.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import io.imhungry.R
import io.imhungry.common.ui.AbstractFragmentActivity

class SettingsActivity : AbstractFragmentActivity() {

    private var preferenceResourceId: Int = R.xml.preferences

    override val fragment: Fragment
        get() = SettingsFragment(preferenceResourceId)

    override fun onCreate(savedInstanceState: Bundle?) {
        intent?.getIntExtra(EXTRA_PREFERENCE_ID, R.xml.preferences)?.let {
            preferenceResourceId = it
        }
        super.onCreate(savedInstanceState)
        title = getString(R.string.action_bar_settings_label)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
        if (android.R.id.home == item?.itemId) {
            onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }


    companion object {
        fun buildIntentFromPreferences(context: Context, resourceId: Int) =
            Intent(context, SettingsActivity::class.java).also {
                it.putExtra(EXTRA_PREFERENCE_ID, resourceId)
            }

        private const val EXTRA_PREFERENCE_ID = "preferenceId"
    }

}
