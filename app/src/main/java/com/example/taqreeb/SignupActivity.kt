package com.example.taqreeb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_signup)

        val name = findViewById<EditText>(R.id.edtName)
        val email = findViewById<EditText>(R.id.edtSignupEmail)
        val password = findViewById<EditText>(R.id.edtSignupPassword)
        val confirmPassword = findViewById<EditText>(R.id.edtConfirmPassword)

        val createAccount = findViewById<Button>(R.id.btnCreateAccount)
        val backToLogin = findViewById<TextView>(R.id.txtBackToLogin)

        createAccount.setOnClickListener {

            val enteredName = name.text.toString()
            val enteredEmail = email.text.toString()
            val enteredPassword = password.text.toString()
            val enteredConfirmPassword = confirmPassword.text.toString()

            if (enteredName.isEmpty() ||
                enteredEmail.isEmpty() ||
                enteredPassword.isEmpty() ||
                enteredConfirmPassword.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
                ).show()

            } else if (enteredPassword != enteredConfirmPassword) {

                Toast.makeText(
                    this,
                    "Passwords do not match",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                val preferences = getSharedPreferences(
                    "TaqreebData",
                    MODE_PRIVATE
                )

                preferences.edit()
                    .putString("name", enteredName)
                    .putString("email", enteredEmail)
                    .putString("password", enteredPassword)
                    .apply()

                Toast.makeText(
                    this,
                    "Account created successfully",
                    Toast.LENGTH_SHORT
                ).show()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)

                finish()
            }
        }

        backToLogin.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

            finish()
        }
    }
}