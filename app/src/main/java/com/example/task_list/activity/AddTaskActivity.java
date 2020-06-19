package com.example.task_list.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.task_list.R;
import com.example.task_list.helper.DAO.TaskDAO;
import com.example.task_list.model.Task;
import com.google.android.material.textfield.TextInputEditText;

public class AddTaskActivity extends AppCompatActivity {

    private TextInputEditText textInputList;
    private Task selectedTask;
    private boolean editing  = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        textInputList = findViewById(R.id.textInputList);

        // recover data only if its edit action (When user clicks on pre-saved task
        selectedTask = (Task) getIntent().getSerializableExtra("selectedTask");
        if(selectedTask != null) {
            textInputList.setText(selectedTask.getTaskText());
            editing = true;
        }


        Toolbar toolbar = findViewById(R.id.toolbarAddTask);
        toolbar.setTitle("Add task");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_task_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemSave:
                TaskDAO taskDao = new TaskDAO(getApplicationContext());
                String taskText = textInputList.getText().toString();
                Task task = new Task();
                if(editing) {
                    if(!taskText.isEmpty()) {
                        task.setId(selectedTask.getId());
                        task.setTaskText(taskText);

                        if(taskDao.update(task)) {
                            Toast.makeText(getApplicationContext(),
                                    "Task updated successfully",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Error updating task :(",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                } else { // if not editing, we are creating a task
//                    String taskText = textInputList.getText().toString();
                    if(!taskText.isEmpty()) {
                        // creating Object Task from input
                        task.setTaskText(textInputList.getText().toString());

                        // save text using Task Data Access Object and using return to show info to user
                        if(taskDao.save(task)) {
                            Toast.makeText(getApplicationContext(),
                                    "Task saved successfully",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Error saving task :(",
                                    Toast.LENGTH_SHORT).show();
                        };
                    }
                }
                //closes activity after adding task
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}