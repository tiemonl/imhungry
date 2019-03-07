package io.imhungry.common.ui

import android.content.Intent
import android.os.Bundle
import io.imhungry.login.LoginInteractorActivity

/**
 * A class all activities included in the bottom navigation should extend
 */
abstract class NavigationActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.userUnauthenticatedHandler = {
            startActivity(Intent(this, LoginInteractorActivity::class.java))
            finish()
        }
    }
}