package io.imhungry.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.imhungry.R
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        emailSignInButton.setOnClickListener(this)
        emailCreateAccountButton.setOnClickListener(this)
        signOutButton.setOnClickListener(this)
        verifyEmailButton.setOnClickListener(this)
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.emailCreateAccountButton -> createAccount(fieldEmail.text.toString(), fieldPassword.text.toString())
            R.id.emailSignInButton -> signIn(fieldEmail.text.toString(), fieldPassword.text.toString())
            R.id.signOutButton -> signOut()
            R.id.verifyEmailButton -> sendEmailVerification()
        }
    }

    private fun createAccount(email: String, password: String) {
        Log.d(TAG, "createAccount:$email")
        if (!validateForm()) {
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }

            }
    }

    private fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn:$email")
        if (!validateForm()) {
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }

                if (!task.isSuccessful) {
                    status.setText(R.string.auth_failed)
                }
            }
    }

    private fun signOut() {
        auth.signOut()
        updateUI(null)
    }

    private fun sendEmailVerification() {
        verifyEmailButton.isEnabled = false

        val user = auth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this) { task ->
                verifyEmailButton.isEnabled = true

                if (task.isSuccessful) {
                    Toast.makeText(
                        baseContext,
                        "Verification email sent to ${user.email} ",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Failed to send verification email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = fieldEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            fieldEmail.error = "Required."
            valid = false
        } else {
            fieldEmail.error = null
        }

        val password = fieldPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            fieldPassword.error = "Required."
            valid = false
        } else {
            fieldPassword.error = null
        }

        return valid
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            if (user.isEmailVerified) {
                routeToMain()
                finish()
            } else {
                status.text = getString(
                    R.string.emailpassword_status_fmt,
                    user.email, user.isEmailVerified
                )
                detail.text = getString(R.string.firebase_status_fmt, user.uid)

                emailPasswordButtons.visibility = View.GONE
                emailPasswordFields.visibility = View.GONE
                signedInButtons.visibility = View.VISIBLE

                verifyEmailButton.isEnabled = !user.isEmailVerified
            }
        } else {
            status.setText(R.string.signed_out)
            detail.text = null

            emailPasswordButtons.visibility = View.VISIBLE
            emailPasswordFields.visibility = View.VISIBLE
            signedInButtons.visibility = View.GONE
        }
    }

    private fun routeToMain() = startActivity(Intent(this, MainActivity::class.java))

    companion object {
        private const val TAG = "EmailPassword"
    }

}