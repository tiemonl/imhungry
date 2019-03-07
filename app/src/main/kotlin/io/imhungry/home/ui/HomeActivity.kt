package io.imhungry.home.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import io.imhungry.R
import io.imhungry.common.di.ViewModelFactory
import io.imhungry.common.ui.BaseActivity
import io.imhungry.common.ui.NavigationActivity
import io.imhungry.home.vm.HomeViewModel
import io.imhungry.login.launchLoginActivity
import io.imhungry.settings.SettingsActivity
import javax.inject.Inject

class HomeActivity : NavigationActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homeViewModel = ViewModelProviders.of(this, viewModelFactory)[HomeViewModel::class.java]
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