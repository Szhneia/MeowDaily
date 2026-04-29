package com.example.loginregist

import android.content.Intent
import android.graphics.Outline
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class SeminarListActivity : AppCompatActivity() {

    private lateinit var btnProfileTop: FrameLayout

    private lateinit var btnJoin1: Button
    private lateinit var btnJoin2: Button
    private lateinit var btnJoin3: Button
    private lateinit var btnJoin4: Button
    private lateinit var btnJoin5: Button

    private lateinit var navHome: ImageButton
    private lateinit var navForm: ImageButton
    private lateinit var navHistory: ImageButton
    private lateinit var navProfile: ImageButton

    private lateinit var etSearchSeminar: EditText
    private lateinit var tvSearchEmpty: TextView

    private lateinit var cardSeminar1: View
    private lateinit var cardSeminar2: View
    private lateinit var cardSeminar3: View
    private lateinit var cardSeminar4: View
    private lateinit var cardSeminar5: View

    private val sharedPrefName = "PROFILE_DATA"

    private val seminar1 = "The Origin of Cats"
    private val seminar2 = "Cats in Ancient Egypt"
    private val seminar3 = "Cats and Human Civilization"
    private val seminar4 = "Cat History in Modern Life"
    private val seminar5 = "From Wild Cats to House Cats"

    private fun Int.dp(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seminar_list)

        btnProfileTop = findViewById(R.id.btnProfileTop)

        btnJoin1 = findViewById(R.id.btnJoin1)
        btnJoin2 = findViewById(R.id.btnJoin2)
        btnJoin3 = findViewById(R.id.btnJoin3)
        btnJoin4 = findViewById(R.id.btnJoin4)
        btnJoin5 = findViewById(R.id.btnJoin5)

        navHome = findViewById(R.id.navHome)
        navForm = findViewById(R.id.navForm)
        navHistory = findViewById(R.id.navHistory)
        navProfile = findViewById(R.id.navProfile)

        etSearchSeminar = findViewById(R.id.etSearchSeminar)
        tvSearchEmpty = findViewById(R.id.tvSearchEmpty)

        cardSeminar1 = findViewById(R.id.cardSeminar1)
        cardSeminar2 = findViewById(R.id.cardSeminar2)
        cardSeminar3 = findViewById(R.id.cardSeminar3)
        cardSeminar4 = findViewById(R.id.cardSeminar4)
        cardSeminar5 = findViewById(R.id.cardSeminar5)

        enableClickableViews()
        loadProfileData()
        setupTopProfileButton()
        setupJoinButtons()
        setupNavbar()
        setupSearch()

        resetNavbarProfileIcon()
        setActiveNav(navForm)
        filterSeminarCards("")
    }

    override fun onResume() {
        super.onResume()

        enableClickableViews()
        loadProfileData()
        resetNavbarProfileIcon()
        setActiveNav(navForm)

        filterSeminarCards(etSearchSeminar.text.toString())
    }

    private fun enableClickableViews() {
        btnProfileTop.isEnabled = true
        btnProfileTop.isClickable = true
        btnProfileTop.isFocusable = false
        btnProfileTop.bringToFront()
        btnProfileTop.elevation = 20f

        val navButtons = listOf(navHome, navForm, navHistory, navProfile)

        navButtons.forEach { button ->
            button.visibility = View.VISIBLE
            button.isEnabled = true
            button.isClickable = true
            button.isFocusable = false
            button.bringToFront()
            button.elevation = 25f
        }

        val navParent = navProfile.parent as? View
        navParent?.bringToFront()
        navParent?.elevation = 30f
        navParent?.requestLayout()
        navParent?.invalidate()

        navProfile.bringToFront()
        navProfile.elevation = 35f
    }

    private fun loadProfileData() {
        loadTopProfileImage()
    }

    private fun loadTopProfileImage() {
        val topProfileImage = findFirstImageView(btnProfileTop)
        val imageUri = getSavedProfileImage()

        topProfileImage?.let { imageView ->
            makeImageCircular(imageView)

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
    }

    private fun makeImageCircular(imageView: ImageView) {
        imageView.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setOval(0, 0, view.width, view.height)
            }
        }

        imageView.clipToOutline = true
    }

    private fun resetNavbarProfileIcon() {
        navProfile.visibility = View.VISIBLE
        navProfile.isEnabled = true
        navProfile.isClickable = true
        navProfile.isFocusable = false

        navProfile.clipToOutline = false
        navProfile.clearColorFilter()
        navProfile.imageTintList = null
        navProfile.imageAlpha = 255

        navProfile.setPadding(8.dp(), 8.dp(), 8.dp(), 8.dp())
        navProfile.scaleType = ImageView.ScaleType.CENTER_INSIDE
        navProfile.setImageResource(R.drawable.ic_nav_profile)
        navProfile.setColorFilter(ContextCompat.getColor(this, android.R.color.white))

        navProfile.bringToFront()
        navProfile.elevation = 20f
        navProfile.invalidate()
    }

    private fun findFirstImageView(view: View): ImageView? {
        if (view is ImageView) {
            return view
        }

        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val foundImageView = findFirstImageView(view.getChildAt(i))
                if (foundImageView != null) {
                    return foundImageView
                }
            }
        }

        return null
    }

    private fun getSavedProfileImage(): String? {
        val profilePref = getSharedPreferences(sharedPrefName, MODE_PRIVATE)
        return profilePref.getString("PROFILE_IMAGE", null)
    }

    private fun setupTopProfileButton() {
        btnProfileTop.setOnClickListener {
            openProfilePage()
        }

        btnProfileTop.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                openProfilePage()
                true
            } else {
                false
            }
        }
    }

    private fun setupJoinButtons() {
        btnJoin1.setOnClickListener {
            openForm(seminar1)
        }

        btnJoin2.setOnClickListener {
            openForm(seminar2)
        }

        btnJoin3.setOnClickListener {
            openForm(seminar3)
        }

        btnJoin4.setOnClickListener {
            openForm(seminar4)
        }

        btnJoin5.setOnClickListener {
            openForm(seminar5)
        }
    }

    private fun setupSearch() {
        etSearchSeminar.addTextChangedListener(object : TextWatcher {
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
                filterSeminarCards(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun filterSeminarCards(rawQuery: String) {
        val query = rawQuery.trim().lowercase()

        val seminar1Text =
            "the origin of cats learn how cats first appeared and became part of human history"

        val seminar2Text =
            "cats in ancient egypt explore why cats were respected and protected in ancient egyptian culture"

        val seminar3Text =
            "cats and human civilization learn how cats became part of human life and culture"

        val seminar4Text =
            "cat history in modern life discover the role of cats in today’s daily life"

        val seminar5Text =
            "from wild cats to house cats discover how wild cats slowly became the pets we know today"

        if (query.isEmpty()) {
            cardSeminar1.visibility = View.VISIBLE
            cardSeminar2.visibility = View.VISIBLE
            cardSeminar3.visibility = View.VISIBLE
            cardSeminar4.visibility = View.VISIBLE
            cardSeminar5.visibility = View.VISIBLE
            tvSearchEmpty.visibility = View.GONE
            return
        }

        val showSeminar1 = seminar1Text.contains(query)
        val showSeminar2 = seminar2Text.contains(query)
        val showSeminar3 = seminar3Text.contains(query)
        val showSeminar4 = seminar4Text.contains(query)
        val showSeminar5 = seminar5Text.contains(query)

        cardSeminar1.visibility = if (showSeminar1) View.VISIBLE else View.GONE
        cardSeminar2.visibility = if (showSeminar2) View.VISIBLE else View.GONE
        cardSeminar3.visibility = if (showSeminar3) View.VISIBLE else View.GONE
        cardSeminar4.visibility = if (showSeminar4) View.VISIBLE else View.GONE
        cardSeminar5.visibility = if (showSeminar5) View.VISIBLE else View.GONE

        val hasResult = showSeminar1 ||
                showSeminar2 ||
                showSeminar3 ||
                showSeminar4 ||
                showSeminar5

        tvSearchEmpty.visibility = if (hasResult) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun openForm(seminarTitle: String) {
        val intent = Intent(this, SeminarFormActivity::class.java)
        intent.putExtra("SELECTED_SEMINAR", seminarTitle)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    private fun setupNavbar() {
        navHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            overridePendingTransition(0, 0)
        }

        navForm.setOnClickListener {
            setActiveNav(navForm)
        }

        navHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
            overridePendingTransition(0, 0)
        }

        navProfile.setOnClickListener {
            openProfilePage()
        }

        navProfile.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                openProfilePage()
                true
            } else {
                false
            }
        }
    }

    private fun openProfilePage() {
        startActivity(Intent(this, ProfileActivity::class.java))
        overridePendingTransition(0, 0)
    }

    private fun setActiveNav(activeNav: ImageButton) {
        val navButtons = listOf(navHome, navForm, navHistory, navProfile)

        navButtons.forEach {
            it.visibility = View.VISIBLE
            it.alpha = 0.55f
            it.scaleX = 1.0f
            it.scaleY = 1.0f
        }

        activeNav.alpha = 1.0f
        activeNav.scaleX = 1.25f
        activeNav.scaleY = 1.25f
    }
}