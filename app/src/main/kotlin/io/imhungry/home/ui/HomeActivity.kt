package io.imhungry.home.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import io.imhungry.R
import io.imhungry.common.di.ViewModelFactory
import io.imhungry.common.ui.BaseActivity
import io.imhungry.home.vm.HomeViewModel
import io.imhungry.login.handleLoginActivityResult
import io.imhungry.notifications.NotificationHelper
import io.imhungry.notifications.NotificationPriority
import io.imhungry.maps.ui.MapActivity
import io.imhungry.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class HomeActivity : BaseActivity() {

    private val auth by lazy { FirebaseAuth.getInstance() }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homeViewModel = ViewModelProviders.of(this, viewModelFactory)[HomeViewModel::class.java]

        logoutButton.setOnClickListener {
            auth.signOut()
        }

        if (isFirst) {
            NotificationHelper(this).sendNotificationNow(
                "Welcome!",
                "We hope you're hungry!",
                null,
                NotificationPriority.DEFAULT,
                0
            )
            isFirst = false
        }
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

    companion object {
        private var isFirst = true
    }
}