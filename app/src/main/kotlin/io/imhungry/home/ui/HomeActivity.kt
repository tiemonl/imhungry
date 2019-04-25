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
import io.imhungry.home.ui.adapters.RestaurantHomeCardProvider
import io.imhungry.home.vm.HomeViewModel
import io.imhungry.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import kotlin.math.round
import kotlin.random.Random

class HomeActivity : NavigationActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var homeViewModel: HomeViewModel

    val rand = Random(hashCode())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homeViewModel = ViewModelProviders.of(this, viewModelFactory)[HomeViewModel::class.java]

        cardList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val r = ArrayList<ArrayList<String>>()

        var places = ArrayList<String>(
            listOf(
                "Wendy's",
                "Arby's",
                "Outback",
                "McDonalds's",
                "Cane's",
                "Hot Tin Roof",
                "Pizza Hut"
            )
        )

        val groups = ArrayList<String>(
            listOf(
                "Nearby",
                "Good Ratings",
                "Sit Down",
                "Recently Viewed",
                "Visited by friends",
                "Fast",
                "Drinks"
            )
        )

        val adapter = HomeCardAdapter(this.applicationContext)
        cardList.adapter = adapter

        for (group_name in groups) {
            val p = ArrayList<RestaurantHomeCardProvider>()

            for (place in places)
                p.add(DummyCard(place, rand.nextDouble(10.0), rand.nextDouble(5.0) + 1))

            p.shuffle()

            adapter.addList(group_name, p)
        }
    }

    class DummyCard(private val name: String, private val dist: Double, private val rating: Double) :
        RestaurantHomeCardProvider {
        override fun getName(): String {
            return name
        }

        override fun getDisplayDistanceFromCurrentLocation(): String {
            return (round(dist * 100) / 100).toString() + " miles"
        }

        override fun getRating(numStars: Int): Float {
            return rating.toFloat()
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
}