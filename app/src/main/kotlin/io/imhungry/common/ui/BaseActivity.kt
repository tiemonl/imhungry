package io.imhungry.common.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import dagger.android.AndroidInjection
import io.imhungry.R
import io.imhungry.login.AuthFailureCallback
import io.imhungry.login.handleLoginActivityResult
import io.imhungry.login.launchLoginActivity

abstract class BaseActivity : AppCompatActivity(), FirebaseAuth.AuthStateListener {

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val currentAppTheme by lazy(::getAppThemeFromPreferences)

    protected var userUnauthenticatedHandler: (() -> Unit)? = null

    protected var loginFailureHandler = object : AuthFailureCallback {
        override fun invoke() = launchLoginActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setTheme(currentAppTheme)
    }

    override fun onResume() {
        super.onResume()
        if (getAppThemeFromPreferences() != currentAppTheme) {
            startActivity(intent)
            finish()
        }
        firebaseAuth.addAuthStateListener(this)
    }

    override fun onPause() {
        super.onPause()
        firebaseAuth.removeAuthStateListener(this)
    }

    override fun onAuthStateChanged(auth: FirebaseAuth) {
        if (auth.currentUser == null) {
            userUnauthenticatedHandler?.invoke()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        handleLoginActivityResult(requestCode, resultCode, loginFailureHandler)
    }

    private fun getAppThemeFromPreferences(): Int {
        val preferenceThemeKey = getString(R.string.settings_app_theme)
        val defaultTheme = getString(R.string.default_theme)
        return when (PreferenceManager.getDefaultSharedPreferences(this).getString(preferenceThemeKey, defaultTheme)) {
            getString(R.string.night_theme) -> R.style.NightTheme
            else -> R.style.AppTheme
        }
    }
}