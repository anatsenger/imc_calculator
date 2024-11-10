package com.example.imc_calculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextHeight = findViewById<EditText>(R.id.editTextHeight)
        val editTextWeight = findViewById<EditText>(R.id.editTextWeight)
        val buttonCalculate = findViewById<Button>(R.id.buttonCalculate)

        buttonCalculate.setOnClickListener {
            val heightText = editTextHeight.text.toString()
            val weightText = editTextWeight.text.toString()

            if (heightText.isNotEmpty() && weightText.isNotEmpty()) {
                val height = heightText.toDoubleOrNull()
                val weight = weightText.toDoubleOrNull()

                if (height != null && weight != null && height > 0) {
                    val imc = weight / (height * height)
                    val result = String.format("Seu IMC é %.2f", imc)
                    Toast.makeText(this, result, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Por favor, insira valores válidos.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
