package com.example.loginregist // Sesuaikan package kamu

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val etResetEmail = findViewById<EditText>(R.id.etResetEmail)
        val etNewPassword = findViewById<EditText>(R.id.etNewPassword)
        val btnResetPassword = findViewById<Button>(R.id.btnResetPassword)

        btnResetPassword.setOnClickListener {
            val inputEmail = etResetEmail.text.toString()
            val newPass = etNewPassword.text.toString()

            if (inputEmail.isEmpty() || newPass.isEmpty()) {
                Toast.makeText(this, "Tolong isi semua kolom!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validasi Password Baru (Min 6 karakter, huruf & angka)
            val passwordPattern = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}\$".toRegex()
            if (!passwordPattern.matches(newPass)) {
                etNewPassword.error = "Masukkan kombinasi huruf, angka, min 6 karakter!"
                return@setOnClickListener
            }

            // Buka memori HP
            val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val savedEmail = sharedPreferences.getString("SAVED_EMAIL", null)

            // Cek apakah email yang dimasukkan sama dengan email yang terdaftar
            if (savedEmail != null && savedEmail == inputEmail) {
                // Berhasil! Timpa password lama dengan password baru
                val editor = sharedPreferences.edit()
                editor.putString("SAVED_PASSWORD", newPass)
                editor.apply()

                Toast.makeText(this, "Password berhasil direset! Silakan Login.", Toast.LENGTH_LONG).show()

                // Tutup halaman reset password dan kembali ke Login dengan animasi mulus
                finish()

            } else {
                etResetEmail.error = "Email tidak ditemukan atau belum terdaftar!"
            }
        }
    }
}