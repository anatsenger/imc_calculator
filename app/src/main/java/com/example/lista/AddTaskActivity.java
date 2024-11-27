package com.example.lista;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {

    private TaskDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        dbHelper = new TaskDatabaseHelper(this);
        EditText titleInput = findViewById(R.id.taskTitleInput);
        EditText descriptionInput = findViewById(R.id.taskDescriptionInput);
        Button saveButton = findViewById(R.id.saveTaskButton);

        saveButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString();
            String description = descriptionInput.getText().toString();

            dbHelper.getWritableDatabase().execSQL("INSERT INTO tasks (title, description) VALUES (?, ?)", new Object[]{title, description});
            finish();
        });
    }
}
