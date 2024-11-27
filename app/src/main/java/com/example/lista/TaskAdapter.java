package com.example.lista;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final List<Task> tasks;
    private final OnTaskClickListener onTaskClick;
    private final OnTaskCheckedChangeListener onTaskCheckedChange;
    private final OnTaskDeleteListener onTaskDelete;

    public interface OnTaskClickListener {
        void onTaskClick(Task task);
    }

    public interface OnTaskCheckedChangeListener {
        void onTaskCheckedChange(Task task, boolean isChecked);
    }

    public interface OnTaskDeleteListener {
        void onTaskDelete(Task task);
    }

    public TaskAdapter(List<Task> tasks, OnTaskClickListener onTaskClick,
                       OnTaskCheckedChangeListener onTaskCheckedChange,
                       OnTaskDeleteListener onTaskDelete) {
        this.tasks = tasks;
        this.onTaskClick = onTaskClick;
        this.onTaskCheckedChange = onTaskCheckedChange;
        this.onTaskDelete = onTaskDelete;
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        CheckBox checkBox;
        ImageButton deleteButton;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.taskTitle);
            description = itemView.findViewById(R.id.taskDescription);
            checkBox = itemView.findViewById(R.id.taskCheckBox);
            deleteButton = itemView.findViewById(R.id.deleteTaskButton);
        }

        public void bind(Task task, OnTaskClickListener onTaskClick,
                         OnTaskCheckedChangeListener onTaskCheckedChange,
                         OnTaskDeleteListener onTaskDelete) {
            title.setText(task.getTitle());
            description.setText(task.getDescription());

            checkBox.setOnCheckedChangeListener(null);
            checkBox.setChecked(task.isCompleted());

            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> onTaskCheckedChange.onTaskCheckedChange(task, isChecked));

            itemView.setOnClickListener(v -> onTaskClick.onTaskClick(task));

            deleteButton.setOnClickListener(v -> onTaskDelete.onTaskDelete(task));
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.bind(task, onTaskClick, onTaskCheckedChange, onTaskDelete);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
