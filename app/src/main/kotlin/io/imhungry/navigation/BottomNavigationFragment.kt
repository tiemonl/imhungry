package io.imhungry.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.imhungry.R
import io.imhungry.calendar.ui.CalendarActivity
import io.imhungry.home.ui.HomeActivity
import io.imhungry.maps.ui.MapActivity
import io.imhungry.restaurantLists.ui.RestaurantListsActivtiy
import kotlinx.android.synthetic.main.fragment_bottom_navigation.*
import kotlin.reflect.KClass

class BottomNavigationFragment : Fragment(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val activityClass: KClass<out FragmentActivity>?
        get() = activity?.let { it::class }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_bottom_navigation, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navigationView.setOnNavigationItemSelectedListener(this)

        val currentMenuId = findCurrentMenuId()
        if (currentMenuId != null) {
            val currentMenuItem = navigationView.menu.findItem(currentMenuId)
            currentMenuItem?.isChecked = true
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val navDestination = findDestination(menuItem)

        if (navDestination != null) {
            navigateToActivity(navDestination)
        } else {
            onMoreClicked()
        }

        return true
    }

    private fun <T : AppCompatActivity> navigateToActivity(klass: KClass<out T>) {
        if (klass != activityClass) {
            activity?.let {
                val intent = Intent(it, klass.java)
                startActivity(intent)
            }
        }
    }

    private fun onMoreClicked() = Toast.makeText(this.context, "more clicked", Toast.LENGTH_SHORT).show()

    private fun findCurrentMenuId() = activityClass?.let { klass ->
        NAVIGATION_DESTINATION_MAP.find { klass == it.second }?.first
    }

    private fun findDestination(menuItem: MenuItem) =
        NAVIGATION_DESTINATION_MAP.find { it.first == menuItem.itemId }?.second

    companion object {
        private val NAVIGATION_DESTINATION_MAP = listOf(
            R.id.navigation_home to HomeActivity::class,
            R.id.navigation_Map to MapActivity::class,
            R.id.navigation_calendar to CalendarActivity::class,
            R.id.navigation_restaurant_lists to RestaurantListsActivtiy::class,
            R.id.navigation_more to null
        )
    }
}
