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

            // CUMA GANTI BARIS INI: Biar nyambung ke data Register yang tadi
            val sharedPreferences = getSharedPreferences("DataUser", MODE_PRIVATE)
            val savedEmail = sharedPreferences.getString("email_save", null)

            if (savedEmail != null && savedEmail == inputEmail) {

                val editor = sharedPreferences.edit()
                // CUMA GANTI BARIS INI: Biar numpuk password yang lama
                editor.putString("pass_save", newPass)
                editor.apply()

                Toast.makeText(this, "Password berhasil direset! Silakan Login.", Toast.LENGTH_LONG).show()
                finish()

            } else {
                etResetEmail.error = "Email tidak ditemukan atau belum terdaftar!"
            }
        }
    }
}