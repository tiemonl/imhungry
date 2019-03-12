package io.imhungry.common.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import dagger.android.AndroidInjection
import io.imhungry.login.AuthFailureCallback
import io.imhungry.login.handleLoginActivityResult

abstract class BaseActivity : AppCompatActivity(), FirebaseAuth.AuthStateListener {

    protected var loginFailureCallback = object : AuthFailureCallback {
        override fun invoke() = launchLoginActivity()
    }
  
    protected var userUnauthenticatedHandler: (() -> Unit)? = null

    protected var loginFailureHandler: (() -> Unit)? = null

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
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
}