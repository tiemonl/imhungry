package io.imhungry.home.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.imhungry.R
import io.imhungry.common.di.ViewModelFactory
import io.imhungry.common.ui.NavigationActivity
import io.imhungry.home.ui.adapters.HomeCardAdapter
import io.imhungry.home.vm.HomeViewModel
import io.imhungry.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class HomeActivity : NavigationActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homeViewModel = ViewModelProviders.of(this, viewModelFactory)[HomeViewModel::class.java]

        cardList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val r = ArrayList<ArrayList<String>>()
        cardList.adapter = HomeCardAdapter(r, this.applicationContext)
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