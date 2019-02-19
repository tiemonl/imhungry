package io.imhungry.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

abstract class BaseActivity: AppCompatActivity(), FirebaseAuth.AuthStateListener {

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

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
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}