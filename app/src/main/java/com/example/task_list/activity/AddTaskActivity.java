package com.example.task_list.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.task_list.R;
import com.example.task_list.helper.DAO.TaskDAO;
import com.example.task_list.model.Task;
import com.google.android.material.textfield.TextInputEditText;

public class AddTaskActivity extends AppCompatActivity {

    TextInputEditText textInputList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        textInputList = findViewById(R.id.textInputList);


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
                // creating Object Task from input
                String taskText = textInputList.getText().toString();
//                if(!taskText.isEmpty()) {
                    Task task = new Task();
                    task.setTaskText(textInputList.getText().toString());

                    // save text using Task Data Access Object
                    TaskDAO taskDao = new TaskDAO(getApplicationContext());
                    taskDao.save(task);
//                }
                //closes activity after adding task
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}