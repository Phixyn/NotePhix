package com.phixyn.notephix

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import java.util.ArrayList

class TaskRecyclerAdapter(private val mContext: Context, private val mTasks: ArrayList<String>) :
    RecyclerView.Adapter<TaskRecyclerAdapter.ViewHolder>() {

    // LayoutInflater is used to create views from a layout resource
    // It "inflates" layout resources into view hierarchies
    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(mContext)

    /**
     * Inflates our task item view and creates a new ViewHolder instance, passing it
     * an instance of the inflated view. Returns the ViewHolder so it can go to the
     * pool that the RecyclerView uses.
     *
     * Takes care of inflating the view and storing information about the view in
     * our ViewHolder.
     * In other words, inflates the View and associates it with a ViewHolder.
     *
     * @return A new instance of our ViewHolder (inner) class, passing in a View.
     * The RecyclerView will use this to start creating the pool of views.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create (inflate) the task item view
        /* Pass the layout resource we want to inflate
         *  (in this case, the item_task_list with the FrameLayout containing CardView)
         * false = don't automatically attach inflated view to its parent
         *  (we do this through the adapter) */
        val itemView = mLayoutInflater.inflate(
            R.layout.item_task_list,
            parent,
            false
        )
        // Return the task item view (CardView inside the FrameLayout)
        return ViewHolder(itemView)
    }

    /**
     * Binds our data to our views.
     *
     * Takes care of associating data for a desired position within a ViewHolder.
     *
     * @param holder An instance of our ViewHolder (inner) class.
     * @param position The position of the data item we need to display.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data from our ArrayList...
        val task = mTasks[position]
        // ...and display it in the view holder's TextView.
        holder.mTaskTextView.text = task
    }

    /**
     * Returns the amount of data for the adapter.
     *
     * @return Number of items in our ArrayList.
     */
    override fun getItemCount(): Int = mTasks.size

    /**
     * ViewHolder class for our RecyclerView. Receives a View.
     * Contains references to the Views inside of it.
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mTaskTextView: TextView = itemView.findViewById(R.id.view_task_card_text)

        init {
            // Set a click listener for the whole CardView
            /*
             * Deletes a task when its CardView is tapped on.
             * Removes the corresponding task from the ArrayList
             * and writes the updated task list to the file.
             *
             * @param view The instance of the CardView that was tapped on.
             */
            itemView.setOnClickListener {
                mTasks.removeAt(adapterPosition)
                notifyDataSetChanged()
                // Write updated data set to file
                FileHelper.writeData(mTasks, mContext)

                Toast.makeText(
                    mContext,
                    "Task deleted",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
