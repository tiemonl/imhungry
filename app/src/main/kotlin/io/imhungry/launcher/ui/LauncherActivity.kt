package io.imhungry.launcher.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import io.imhungry.R
import io.imhungry.login.ui.LoginActivity
import io.imhungry.home.ui.HomeActivity

class LauncherActivity : AppCompatActivity() {

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        routeAuth()
        finish()
    }

    private val isLoggedIn
        get() = firebaseAuth.currentUser != null

    private fun routeToMain() = startActivity(Intent(this, HomeActivity::class.java))

    private fun routeToLogin() = startActivity(Intent(this, LoginActivity::class.java))

    private fun routeAuth() {
        if (isLoggedIn) {
            routeToMain()
        } else {
            routeToLogin()
        }
    }
}