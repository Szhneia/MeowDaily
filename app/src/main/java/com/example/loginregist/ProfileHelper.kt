package com.example.loginregist

import android.content.Context
import android.net.Uri
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

object ProfileHelper {

    private const val PROFILE_PREF = "PROFILE_DATA"
    private const val USER_PREF = "DataUser"
    private const val REGISTRATION_PREF = "REGISTRATION_DATA"
    private const val SEMINAR_PREF = "SEMINAR_DATA"

    private const val KEY_PROFILE_NAME = "PROFILE_NAME"
    private const val KEY_PROFILE_BIO = "PROFILE_BIO"
    private const val KEY_PROFILE_IMAGE = "PROFILE_IMAGE"

    private const val KEY_USER_NAME = "name_save"
    private const val KEY_USER_EMAIL = "email_save"
    private const val KEY_USER_PASSWORD = "password_save"
    private const val KEY_USER_PASSWORD_OLD = "pass_save"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"

    private const val DEFAULT_NAME = "Meow User"
    private const val DEFAULT_EMAIL = "meowdaily@gmail.com"
    private const val DEFAULT_BIO = "Pecinta kucing dan peserta seminar MeowDaily."

    private fun Int.dp(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }

    fun getName(context: Context): String {
        val profilePref = context.getSharedPreferences(PROFILE_PREF, Context.MODE_PRIVATE)
        val userPref = context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE)

        val defaultName = userPref.getString(KEY_USER_NAME, DEFAULT_NAME) ?: DEFAULT_NAME
        return profilePref.getString(KEY_PROFILE_NAME, defaultName) ?: defaultName
    }

    fun getEmail(context: Context): String {
        val userPref = context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE)
        return userPref.getString(KEY_USER_EMAIL, DEFAULT_EMAIL) ?: DEFAULT_EMAIL
    }

    fun getBio(context: Context): String {
        val profilePref = context.getSharedPreferences(PROFILE_PREF, Context.MODE_PRIVATE)
        return profilePref.getString(KEY_PROFILE_BIO, DEFAULT_BIO) ?: DEFAULT_BIO
    }

    fun getImageUri(context: Context): String? {
        val profilePref = context.getSharedPreferences(PROFILE_PREF, Context.MODE_PRIVATE)
        return profilePref.getString(KEY_PROFILE_IMAGE, "")
    }

    fun isLoggedIn(context: Context): Boolean {
        val userPref = context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE)
        return userPref.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun setLoggedIn(context: Context, loggedIn: Boolean) {
        context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_IS_LOGGED_IN, loggedIn)
            .apply()
    }

    fun logout(context: Context) {
        setLoggedIn(context, false)
    }

    fun clearOldUserData(context: Context) {
        context.getSharedPreferences(PROFILE_PREF, Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()

        context.getSharedPreferences(REGISTRATION_PREF, Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()

        context.getSharedPreferences(SEMINAR_PREF, Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()
    }

    fun saveNewUserAccount(
        context: Context,
        name: String,
        email: String,
        password: String
    ) {
        clearOldUserData(context)

        context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE)
            .edit()
            .clear()
            .putString(KEY_USER_NAME, name)
            .putString(KEY_USER_EMAIL, email)
            .putString(KEY_USER_PASSWORD, password)
            .putString(KEY_USER_PASSWORD_OLD, password)
            .putBoolean(KEY_IS_LOGGED_IN, false)
            .apply()

        context.getSharedPreferences(PROFILE_PREF, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_PROFILE_NAME, name)
            .putString(KEY_PROFILE_BIO, DEFAULT_BIO)
            .putString(KEY_PROFILE_IMAGE, "")
            .apply()
    }

    fun applyProfileImage(context: Context, imageView: ImageView) {
        val imageUri = getImageUri(context)

        imageView.clipToOutline = true

        if (!imageUri.isNullOrEmpty()) {
            imageView.clearColorFilter()
            imageView.imageTintList = null
            imageView.setPadding(0, 0, 0, 0)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setImageURI(Uri.parse(imageUri))
        } else {
            imageView.imageTintList = null
            imageView.setPadding(8.dp(context), 8.dp(context), 8.dp(context), 8.dp(context))
            imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            imageView.setImageResource(R.drawable.ic_nav_profile)
            imageView.setColorFilter(ContextCompat.getColor(context, android.R.color.black))
        }
    }

    fun applyProfileButtonImage(context: Context, imageButton: ImageButton) {
        val imageUri = getImageUri(context)

        imageButton.clipToOutline = true

        if (!imageUri.isNullOrEmpty()) {
            imageButton.clearColorFilter()
            imageButton.imageTintList = null
            imageButton.setPadding(0, 0, 0, 0)
            imageButton.scaleType = ImageView.ScaleType.CENTER_CROP
            imageButton.setImageURI(Uri.parse(imageUri))
        } else {
            imageButton.imageTintList = null
            imageButton.setPadding(8.dp(context), 8.dp(context), 8.dp(context), 8.dp(context))
            imageButton.scaleType = ImageView.ScaleType.CENTER_INSIDE
            imageButton.setImageResource(R.drawable.ic_nav_profile)
            imageButton.setColorFilter(ContextCompat.getColor(context, android.R.color.black))
        }
    }

    fun resetNavbarProfileIcon(context: Context, imageButton: ImageButton) {
        imageButton.clipToOutline = false
        imageButton.clearColorFilter()
        imageButton.imageTintList = null
        imageButton.imageAlpha = 255

        imageButton.setPadding(8.dp(context), 8.dp(context), 8.dp(context), 8.dp(context))
        imageButton.scaleType = ImageView.ScaleType.CENTER_INSIDE
        imageButton.setImageResource(R.drawable.ic_nav_profile)
        imageButton.setColorFilter(ContextCompat.getColor(context, android.R.color.white))
    }

    fun applyProfileText(
        context: Context,
        tvName: TextView? = null,
        tvEmail: TextView? = null,
        tvBio: TextView? = null
    ) {
        tvName?.text = getName(context)
        tvEmail?.text = "Email: ${getEmail(context)}"
        tvBio?.text = getBio(context)
    }
}