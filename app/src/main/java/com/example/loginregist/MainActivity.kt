package com.example.loginregist // Sesuaikan package kamu

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
        setContentView(R.layout.activity_main)

        // 1. HUBUNGKAN SEMUA KOMPONEN DARI XML
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val textRegister = findViewById<TextView>(R.id.textRegister)
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)

        // 2. LOGIKA PINDAH KE HALAMAN REGISTER
        textRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        // 3. LOGIKA PINDAH KE HALAMAN FORGOT PASSWORD
        tvForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        // 4. LOGIKA TOMBOL LOGIN DIKLIK
        btnLogin.setOnClickListener {
            val inputEmail = etEmail.text.toString()
            val inputPassword = etPassword.text.toString()

            // Buka memori HP (SharedPreferences)
            val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val savedEmail = sharedPreferences.getString("SAVED_EMAIL", null)
            val savedPassword = sharedPreferences.getString("SAVED_PASSWORD", null)

            // Cek apakah ada yang kosong
            if (inputEmail.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(this, "Tolong isi Email dan Password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Skenario 1: Email belum terdaftar atau salah
            if (savedEmail == null || savedEmail != inputEmail) {
                Toast.makeText(this, "Akun belum terdaftar, silakan registrasi dulu.", Toast.LENGTH_LONG).show()

                // Skenario 2: Email benar, tapi Password salah
            } else if (savedPassword != inputPassword) {
                etPassword.error = "Password salah, masukkan password yang benar"

                // Skenario 3: Keduanya Benar!
            } else {
                Toast.makeText(this, "Login Berhasil! Selamat Datang.", Toast.LENGTH_SHORT).show()
                // (Nanti kodingan pindah ke halaman Dashboard/Home ditaruh di sini)
            }
        }
    }
}