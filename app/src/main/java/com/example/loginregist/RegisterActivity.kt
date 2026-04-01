package com.example.loginregist // Sesuaikan dengan namamu

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // 1. MENGATUR DROPDOWN LOCATION
        val spinnerLocation = findViewById<Spinner>(R.id.spinnerLocation)
        val locationList = arrayOf("-Select location-", "Jakarta", "Bandung", "Surabaya", "Yogyakarta", "Bali")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, locationList)
        spinnerLocation.adapter = adapter

        // 2. MENGHUBUNGKAN KOMPONEN FORM
        val etEmailReg = findViewById<EditText>(R.id.etEmailReg)
        val etPasswordReg = findViewById<EditText>(R.id.etPasswordReg)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)
        val btnGetStarted = findViewById<Button>(R.id.btnGetStarted)

        // 3. LOGIKA TOMBOL GET STARTED DIKLIK
        btnGetStarted.setOnClickListener {
            val email = etEmailReg.text.toString()
            val pass = etPasswordReg.text.toString()
            val confirmPass = etConfirmPassword.text.toString()

            var isAdaYangKosong = false // Penanda apakah ada form yang bolong

            // --- CEK SATU PER SATU MANA YANG KOSONG ---
            if (email.isEmpty()) {
                etEmailReg.error = "Email wajib diisi!"
                shakeAnimation(etEmailReg) // Getarkan kolom email
                isAdaYangKosong = true
            }
            if (pass.isEmpty()) {
                etPasswordReg.error = "Password wajib diisi!"
                shakeAnimation(etPasswordReg) // Getarkan kolom password
                isAdaYangKosong = true
            }
            if (confirmPass.isEmpty()) {
                etConfirmPassword.error = "Konfirmasi password wajib diisi!"
                shakeAnimation(etConfirmPassword) // Getarkan kolom konfirmasi
                isAdaYangKosong = true
            }

            // Kalau ada minimal 1 yang kosong, hentikan proses di sini
            if (isAdaYangKosong) {
                Toast.makeText(this, "Lengkapi data diri terlebih dahulu!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // --- CEK VALIDASI PASSWORD LEMAH ---
            val passwordPattern = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}\$".toRegex()
            if (!passwordPattern.matches(pass)) {
                etPasswordReg.error = "Masukkan kombinasi huruf, angka, minimal 6 karakter!"
                shakeAnimation(etPasswordReg) // Getarkan juga kalau passwordnya lemah!
                return@setOnClickListener
            }

            // --- CEK PASSWORD SAMA ATAU TIDAK ---
            if (pass == confirmPass) {

                // Simpan Data ke Memori
                val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("SAVED_EMAIL", email)
                editor.putString("SAVED_PASSWORD", pass)
                editor.apply()

                Toast.makeText(this, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show()

                // Pindah ke halaman Login
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

                // --- INI KODE SAKTI ANIMASI SMOOTH-NYA ---
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

                finish()

            } else {
                etConfirmPassword.error = "Password tidak cocok!"
                shakeAnimation(etConfirmPassword) // Getarkan kalau password gak sama!
            }
        }
    }

    // ==========================================================
    // FUNGSI RAHASIA: MEMBUAT EFEK GETAR
    // ==========================================================
    private fun shakeAnimation(view: View) {
        val animator = ObjectAnimator.ofFloat(view, "translationX", 0f, 25f, -25f, 25f, -25f, 15f, -15f, 6f, -6f, 0f)
        animator.duration = 400
        animator.start()
    }
}