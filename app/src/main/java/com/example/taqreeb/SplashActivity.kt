package com.example.taqreeb

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        // Wait for 2 seconds

        window.decorView.postDelayed({

            val intent = Intent(
                this,
                LoginActivity::class.java
            )

            startActivity(intent)

            finish()

        }, 2000)
    }
}