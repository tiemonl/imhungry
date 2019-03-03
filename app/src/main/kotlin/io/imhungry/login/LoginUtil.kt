package io.imhungry.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import io.imhungry.R
import io.imhungry.home.ui.HomeActivity
import io.imhungry.login.LoginConstants.RC_SIGN_IN
import io.imhungry.notifications.NotificationHelper
import io.imhungry.notifications.NotificationPriority

private object LoginConstants {
    val AUTH_PROVIDERS = listOf(
        AuthUI.IdpConfig.EmailBuilder().build()
    )
    const val RC_SIGN_IN = 889
}

fun AppCompatActivity.launchLoginActivity() {
    startActivityForResult(
        AuthUI.getInstance().createSignInIntentBuilder()
            .setAvailableProviders(
            LoginConstants.AUTH_PROVIDERS
        )
            .setLogo(R.drawable.ic_imhungry_logo_outlined)
            .setTheme(R.style.AppTheme)
            .setIsSmartLockEnabled(false, false)
            .build(),
        LoginConstants.RC_SIGN_IN
    )
}

fun AppCompatActivity.handleLoginActivityResult(requestCode: Int, resultCode: Int, failureCallback: (() -> Unit)? = null) {
    when (requestCode) {
        RC_SIGN_IN -> {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                startActivity(Intent(this, HomeActivity::class.java))
                NotificationHelper(this).sendNotificationNow(
                    "Welcome!",
                    "We hope you're hungry!",
                    null,
                    NotificationPriority.DEFAULT,
                    0
                )
            } else {
                if (failureCallback != null) {
                    failureCallback()
                }
            }
        }
    }
}