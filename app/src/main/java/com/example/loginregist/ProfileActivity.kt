package com.example.loginregist

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class ProfileActivity : AppCompatActivity() {

    private lateinit var imgProfile: ImageView
    private lateinit var tvProfileName: TextView
    private lateinit var tvProfileEmail: TextView
    private lateinit var tvProfileBio: TextView
    private lateinit var btnEditProfile: Button
    private lateinit var btnLogout: Button

    private lateinit var navHome: ImageButton
    private lateinit var navForm: ImageButton
    private lateinit var navHistory: ImageButton
    private lateinit var navProfile: ImageButton

    private val sharedPrefName = "PROFILE_DATA"

    private var selectedDialogImageUri: Uri? = null
    private var activeDialogImageView: ImageView? = null

    private fun Int.dp(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }

    private fun setProfileImage(imageView: ImageView, imageUri: String?) {
        imageView.clipToOutline = true

        if (!imageUri.isNullOrEmpty()) {
            imageView.clearColorFilter()
            imageView.imageTintList = null
            imageView.setPadding(0, 0, 0, 0)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setImageURI(Uri.parse(imageUri))
        } else {
            imageView.imageTintList = null
            imageView.setPadding(8.dp(), 8.dp(), 8.dp(), 8.dp())
            imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            imageView.setImageResource(R.drawable.ic_nav_profile)
            imageView.setColorFilter(ContextCompat.getColor(this, android.R.color.black))
        }
    }

    private val dialogImagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri: Uri? = result.data?.data

                if (imageUri != null) {
                    try {
                        contentResolver.takePersistableUriPermission(
                            imageUri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                    } catch (_: Exception) {
                    }

                    selectedDialogImageUri = imageUri

                    activeDialogImageView?.let { imageView ->
                        setProfileImage(imageView, imageUri.toString())
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        imgProfile = findViewById(R.id.imgProfile)
        tvProfileName = findViewById(R.id.tvProfileName)
        tvProfileEmail = findViewById(R.id.tvProfileEmail)
        tvProfileBio = findViewById(R.id.tvProfileBio)
        btnEditProfile = findViewById(R.id.btnEditProfile)
        btnLogout = findViewById(R.id.btnLogout)

        navHome = findViewById(R.id.navHome)
        navForm = findViewById(R.id.navForm)
        navHistory = findViewById(R.id.navHistory)
        navProfile = findViewById(R.id.navProfile)

        loadProfileData()
        setupNavbar()
        setActiveNav(navProfile)

        btnEditProfile.setOnClickListener {
            showEditProfileDialog()
        }

        btnLogout.setOnClickListener {
            logout()
        }
    }

    override fun onResume() {
        super.onResume()

        loadProfileData()
        setActiveNav(navProfile)
    }

    private fun loadProfileData() {
        ProfileHelper.applyProfileText(
            context = this,
            tvName = tvProfileName,
            tvEmail = tvProfileEmail,
            tvBio = tvProfileBio
        )

        ProfileHelper.applyProfileImage(this, imgProfile)
    }

    private fun showEditProfileDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_profile, null)

        val imgDialogProfile = dialogView.findViewById<ImageView>(R.id.imgDialogProfile)
        val btnDialogChangePhoto = dialogView.findViewById<Button>(R.id.btnDialogChangePhoto)
        val etEditName = dialogView.findViewById<EditText>(R.id.etEditName)
        val etEditBio = dialogView.findViewById<EditText>(R.id.etEditBio)
        val btnCancelEdit = dialogView.findViewById<Button>(R.id.btnCancelEdit)
        val btnSaveEdit = dialogView.findViewById<Button>(R.id.btnSaveEdit)

        val profilePref = getSharedPreferences(sharedPrefName, MODE_PRIVATE)

        val savedName = ProfileHelper.getName(this)
        val savedBio = ProfileHelper.getBio(this)
        val savedImage = ProfileHelper.getImageUri(this)

        selectedDialogImageUri = if (!savedImage.isNullOrEmpty()) Uri.parse(savedImage) else null
        activeDialogImageView = imgDialogProfile

        etEditName.setText(savedName)
        etEditBio.setText(savedBio)

        setProfileImage(imgDialogProfile, savedImage)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        btnDialogChangePhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "image/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            dialogImagePickerLauncher.launch(intent)
        }

        btnCancelEdit.setOnClickListener {
            dialog.dismiss()
        }

        btnSaveEdit.setOnClickListener {
            val newName = etEditName.text.toString().trim()
            val newBio = etEditBio.text.toString().trim()

            if (newName.isEmpty()) {
                Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newBio.isEmpty()) {
                Toast.makeText(this, "Bio tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val finalImageUri = selectedDialogImageUri?.toString() ?: savedImage.orEmpty()

            profilePref.edit()
                .putString("PROFILE_NAME", newName)
                .putString("PROFILE_BIO", newBio)
                .putString("PROFILE_IMAGE", finalImageUri)
                .apply()

            tvProfileName.text = newName
            tvProfileBio.text = newBio
            setProfileImage(imgProfile, finalImageUri)

            Toast.makeText(this, "Profile berhasil diperbarui", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun logout() {
        ProfileHelper.logout(this)

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun setupNavbar() {
        navHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            overridePendingTransition(0, 0)
        }

        navForm.setOnClickListener {
            startActivity(Intent(this, SeminarListActivity::class.java))
            overridePendingTransition(0, 0)
        }

        navHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
            overridePendingTransition(0, 0)
        }

        navProfile.setOnClickListener {
            setActiveNav(navProfile)
        }
    }

    private fun setActiveNav(activeNav: ImageButton) {
        val navButtons = listOf(navHome, navForm, navHistory, navProfile)

        navButtons.forEach {
            it.alpha = 0.55f
            it.scaleX = 1.0f
            it.scaleY = 1.0f
        }

        activeNav.alpha = 1.0f
        activeNav.scaleX = 1.25f
        activeNav.scaleY = 1.25f
    }
}