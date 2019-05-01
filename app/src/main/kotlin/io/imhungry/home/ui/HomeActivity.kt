package io.imhungry.home.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
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
    private val homeViewModel by lazy { ViewModelProviders.of(this, viewModelFactory)[HomeViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val adapter = HomeCardAdapter(this.applicationContext)
        cardList.adapter = adapter

        homeViewModel.nearbyLiveData.observe(this, Observer {
            adapter.addList("Nearby", it)
        })

        homeViewModel.highestRatingsLiveData.observe(this, Observer {
            adapter.addList("Good Ratings", it)

            adapter.addList("Sit Down", it.shuffled())
            adapter.addList("Recently Viewed", it.shuffled())
            adapter.addList("Visited by friends", it.shuffled())
            adapter.addList("Fast", it.shuffled())
            adapter.addList("Drinks", it.shuffled())
        })
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