package com.example.loginregist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var tvNamaResult: TextView
    private lateinit var tvEmailResult: TextView
    private lateinit var tvHpResult: TextView
    private lateinit var tvGenderResult: TextView
    private lateinit var tvSeminarResult: TextView
    private lateinit var btnBackHome: Button
    private lateinit var btnHistory: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        tvNamaResult = findViewById(R.id.tvNamaResult)
        tvEmailResult = findViewById(R.id.tvEmailResult)
        tvHpResult = findViewById(R.id.tvHpResult)
        tvGenderResult = findViewById(R.id.tvGenderResult)
        tvSeminarResult = findViewById(R.id.tvSeminarResult)
        btnBackHome = findViewById(R.id.btnBackHome)
        btnHistory = findViewById(R.id.btnHistory)

        loadRegistrationData()

        btnBackHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        btnHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            intent.putExtra("OPEN_TAB", "CURRENT")
            startActivity(intent)
            finish()
        }
    }

    private fun loadRegistrationData() {
        val sharedPref = getSharedPreferences("REGISTRATION_DATA", MODE_PRIVATE)

        val nama = sharedPref.getString("NAMA", "-")
        val email = sharedPref.getString("EMAIL", "-")
        val hp = sharedPref.getString("HP", "-")
        val gender = sharedPref.getString("GENDER", "-")
        val seminar = sharedPref.getString("SEMINAR", "-")

        tvNamaResult.text = "Name: $nama"
        tvEmailResult.text = "Email: $email"
        tvHpResult.text = "Phone Number: $hp"
        tvGenderResult.text = "Gender: $gender"
        tvSeminarResult.text = "Cat Seminar: $seminar"
    }
}