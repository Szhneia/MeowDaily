package com.example.loginregist

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class SeminarFormActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var etNama: EditText
    private lateinit var etEmail: EditText
    private lateinit var etHp: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var spinnerSeminar: Spinner
    private lateinit var cbAgreement: CheckBox
    private lateinit var btnSubmit: Button

    private val seminarList = arrayListOf(
        "- Select Cat Seminar -",
        "The Origin of Cats",
        "Cats in Ancient Egypt",
        "Cats and Human Civilization",
        "Cat History in Modern Life",
        "Cat Evolution and Human Culture"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seminar_form)

        btnBack = findViewById(R.id.btnBack)
        etNama = findViewById(R.id.etNama)
        etEmail = findViewById(R.id.etEmail)
        etHp = findViewById(R.id.etHp)
        rgGender = findViewById(R.id.rgGender)
        spinnerSeminar = findViewById(R.id.spinnerSeminar)
        cbAgreement = findViewById(R.id.cbAgreement)
        btnSubmit = findViewById(R.id.btnSubmit)

        setupBackButton()
        setupSpinner()
        setupSelectedSeminar()
        setupPhoneInputRule()
        setupRealTimeValidation()

        btnSubmit.setOnClickListener {
            submitForm()
        }
    }

    private fun setupBackButton() {
        btnBack.setOnClickListener {
            val intent = Intent(this, SeminarListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            seminarList
        )
        spinnerSeminar.adapter = adapter
    }

    private fun setupSelectedSeminar() {
        val selectedSeminar = intent.getStringExtra("SELECTED_SEMINAR")

        if (!selectedSeminar.isNullOrEmpty()) {
            val index = seminarList.indexOf(selectedSeminar)

            if (index != -1) {
                spinnerSeminar.setSelection(index)
            }
        }
    }

    private fun setupPhoneInputRule() {
        // Nomor HP hanya boleh angka dan maksimal 13 digit
        etHp.keyListener = DigitsKeyListener.getInstance("0123456789")
        etHp.filters = arrayOf(InputFilter.LengthFilter(13))
    }

    private fun setupRealTimeValidation() {
        etNama.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateNama(showError = true)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        etEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateEmail(showError = true)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        etHp.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateHp(showError = true)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun validateNama(showError: Boolean = true): Boolean {
        val nama = etNama.text.toString().trim()

        return if (nama.isEmpty()) {
            if (showError) {
                etNama.error = "Nama wajib diisi"
            }
            false
        } else {
            etNama.error = null
            true
        }
    }

    private fun validateEmail(showError: Boolean = true): Boolean {
        val email = etEmail.text.toString().trim()

        return when {
            email.isEmpty() -> {
                if (showError) {
                    etEmail.error = "Email wajib diisi"
                }
                false
            }

            !email.contains("@") -> {
                if (showError) {
                    etEmail.error = "Email harus mengandung @"
                }
                false
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                if (showError) {
                    etEmail.error = "Format email tidak valid"
                }
                false
            }

            else -> {
                etEmail.error = null
                true
            }
        }
    }

    private fun validateHp(showError: Boolean = true): Boolean {
        val hp = etHp.text.toString().trim()

        return when {
            hp.isEmpty() -> {
                if (showError) {
                    etHp.error = "Nomor HP wajib diisi"
                }
                false
            }

            !hp.all { it.isDigit() } -> {
                if (showError) {
                    etHp.error = "Nomor HP hanya boleh angka"
                }
                false
            }

            !hp.startsWith("08") -> {
                if (showError) {
                    etHp.error = "Nomor HP harus diawali dengan 08"
                }
                false
            }

            hp.length < 10 -> {
                if (showError) {
                    etHp.error = "Nomor HP minimal 10 digit"
                }
                false
            }

            hp.length > 13 -> {
                if (showError) {
                    etHp.error = "Nomor HP maksimal 13 digit"
                }
                false
            }

            else -> {
                etHp.error = null
                true
            }
        }
    }

    private fun validateGender(): Boolean {
        return rgGender.checkedRadioButtonId != -1
    }

    private fun validateSeminar(): Boolean {
        return spinnerSeminar.selectedItemPosition != 0
    }

    private fun validateAgreement(): Boolean {
        return cbAgreement.isChecked
    }

    private fun submitForm() {
        val isNamaValid = validateNama(showError = true)
        val isEmailValid = validateEmail(showError = true)
        val isHpValid = validateHp(showError = true)
        val isGenderValid = validateGender()
        val isSeminarValid = validateSeminar()
        val isAgreementValid = validateAgreement()

        if (!isNamaValid || !isEmailValid || !isHpValid || !isGenderValid || !isSeminarValid) {
            showWarningDialog("Lengkapi seluruh field terlebih dahulu.")
            return
        }

        if (!isAgreementValid) {
            showWarningDialog("Centang persetujuan terlebih dahulu sebelum submit.")
            return
        }

        showConfirmationDialog()
    }

    private fun showWarningDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Peringatan")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun showConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Pendaftaran")
            .setMessage("Apakah data yang Anda isi sudah benar?")
            .setPositiveButton("Ya") { _, _ ->
                saveDataAndGoToHistory()
            }
            .setNegativeButton("Tidak", null)
            .show()
    }

    private fun saveDataAndGoToHistory() {
        val selectedGenderId = rgGender.checkedRadioButtonId
        val gender = findViewById<RadioButton>(selectedGenderId).text.toString()

        val nama = etNama.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val hp = etHp.text.toString().trim()
        val seminar = spinnerSeminar.selectedItem.toString()

        val regPref = getSharedPreferences("REGISTRATION_DATA", MODE_PRIVATE)
        regPref.edit()
            .putString("NAMA", nama)
            .putString("EMAIL", email)
            .putString("HP", hp)
            .putString("GENDER", gender)
            .putString("SEMINAR", seminar)
            .putBoolean("HAS_REGISTERED", true)
            .apply()

        val seminarPref = getSharedPreferences("SEMINAR_DATA", MODE_PRIVATE)
        val oldHistory = seminarPref.getString("SEMINAR_HISTORY", "") ?: ""

        val historyList = oldHistory
            .split("|||")
            .filter { it.isNotBlank() }
            .toMutableList()

        if (!historyList.contains(seminar)) {
            historyList.add(0, seminar)
        }

        seminarPref.edit()
            .putString("CURRENT_SEMINAR", seminar)
            .putString("SEMINAR_HISTORY", historyList.joinToString("|||"))
            .apply()

        val intent = Intent(this, ResultActivity::class.java)
        startActivity(intent)
        finish()
    }
}