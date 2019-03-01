package io.imhungry.launcher.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import io.imhungry.R
import io.imhungry.home.ui.HomeActivity
import io.imhungry.login.handleLoginActivityResult
import io.imhungry.login.launchLoginActivity

class LauncherActivity : AppCompatActivity() {

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        routeAuth()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        handleLoginActivityResult(requestCode, resultCode) {
            Toast.makeText(this, "Failed to Log In", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    private val isLoggedIn
        get() = firebaseAuth.currentUser != null

    private fun routeToMain() = startActivity(Intent(this, HomeActivity::class.java))

    private fun launchLogin() = launchLoginActivity()

    private fun routeAuth() {
        if (isLoggedIn) {
            routeToMain()
            finish()
        } else {
            launchLogin()
        }
    }
}