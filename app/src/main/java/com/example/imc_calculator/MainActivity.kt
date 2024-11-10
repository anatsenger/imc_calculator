package com.example.imc_calculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weightInput = findViewById<EditText>(R.id.weightInput)
        val heightInput = findViewById<EditText>(R.id.heightInput)
        val calculateButton = findViewById<Button>(R.id.calculateButton)
        val imcResult = findViewById<TextView>(R.id.imcResult)

        calculateButton.setOnClickListener {
            val weightText = weightInput.text.toString()
            val heightText = heightInput.text.toString()

            if (weightText.isNotEmpty() && heightText.isNotEmpty()) {
                val weight = weightText.toDoubleOrNull()
                val height = heightText.toDoubleOrNull()

                if (weight != null && height != null && height > 0) {
                    val imc = weight / (height * height)
                    imcResult.text = String.format("IMC: %.2f", imc)
                } else {
                    imcResult.text = "Por favor, insira valores válidos."
                }
            } else {
                imcResult.text = "Por favor, preencha todos os campos."
            }
        }
    }
}
