package com.phixyn.notephix

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Date

class MainActivity : AppCompatActivity() {
    private var appDatabase: AppDatabase? = null
    private var noteDao: NoteDao? = null

    private var newNoteEditText: EditText? = null
//    private var notesList: List<Note>? = null
    private var notesList: LiveData<List<Note>>? = null
    private var notesRecyclerAdapter: NoteRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        appDatabase = AppDatabase.getAppDatabase(context = this)
        noteDao = appDatabase?.noteDao()

        // Get references of the EditText and Add button
        newNoteEditText = findViewById(R.id.item_edit_text)
        val addTaskButton = findViewById<Button>(R.id.add_btn)

        // Set up EditText for typing a new task
        // Set up an event listener for when the focus on EditText changes
        newNoteEditText!!.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            // Hide soft keyboard if EditText loses focus
            if (!hasFocus) {
                val imm = getSystemService(
                    Context.INPUT_METHOD_SERVICE
                ) as InputMethodManager
                imm.hideSoftInputFromWindow(
                    newNoteEditText!!.windowToken,
                    0
                )
            }
        }
        // Set up an event listener for editor actions
        newNoteEditText!!.setOnEditorActionListener(
            TextView.OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // When IME_ACTION_DONE event is triggered, send a
                    // click event to the add task button.
                    addTaskButton.performClick()
                    // Return true because we have consumed the action
                    return@OnEditorActionListener true
                }
                false
            }
        )

        // Set up "Add task" button
        // Register an event listener for onClick to the Button
        addTaskButton.setOnClickListener {
            val taskEntered = newNoteEditText!!.text.toString()
            // Ensure that the string entered is not empty or all spaces
            if (taskEntered.isNotEmpty() && taskEntered.trim { it <= ' ' }.isNotEmpty()) {
                // TODO: Not sure if this is the best way to do things
                // Should we add via the adapter directly, like we did previously
                // with the ArrayAdapter? Or is this fine?
                // tasksAdapter.add(taskEntered);
                val dateNow = Date()
                val newNote = Note(
                    body = taskEntered,
                    createdOn = dateNow,
                    updatedOn = dateNow
                )

                /* Add new task to the beginning of the ArrayList
                 * Unlike notesList.add(taskEntered); this will ensure that
                 * new tasks appear at the top of the RecyclerView, rather than
                 * being added at the bottom. */
                // Write note to database
                noteDao?.insertNote(newNote)
                // notesList!!.add(0, taskEntered)
                notesRecyclerAdapter!!.notifyDataSetChanged()
                // Write task to file
                // FileHelper.writeData(notesList!!, this@MainActivity)

                newNoteEditText!!.setText("")
                newNoteEditText!!.clearFocus()
                Toast.makeText(
                    this@MainActivity,
                    "Task added",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Set up RecyclerView for the list of tasks
        val tasksListView = findViewById<RecyclerView>(R.id.items_list)
        // "RecyclerView"s need a LayoutManager to manage how items within
        // it are displayed.
        val tasksLayoutManager = LinearLayoutManager(this)
        tasksListView.layoutManager = tasksLayoutManager
        /* Other LayoutManager implementations we could use include:
         *   GridLayoutManager
         *   StaggeredGridLayoutManager */

        // Read tasks from the file and pass them to our Recycler Adapter
        // notesList = FileHelper.readData(this)

        // Read notes from the Room database and pass them to our Recycler Adapter
        notesList = noteDao?.getAll()
        notesRecyclerAdapter = NoteRecyclerAdapter(this, notesList!!)
        // Finally, set the RecyclerView's adapter to our adapter
        tasksListView.adapter = notesRecyclerAdapter
    }

    override fun onResume() {
        super.onResume()
        // Make sure the EditText does not receive focus when activity is resumed
        newNoteEditText!!.clearFocus()
        /* Note: If the data set is large, it is worth looking into the finer control
         * methods available in RecyclerAdapter, which allow notifying if individual
         * items have been added, removed, or changed. */
        notesRecyclerAdapter!!.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /* Handle action bar item clicks here. The action bar will
         * automatically handle clicks on the Home/Up button, so long
         * as you specify a parent activity in AndroidManifest.xml. */
        val id = item.itemId

        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }
}
