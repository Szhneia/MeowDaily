package com.example.loginregist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Selalu tampilkan halaman login saat app dibuka
        setContentView(R.layout.activity_main)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val textRegister = findViewById<TextView>(R.id.textRegister)
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)

        textRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        tvForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val emailInput = etEmail.text.toString().trim()
            val passInput = etPassword.text.toString()

            if (emailInput.isEmpty() || passInput.isEmpty()) {
                Toast.makeText(this, "Isi email dan password dulu ya!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sharedPref = getSharedPreferences("DataUser", MODE_PRIVATE)

            val savedEmail = sharedPref.getString("email_save", "") ?: ""
            val savedPassOld = sharedPref.getString("pass_save", "") ?: ""
            val savedPassNew = sharedPref.getString("password_save", "") ?: ""

            val emailMatch = emailInput == savedEmail
            val passwordMatch = passInput == savedPassOld || passInput == savedPassNew

            if (emailMatch && passwordMatch) {
                sharedPref.edit()
                    .putBoolean("is_logged_in", true)
                    .apply()

                Toast.makeText(this, "Login Berhasil! Halo $savedEmail", Toast.LENGTH_LONG).show()

                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.putExtra("USER_EMAIL", emailInput)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Email atau Password salah / belum terdaftar!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}