package com.example.lista;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TaskDatabaseHelper dbHelper;
    private TaskAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new TaskDatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        Button addTaskButton = findViewById(R.id.addTaskButton);
        Button reloadTasksButton = findViewById(R.id.reloadTasksButton);

        List<Task> tasks = loadTasks();

        adapter = new TaskAdapter(tasks,
                task -> {
                    Intent intent = new Intent(MainActivity.this, TaskDetailsActivity.class);
                    intent.putExtra("taskId", task.getId());
                    startActivity(intent);
                },
                (task, isChecked) -> {
                    updateTaskCompletion(task.getId(), isChecked);
                },
                task -> {
                    deleteTask(task.getId());
                    reloadTasks();
                });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        addTaskButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddTaskActivity.class)));

        reloadTasksButton.setOnClickListener(v -> reloadTasks());
    }

    private List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT * FROM tasks", null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            boolean isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow("is_completed")) == 1;
            tasks.add(new Task(id, title, description, isCompleted));
        }

        cursor.close();
        return tasks;
    }

    private void reloadTasks() {
        List<Task> updatedTasks = loadTasks();
        adapter = new TaskAdapter(updatedTasks,
                task -> {
                    Intent intent = new Intent(MainActivity.this, TaskDetailsActivity.class);
                    intent.putExtra("taskId", task.getId());
                    startActivity(intent);
                },
                (task, isChecked) -> updateTaskCompletion(task.getId(), isChecked),
                task -> { // Callback para deletar tarefa
                    deleteTask(task.getId());
                    reloadTasks();
                });
        recyclerView.setAdapter(adapter);
        Toast.makeText(this, "Lista recarregada!", Toast.LENGTH_SHORT).show();
    }

    private void updateTaskCompletion(int taskId, boolean isCompleted) {
        int completionStatus = isCompleted ? 1 : 0;
        dbHelper.getWritableDatabase().execSQL(
                "UPDATE tasks SET is_completed = ? WHERE id = ?",
                new Object[]{completionStatus, taskId});
    }

    private void deleteTask(int taskId) {
        dbHelper.getWritableDatabase().execSQL(
                "DELETE FROM tasks WHERE id = ?",
                new Object[]{taskId});
        Toast.makeText(this, "Tarefa deletada!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadTasks();
    }
}
