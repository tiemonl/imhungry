package io.imhungry.home.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.imhungry.R

class ResturantScrollerAdapter(private val resturants: ArrayList<String>) :
    RecyclerView.Adapter<ResturantScrollerAdapter.ResturantPreviewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class ResturantPreviewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResturantScrollerAdapter.ResturantPreviewHolder {
        // create a new view
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.resturant_name_card, parent, false) as TextView
        // set the view's size, margins, paddings and layout parameters

        return ResturantPreviewHolder(textView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ResturantPreviewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.text = resturants[position]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = resturants.size
}