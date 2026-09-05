package com.example.taqreeb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        // Firebase Authentication
        auth = FirebaseAuth.getInstance()


        // Check if user is already logged in

        if (auth.currentUser != null) {

            val intent = Intent(
                this,
                MainActivity::class.java
            )

            startActivity(intent)

            finish()

            return
        }


        val email = findViewById<EditText>(
            R.id.edtEmail
        )

        val password = findViewById<EditText>(
            R.id.edtPassword
        )

        val loginButton = findViewById<Button>(
            R.id.btnLogin
        )

        val signupText = findViewById<TextView>(
            R.id.txtSignup
        )

        val forgotPassword = findViewById<TextView>(
            R.id.txtForgotPassword
        )


        // Login

        loginButton.setOnClickListener {

            val enteredEmail =
                email.text.toString().trim()

            val enteredPassword =
                password.text.toString()


            if (enteredEmail.isEmpty() ||
                enteredPassword.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Please enter email and password",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                auth.signInWithEmailAndPassword(
                    enteredEmail,
                    enteredPassword
                )
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {

                            Toast.makeText(
                                this,
                                "Login successful",
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent = Intent(
                                this,
                                MainActivity::class.java
                            )

                            startActivity(intent)

                            finish()

                        } else {

                            Toast.makeText(
                                this,
                                task.exception?.message
                                    ?: "Invalid email or password",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }


        // Forgot Password

        forgotPassword.setOnClickListener {

            val enteredEmail =
                email.text.toString().trim()


            if (enteredEmail.isEmpty()) {

                Toast.makeText(
                    this,
                    "Please enter your email first",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                auth.sendPasswordResetEmail(
                    enteredEmail
                )
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {

                            Toast.makeText(
                                this,
                                "Password reset email sent!",
                                Toast.LENGTH_LONG
                            ).show()

                        } else {

                            Toast.makeText(
                                this,
                                task.exception?.message
                                    ?: "Failed to send reset email",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }


        // Go to Signup

        signupText.setOnClickListener {

            val intent = Intent(
                this,
                SignupActivity::class.java
            )

            startActivity(intent)
        }
    }
}

