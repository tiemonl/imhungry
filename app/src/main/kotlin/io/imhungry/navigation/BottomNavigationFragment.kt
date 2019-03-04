package io.imhungry.navigation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.imhungry.R
import io.imhungry.home.ui.HomeActivity
import io.imhungry.maps.ui.MapActivity


class BottomNavigationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_bottom_navigation, container, false) as View

        val bottomNavigation: BottomNavigationView = v.findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId){
                R.id.navigation_home ->
                    activity?.let {
                        val intent = Intent(it, HomeActivity::class.java)
                        it.startActivity(intent)
                    }
                R.id.navigation_Map ->
                    activity?.let {
                        val intent = Intent(it, MapActivity::class.java)
                        it.startActivity(intent)
                    }
                R.id.navigation_more ->
                    Toast.makeText(this.context, "more clicked", Toast.LENGTH_SHORT).show()
            }
            true
        }

        return v;

    }
}
