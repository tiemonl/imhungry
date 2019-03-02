package io.imhungry.home.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import io.imhungry.R
import io.imhungry.common.di.ViewModelFactory
import io.imhungry.common.ui.BaseActivity
import io.imhungry.home.vm.HomeViewModel
import io.imhungry.maps.ui.MapActivity
import io.imhungry.notifications.NotificationHelper
import io.imhungry.notifications.NotificationPriority
import io.imhungry.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class HomeActivity : BaseActivity() {

    private val auth by lazy { FirebaseAuth.getInstance() }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var notificationHelper: NotificationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homeViewModel = ViewModelProviders.of(this, viewModelFactory)[HomeViewModel::class.java]

        logoutButton.setOnClickListener {
            auth.signOut()
        }

        val bottomNavigation = findViewById<View>(R.id.navigationView) as BottomNavigationView
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId){
                R.id.navigation_home ->
                    startActivity(Intent(this, HomeActivity::class.java))
                R.id.navigation_Map ->
                    startActivity(Intent(this, MapActivity::class.java))
                R.id.navigation_more ->
                    Toast.makeText(application, "more clicked", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }

    override fun onStart() {
        super.onStart()
        notificationHelper.sendNotificationNow(
            "Welcome!",
            "We hope you're hungry!",
            null,
            NotificationPriority.DEFAULT,
            0
        )
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