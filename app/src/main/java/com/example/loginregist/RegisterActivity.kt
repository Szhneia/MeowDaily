package com.example.loginregist

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var btnGetStarted: Button
    private lateinit var spinnerLocation: Spinner

    private lateinit var cbCoding: CheckBox
    private lateinit var cbSwimming: CheckBox
    private lateinit var cbReading: CheckBox
    private lateinit var cbGaming: CheckBox
    private lateinit var cbTravelling: CheckBox
    private lateinit var cbMusic: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmailReg)
        etPassword = findViewById(R.id.etPasswordReg)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        rgGender = findViewById(R.id.rgGender)
        btnGetStarted = findViewById(R.id.btnGetStarted)
        spinnerLocation = findViewById(R.id.spinnerLocation)

        cbCoding = findViewById(R.id.cbCoding)
        cbSwimming = findViewById(R.id.cbSwimming)
        cbReading = findViewById(R.id.cbReading)
        cbGaming = findViewById(R.id.cbGaming)
        cbTravelling = findViewById(R.id.cbTravelling)
        cbMusic = findViewById(R.id.cbMusic)

        setupLocationSpinner()
        setupRealTimeValidation()
        setupButtonActions()
    }

    private fun setupLocationSpinner() {
        val locations = arrayOf(
            "- Select Location -",
            "Jakarta",
            "Bandung",
            "Surabaya",
            "Yogyakarta",
            "Bali"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            locations
        )

        spinnerLocation.adapter = adapter
    }

    private fun setupRealTimeValidation() {
        etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                validateEmail()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                validatePassword()
                validateConfirmPassword()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        etConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                validateConfirmPassword()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun setupButtonActions() {
        btnGetStarted.setOnLongClickListener {
            Toast.makeText(this, "Meow! Tombol ditekan lama (Long Press)", Toast.LENGTH_SHORT).show()
            true
        }

        btnGetStarted.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val name = etName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val pass = etPassword.text.toString()
        val confirm = etConfirmPassword.text.toString()
        val location = spinnerLocation.selectedItem.toString()

        val selectedHobbiesCount = getSelectedHobbiesCount()
        val genderId = rgGender.checkedRadioButtonId

        val isEmailValid = validateEmail()
        val isPasswordValid = validatePassword()
        val isConfirmPasswordValid = validateConfirmPassword()

        when {
            name.isEmpty() || email.isEmpty() || pass.isEmpty() || confirm.isEmpty() -> {
                Toast.makeText(this, "Semua data wajib diisi!", Toast.LENGTH_SHORT).show()
            }

            !isEmailValid || !isPasswordValid || !isConfirmPasswordValid -> {
                Toast.makeText(this, "Tolong perbaiki data yang merah!", Toast.LENGTH_SHORT).show()
            }

            genderId == -1 -> {
                Toast.makeText(this, "Pilih jenis kelamin dulu!", Toast.LENGTH_SHORT).show()
            }

            selectedHobbiesCount < 3 -> {
                Toast.makeText(this, "Pilih minimal 3 hobi favoritmu!", Toast.LENGTH_SHORT).show()
            }

            spinnerLocation.selectedItemPosition == 0 -> {
                Toast.makeText(this, "Pilih lokasi kamu dulu ya!", Toast.LENGTH_SHORT).show()
            }

            else -> {
                showConfirmDialog(name, location, email, pass)
            }
        }
    }

    private fun validateEmail(): Boolean {
        val email = etEmail.text.toString().trim()

        return when {
            email.isEmpty() -> {
                etEmail.error = "Email wajib diisi!"
                false
            }

            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                etEmail.error = "Format email tidak valid!"
                false
            }

            else -> {
                etEmail.error = null
                true
            }
        }
    }

    private fun validatePassword(): Boolean {
        val pass = etPassword.text.toString()

        return when {
            pass.isEmpty() -> {
                etPassword.error = "Password wajib diisi!"
                false
            }

            pass.length < 8 -> {
                etPassword.error = "Minimal 8 karakter!"
                false
            }

            !pass.any { it.isUpperCase() } -> {
                etPassword.error = "Harus ada 1 huruf BESAR!"
                false
            }

            !pass.any { it.isDigit() } -> {
                etPassword.error = "Harus ada 1 angka!"
                false
            }

            !pass.any { !it.isLetterOrDigit() } -> {
                etPassword.error = "Tambahkan 1 simbol (misal: @, #)"
                false
            }

            else -> {
                etPassword.error = null
                true
            }
        }
    }

    private fun validateConfirmPassword(): Boolean {
        val pass = etPassword.text.toString()
        val confirm = etConfirmPassword.text.toString()

        return when {
            confirm.isEmpty() -> {
                etConfirmPassword.error = "Konfirmasi password wajib diisi!"
                false
            }

            confirm != pass -> {
                etConfirmPassword.error = "Password tidak cocok!"
                false
            }

            else -> {
                etConfirmPassword.error = null
                true
            }
        }
    }

    private fun getSelectedHobbiesCount(): Int {
        val checkboxes = listOf(
            cbCoding,
            cbSwimming,
            cbReading,
            cbGaming,
            cbTravelling,
            cbMusic
        )

        return checkboxes.count { it.isChecked }
    }

    private fun showConfirmDialog(
        name: String,
        location: String,
        email: String,
        pass: String
    ) {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Pendaftaran")
            .setMessage("Halo $name dari $location, apakah data kamu sudah benar?")
            .setPositiveButton("Ya, Simpan") { _, _ ->
                saveNewAccountAndOpenLogin(name, email, pass)
            }
            .setNegativeButton("Cek Lagi", null)
            .show()
    }

    private fun saveNewAccountAndOpenLogin(
        name: String,
        email: String,
        pass: String
    ) {
        ProfileHelper.saveNewUserAccount(
            context = this,
            name = name,
            email = email.trim(),
            password = pass
        )

        getSharedPreferences("DataUser", MODE_PRIVATE)
            .edit()
            .putString("name_save", name)
            .putString("email_save", email.trim())
            .putString("pass_save", pass)
            .putString("password_save", pass)
            .putBoolean("is_logged_in", false)
            .apply()

        Toast.makeText(this, "Registrasi Berhasil! Silakan login.", Toast.LENGTH_LONG).show()

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}