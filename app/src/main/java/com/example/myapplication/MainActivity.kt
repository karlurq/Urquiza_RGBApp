package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import androidx.core.graphics.toColorInt

class MainActivity : AppCompatActivity() {

    private lateinit var redInputEditText: TextInputEditText
    private lateinit var greenInputEditText: TextInputEditText
    private lateinit var blueInputEditText: TextInputEditText
    private lateinit var createButton: Button
    private lateinit var colorDisplayView: View


    private lateinit var redInputLayout: TextInputLayout
    private lateinit var greenInputLayout: TextInputLayout
    private lateinit var blueInputLayout: TextInputLayout


    private val defaultDisplayColor by lazy { "#FF5722".toColorInt() }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        redInputEditText = findViewById(R.id.redInput)
        greenInputEditText = findViewById(R.id.greenInput)
        blueInputEditText = findViewById(R.id.blueInput)
        createButton = findViewById(R.id.createButton)
        colorDisplayView = findViewById(R.id.colorDisplay)


        redInputLayout = findViewById(R.id.redInputLayout)
        greenInputLayout = findViewById(R.id.greenInputLayout)
        blueInputLayout = findViewById(R.id.blueInputLayout)


        colorDisplayView.setBackgroundColor(defaultDisplayColor)

        createButton.setOnClickListener {
            validateAndCreateColor()
        }
    }

    private fun validateAndCreateColor() {
        val redValue = redInputEditText.text.toString().trim().uppercase()
        val greenValue = greenInputEditText.text.toString().trim().uppercase()
        val blueValue = blueInputEditText.text.toString().trim().uppercase()

        var isValid = true


        if (!isValidHexChannel(redValue)) {
            redInputLayout.error = "Red: 00-FF"
            redInputLayout.isErrorEnabled = true
            isValid = false
        } else {
            redInputLayout.error = null
            redInputLayout.isErrorEnabled = false
        }


        if (!isValidHexChannel(greenValue)) {
            greenInputLayout.error = "Green: 00-FF"
            greenInputLayout.isErrorEnabled = true
            isValid = false
        } else {
            greenInputLayout.error = null
            greenInputLayout.isErrorEnabled = false
        }


        if (!isValidHexChannel(blueValue)) {
            blueInputLayout.error = "Blue: 00-FF"
            blueInputLayout.isErrorEnabled = true
            isValid = false
        } else {
            blueInputLayout.error = null
            blueInputLayout.isErrorEnabled = false
        }

        if (isValid) {
            val hexColorString = "#$redValue$greenValue$blueValue"
            try {
                val color = hexColorString.toColorInt()
                colorDisplayView.setBackgroundColor(color)
            } catch (_: IllegalArgumentException) {
                Toast.makeText(this, "Error: Invalid color format generated.", Toast.LENGTH_SHORT).show()
                colorDisplayView.setBackgroundColor(defaultDisplayColor) // Reset to default
            }
        } else {
            colorDisplayView.setBackgroundColor(defaultDisplayColor)
            Toast.makeText(this, "Please correct the input values.", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Validates if the given string is a two-character hexadecimal value (0-9, A-F, a-f).
     */
    private fun isValidHexChannel(value: String): Boolean {
        if (value.length != 2) {
            return false
        }

        return value.matches(Regex("[0-9A-Fa-f]{2}"))
    }
}
