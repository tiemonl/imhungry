package io.imhungry.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import io.imhungry.R
import io.imhungry.notifications.NotificationHelper
import io.imhungry.notifications.NotificationPriority
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogout.setOnClickListener {
            auth.signOut()
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        NotificationHelper(this).sendNotificationNow("Welcome!", "We hope you're hungry!", null, NotificationPriority.DEFAULT, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.settingsGear -> {
            startActivity(Intent(this, SettingsActivity::class.java))
            true
        }
        else -> false
    }
}