package com.phixyn.notephix;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskRecyclerAdapter.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final ArrayList<String> mTasks;

    public TaskRecyclerAdapter(Context context, ArrayList<String> tasks) {
        mContext = context;
        // LayoutInflater is used to create views from a layout resource
        // It "inflates" layout resources into view hierarchies
        mLayoutInflater = LayoutInflater.from(mContext);
        mTasks = tasks;
    }

    /**
     * Inflates our task item view and creates a new ViewHolder instance, passing it
     * an instance of the inflated view. Returns the ViewHolder so it can go to the
     * pool that the RecyclerView uses.
     *
     * Takes care of inflating the view and storing information about the view in our ViewHolder.
     *
     * @return A new instance of our ViewHolder (inner) class, passing in a View.
     *         The RecyclerView will use this to start creating the pool of views.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create (inflate) the task item view
        // Pass the layout resource we want to inflate
        //  (in this case, the item_task_list with the FrameLayout containing CardView)
        // false = don't automatically attach inflated view to its parent
        //  (we do this through the adapter)
        View itemView = mLayoutInflater.inflate(R.layout.item_task_list, parent, false);
        // Return the task item view (CardView inside the FrameLayout)
        return new ViewHolder(itemView);
    }

    /**
     * Binds our data to our views.
     *
     * Takes care of associating data for a desired position within a ViewHolder.
     *
     * @param holder An instance of our ViewHolder (inner) class.
     * @param position The position of the data item we need to display.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data from our ArrayList and display it in the view holder's TextView
        String task = mTasks.get(position);
        holder.mTaskTextView.setText(task);
    }

    /**
     * Returns the amount of data for the adapter.
     *
     * @return Number of items in our ArrayList.
     */
    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    /**
     * ViewHolder class for our RecyclerView.
     * Receives a View.
     * Contains references to the (Text) Views inside of it.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView mTaskTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTaskTextView = itemView.findViewById(R.id.view_task_card_text);

            // Set a click listener for the whole CardView
            itemView.setOnClickListener(new View.OnClickListener() {
                /**
                 * Deletes a task when its CardView is tapped on.
                 * Removes the corresponding task from the ArrayList an writes the updated
                 * task list to the file.
                 *
                 * @param view The instance of the CardView that was tapped on.
                 */
                @Override
                public void onClick(View view) {
                    mTasks.remove(getAdapterPosition());
                    notifyDataSetChanged();
                    // Write updated data set to file
                    FileHelper.writeData(mTasks, mContext);

                    Toast.makeText(mContext, "Task deleted", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
