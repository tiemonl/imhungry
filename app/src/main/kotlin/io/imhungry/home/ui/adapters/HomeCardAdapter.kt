package io.imhungry.home.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.imhungry.R
import java.util.*

class HomeCardAdapter(private val resturants: ArrayList<ArrayList<String>>, private val context: Context) :
    RecyclerView.Adapter<HomeCardAdapter.HomeViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class HomeViewHolder(val recycler: RecyclerView) : RecyclerView.ViewHolder(recycler)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeCardAdapter.HomeViewHolder {
        // create a new view
        val recycler = LayoutInflater.from(parent.context)
            .inflate(R.layout.resturant_list, parent, false) as RecyclerView
        // set the view's size, margins, paddings and layout parameters

        return HomeViewHolder(recycler)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.recycler.adapter = ResturantScrollerAdapter(resturants[position])
        holder.recycler.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = resturants.size
}