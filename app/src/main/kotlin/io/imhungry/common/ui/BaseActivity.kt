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

    protected var userUnauthenticatedHandler: (() -> Unit)? = null

    protected var loginFailureHandler = object : AuthFailureCallback {
        override fun invoke() = launchLoginActivity()
    }

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        when (theme()) {
            getString(R.string.night_theme) -> setTheme(R.style.NightTheme)
            else -> setTheme(R.style.AppTheme)
        }
    }

    override fun onResume() {
        super.onResume()
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

    fun theme(): String? {
        val key = getString(R.string.settings_app_theme)
        val default = getString(R.string.default_theme)
        return PreferenceManager.getDefaultSharedPreferences(this).getString(key, default)
    }
}