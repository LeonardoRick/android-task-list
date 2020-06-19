package com.example.task_list.helper.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.task_list.helper.LocalDbHelper;
import com.example.task_list.model.Task;

import java.util.ArrayList;

/**
 * DAO: Data Access Object
 * Used as interface with database
 */

public class TaskDAO implements ITaskDAO {
    private static final String TAG = "TaskDAO";
    private SQLiteDatabase write;
    private SQLiteDatabase read;
    LocalDbHelper localDb;

    public TaskDAO(Context context) {
        localDb = new LocalDbHelper(context);
        write = localDb.getWritableDatabase(); // object that allows to write on database
        read = localDb.getReadableDatabase(); // object that allows to read from database
    }
    @Override
    public boolean save(Task task) {
        ContentValues cv = new ContentValues();
        cv.put(LocalDbHelper.KEY_TEXT, task.getTaskText());
        try {
            write.insert(LocalDbHelper.TABLE_NAME, null /*only save if task is filled*/, cv);
            Log.i(TAG, "save: sucesss");
        } catch (Exception e) {
            Log.e(TAG, "error to save task -" + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Task task) {
        return false;
    }

    @Override
    public boolean delete(Task task) {
        return false;
    }

    @Override
    public ArrayList<Task> list() {
        ArrayList<Task> tasks = new ArrayList<>();
        String SELECT = "SELECT * FROM " + LocalDbHelper.TABLE_NAME + ";";
        Cursor cursor = read.rawQuery(SELECT, null /*selectionArgs*/);


        try {
            // index of table so we don't need to write column index as number
            int iTaskText = cursor.getColumnIndex(LocalDbHelper.KEY_TEXT);
            int iId = cursor.getColumnIndex(LocalDbHelper.KEY_ID);
            while(cursor.moveToNext()) { // While we have lines (registers) on table. simultaneously move to next
                // mapping task object with each returned info of database
                Task task = new Task(
                        cursor.getLong(iId),
                        cursor.getString(iTaskText)
                );
                tasks.add(task);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return tasks;
    }
}
