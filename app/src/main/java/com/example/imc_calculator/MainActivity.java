package com.example.imc_calculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText weightInput = findViewById(R.id.weightInput);
        EditText heightInput = findViewById(R.id.heightInput);
        Button calculateButton = findViewById(R.id.calculateButton);
        TextView imcResult = findViewById(R.id.imcResult);

        calculateButton.setOnClickListener(v -> {
            String weightText = weightInput.getText().toString();
            String heightText = heightInput.getText().toString();

            if (!weightText.isEmpty() && !heightText.isEmpty()) {
                try {
                    double weight = Double.parseDouble(weightText);
                    double height = Double.parseDouble(heightText);

                    if (height > 0) {
                        double imc = weight / (height * height);
                        imcResult.setText(String.format("IMC: %.2f", imc));
                    } else {
                        imcResult.setText("Por favor, insira valores válidos.");
                    }
                } catch (NumberFormatException e) {
                    imcResult.setText("Por favor, insira valores válidos.");
                }
            } else {
                imcResult.setText("Por favor, preencha todos os campos.");
            }
        });
    }
}
