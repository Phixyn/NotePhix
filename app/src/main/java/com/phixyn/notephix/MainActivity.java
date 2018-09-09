package com.phixyn.notephix;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText taskTextField;
    private Button addTaskButton;
    // private ListView tasksListView;

    private RecyclerView tasksListView;

    private ArrayList<String> tasksList;
    // private ArrayAdapter<String> tasksAdapter;
    private TaskRecyclerAdapter tasksRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        taskTextField = findViewById(R.id.item_edit_text);
        addTaskButton = findViewById(R.id.add_btn);
        tasksListView = findViewById(R.id.items_list);

        // RecyclerViews need a LayoutManager to manage how items within it are displayed
        final LinearLayoutManager tasksLayoutManager = new LinearLayoutManager(this);
        tasksListView.setLayoutManager(tasksLayoutManager);
        // Other LayoutManager implementations include:
        // * GridLayoutManager
        // * StaggeredGridLayoutManager

        // Read tasks from file and show them in the ListView
        tasksList = FileHelper.readData(this);
        // final TaskRecyclerAdapter taskRecyclerAdapter = new TaskRecyclerAdapter(this, tasksList);

        // tasksAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasksList);
        tasksRecyclerAdapter = new TaskRecyclerAdapter(this, tasksList);
        tasksListView.setAdapter(tasksRecyclerAdapter);

        addTaskButton.setOnClickListener(this);
        // tasksListView.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Note: If the data set is large, it is worth looking into the finer control methods
        // available in RecyclerAdapter, which allow notifying if individual items have been added,
        // removed, or changed.
        tasksRecyclerAdapter.notifyDataSetChanged();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_btn:
                String taskEntered = taskTextField.getText().toString();
                // TODO: Not sure if this is the best way to do things
                // Should we add via the adapter directly, like we did with the ArrayAdapter?
                // Or is this fine?
                // tasksAdapter.add(taskEntered);
                tasksList.add(taskEntered);
                tasksRecyclerAdapter.notifyDataSetChanged();
                // Write task to file
                FileHelper.writeData(tasksList, this);

                taskTextField.setText("");
                Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /*
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        tasksList.remove(position);
        tasksRecyclerAdapter.notifyDataSetChanged();
        // Write updated data set to file
        FileHelper.writeData(tasksList, this);

        Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show();
    }*/
}
