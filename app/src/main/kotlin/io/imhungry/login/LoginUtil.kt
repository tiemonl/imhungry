package io.imhungry.login

import android.app.Activity
import android.content.Intent
import com.firebase.ui.auth.AuthUI
import io.imhungry.R
import io.imhungry.home.ui.HomeActivity
import io.imhungry.login.LoginConstants.RC_SIGN_IN
import io.imhungry.notifications.NotificationHelper
import io.imhungry.notifications.NotificationPriority

internal object LoginConstants {
    val AUTH_PROVIDERS = listOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build()
    )
    const val RC_SIGN_IN = 889
}

fun Activity.launchLoginActivity() {
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

fun Activity.handleLoginActivityResult(
    requestCode: Int,
    resultCode: Int,
    failureCallback: AuthFailureCallback? = null
) {
    when (requestCode) {
        RC_SIGN_IN -> {
            if (resultCode == Activity.RESULT_OK) {
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                if (failureCallback != null) {
                    failureCallback()
                }
            }
        }
    }
}