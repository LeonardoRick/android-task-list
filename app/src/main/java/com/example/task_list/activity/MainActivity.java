package com.example.task_list.activity;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.task_list.R;
import com.example.task_list.helper.DAO.TaskDAO;
import com.example.task_list.adapter.TaskListAdapter;
import com.example.task_list.helper.RecyclerItemClickListener;
import com.example.task_list.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    Toolbar toolbar;
    private ArrayList<Task> taskList = new ArrayList<>();
    TaskDAO tDao;
    Task selectedTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // task Data Access Object to control database
         tDao = new TaskDAO(getApplicationContext());

        // toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Task List");
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);


        // click events on reclyclerVIew and FAB button
        configItemClickEvents();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddTaskActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadTaskList();
    }

    public void loadTaskList() {
        loadTaskData();
        /* RecyclerView config */
        recyclerView.setHasFixedSize(true);

        // set layout manager
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this); // linear Layout
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));

        // set adapter
        TaskListAdapter adapter = new TaskListAdapter(taskList);
        recyclerView.setAdapter(adapter);
    }

    public void loadTaskData() {
        taskList = tDao.list();
    }

    /**
     * config RecyclerView click listener
     */
    private void configItemClickEvents() {
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Log.e(TAG, "onItemClick: ");
                                // recover data to edit
                                selectedTask = taskList.get(position);

                                // send data to next acitivity
                                Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
                                intent.putExtra("selectedTask", selectedTask);
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                // recover data to delete
                                selectedTask = taskList.get(position);

                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                                //dialog config
                                dialog.setTitle("Confirm Delete");
                                dialog.setMessage("Do you really want's to delete: " + selectedTask.getTaskText() + " ?");
                                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(tDao.delete(selectedTask)) {
                                            // recall loadTaskList to update RecyclerView
                                            loadTaskList();
                                            Toast.makeText(getApplicationContext(),
                                                "Task deleted!",
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(),
                                                    "Error to delete task :(",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                dialog.setNegativeButton("No", null);
                                dialog.create();
                                dialog.show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );
    }
}