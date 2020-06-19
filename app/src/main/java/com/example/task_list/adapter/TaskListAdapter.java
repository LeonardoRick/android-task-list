package com.example.task_list.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.task_list.R;
import com.example.task_list.model.Task;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder> {

    ArrayList<Task> taskList;
    public TaskListAdapter(ArrayList<Task> taskList) {
        this.taskList = taskList;
    };

    @NonNull
    @Override
    public TaskListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new TaskListViewHolder(listItem);
    }

    /**
     *  To recycle views and, in did, show info
     */
    @Override
    public void onBindViewHolder(@NonNull TaskListViewHolder holder, int position) {

        // iterate on tasklist
        Task task = taskList.get(position);
        holder.textViewItem.setText(task.getTaskText());
    }


    @Override
    public int getItemCount() {
        return this.taskList.size();
    }

    /**
     * inner class TaskListViewHolder
     */
    public static class TaskListViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItem;
        public TaskListViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItem = itemView.findViewById(R.id.textViewitem);
        }
    }
}
