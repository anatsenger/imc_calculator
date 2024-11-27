package com.example.lista;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TaskDetailsActivity extends AppCompatActivity {

    private TaskDatabaseHelper dbHelper;
    private int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        dbHelper = new TaskDatabaseHelper(this);

        TextView titleTextView = findViewById(R.id.taskTitleDetails);
        TextView descriptionTextView = findViewById(R.id.taskDescriptionDetails);
        Button editButton = findViewById(R.id.editTaskButton);

        taskId = getIntent().getIntExtra("taskId", 0);

        Task task = getTaskById(taskId);
        if (task != null) {
            titleTextView.setText(task.getTitle());
            descriptionTextView.setText(task.getDescription());
        }

        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(TaskDetailsActivity.this, AddTaskActivity.class);
            intent.putExtra("taskId", taskId);
            startActivity(intent);
        });
    }

    private Task getTaskById(int id) {
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                "SELECT * FROM tasks WHERE id = ?",
                new String[]{String.valueOf(id)}
        );

        Task task = null;
        if (cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            boolean isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow("is_completed")) == 1;
            task = new Task(id, title, description, isCompleted);
        }
        cursor.close();
        return task;
    }

}
