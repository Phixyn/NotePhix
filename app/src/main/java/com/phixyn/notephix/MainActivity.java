package com.phixyn.notephix;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText mNewTaskEditText;
    private ArrayList<String> mTasksArrayList;
    private TaskRecyclerAdapter mTasksRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        // Set up FloatingActionButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        // Set up EditText for typing a new task
        mNewTaskEditText = findViewById(R.id.item_edit_text);
        mNewTaskEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                // Hide soft keyboard if EditText loses focus
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE
                    );
                    if (imm != null)
                        imm.hideSoftInputFromWindow(
                                mNewTaskEditText.getWindowToken(),
                                0);
                }
            }
        });

        // Set up "Add task" button
        final Button addTaskButton = findViewById(R.id.add_btn);
        // Register an event listener for onClick to the Button
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String taskEntered = mNewTaskEditText.getText().toString();
                // Ensure that the string entered is not empty or all spaces
                if (!taskEntered.isEmpty() && !taskEntered.trim().isEmpty()) {
                    // TODO: Not sure if this is the best way to do things
                    // Should we add via the adapter directly, like we did previously
                    // with the ArrayAdapter? Or is this fine?
                    // tasksAdapter.add(taskEntered);
                    mTasksArrayList.add(taskEntered);
                    mTasksRecyclerAdapter.notifyDataSetChanged();
                    // Write task to file
                    FileHelper.writeData(mTasksArrayList, MainActivity.this);

                    mNewTaskEditText.setText("");
                    mNewTaskEditText.clearFocus();
                    Toast.makeText(
                            MainActivity.this,
                            "Task added",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });

        // Set up RecyclerView for the list of tasks
        final RecyclerView tasksListView = findViewById(R.id.items_list);
        // "RecyclerView"s need a LayoutManager to manage how items within
        // it are displayed.
        final LinearLayoutManager tasksLayoutManager =
                new LinearLayoutManager(this);
        tasksListView.setLayoutManager(tasksLayoutManager);
        /* Other LayoutManager implementations we could use include:
         *   GridLayoutManager
         *   StaggeredGridLayoutManager
         */

        // Read tasks from the file and pass them to our Recycler Adapter
        mTasksArrayList = FileHelper.readData(this);
        mTasksRecyclerAdapter = new TaskRecyclerAdapter(this, mTasksArrayList);
        // Finally, set the RecyclerView's adapter to our adapter
        tasksListView.setAdapter(mTasksRecyclerAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Make sure the EditText does not receive focus when activity is resumed
        mNewTaskEditText.clearFocus();
        /* Note: If the data set is large, it is worth looking into the finer control
         * methods available in RecyclerAdapter, which allow notifying if individual
         * items have been added, removed, or changed.
         */
        mTasksRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
