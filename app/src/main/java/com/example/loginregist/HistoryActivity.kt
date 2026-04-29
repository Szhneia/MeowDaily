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
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class HistoryActivity : AppCompatActivity() {

    private lateinit var btnCurrent: Button
    private lateinit var btnPrevious: Button
    private lateinit var listContainer: LinearLayout
    private lateinit var tvEmpty: TextView
    private lateinit var etSearchHistory: EditText

    private lateinit var btnProfileTop: FrameLayout
    private lateinit var imgProfileTop: ImageView

    private lateinit var navHome: ImageButton
    private lateinit var navForm: ImageButton
    private lateinit var navHistory: ImageButton
    private lateinit var navProfile: ImageButton

    private var isCurrentMode = true
    private var currentDisplayedList = listOf<String>()

    private val sharedPrefName = "PROFILE_DATA"

    private val seminarDescriptions = mapOf(
        "The Origin of Cats" to "Learn how cats first appeared and became part of human history.",
        "Cats in Ancient Egypt" to "Explore why cats were respected and protected in Ancient Egyptian culture.",
        "Cats and Human Civilization" to "Learn how cats became part of human life and culture.",
        "Cat History in Modern Life" to "Discover the role of cats in today’s daily life.",
        "From Wild Cats to House Cats" to "Discover how wild cats slowly became the pets we know today.",
        "Cat Evolution and Human Culture" to "Discover how wild cats slowly became the pets we know today."
    )

    private val seminarDates = mapOf(
        "The Origin of Cats" to "Wed, 31st June",
        "Cats in Ancient Egypt" to "Fri, 6th Nov",
        "Cats and Human Civilization" to "Sun, 11th Jan",
        "Cat History in Modern Life" to "Tue, 21st May",
        "From Wild Cats to House Cats" to "Tue, 29th Feb",
        "Cat Evolution and Human Culture" to "Tue, 29th Feb"
    )

    private fun Int.dp(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        btnCurrent = findViewById(R.id.btnCurrent)
        btnPrevious = findViewById(R.id.btnPrevious)
        listContainer = findViewById(R.id.listContainer)
        tvEmpty = findViewById(R.id.tvEmpty)
        etSearchHistory = findViewById(R.id.etSearchHistory)

        btnProfileTop = findViewById(R.id.btnProfileTop)
        imgProfileTop = findViewById(R.id.imgProfileTop)

        navHome = findViewById(R.id.navHome)
        navForm = findViewById(R.id.navForm)
        navHistory = findViewById(R.id.navHistory)
        navProfile = findViewById(R.id.navProfile)

        enableNavbarClick()
        setupTopProfileButton()
        setupButtons()
        setupNavbar()
        setupSearchHistory()

        loadTopProfileImage()
        resetNavbarProfileIcon()
        setActiveNav(navHistory)

        val openTab = intent.getStringExtra("OPEN_TAB")
        if (openTab == "PREVIOUS") {
            isCurrentMode = false
            showPreviousSeminar()
        } else {
            isCurrentMode = true
            showCurrentSeminar()
        }
    }

    override fun onResume() {
        super.onResume()

        enableNavbarClick()
        loadTopProfileImage()
        resetNavbarProfileIcon()
        setActiveNav(navHistory)

        if (isCurrentMode) {
            showCurrentSeminar()
        } else {
            showPreviousSeminar()
        }
    }

    private fun setupTopProfileButton() {
        btnProfileTop.visibility = View.VISIBLE
        btnProfileTop.isEnabled = true
        btnProfileTop.isClickable = true
        btnProfileTop.isFocusable = false
        btnProfileTop.bringToFront()
        btnProfileTop.elevation = 30f

        btnProfileTop.setOnClickListener {
            openProfilePage()
        }

        imgProfileTop.setOnClickListener {
            openProfilePage()
        }
    }

    private fun loadTopProfileImage() {
        val profilePref = getSharedPreferences(sharedPrefName, MODE_PRIVATE)
        val imageUri = profilePref.getString("PROFILE_IMAGE", null)

        makeImageCircular(imgProfileTop)

        if (!imageUri.isNullOrEmpty()) {
            imgProfileTop.clearColorFilter()
            imgProfileTop.imageTintList = null
            imgProfileTop.setPadding(0, 0, 0, 0)
            imgProfileTop.scaleType = ImageView.ScaleType.CENTER_CROP
            imgProfileTop.setImageURI(Uri.parse(imageUri))
        } else {
            imgProfileTop.imageTintList = null
            imgProfileTop.setPadding(8.dp(), 8.dp(), 8.dp(), 8.dp())
            imgProfileTop.scaleType = ImageView.ScaleType.CENTER_INSIDE
            imgProfileTop.setImageResource(R.drawable.ic_nav_profile)
            imgProfileTop.setColorFilter(ContextCompat.getColor(this, android.R.color.black))
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

    private fun enableNavbarClick() {
        val navButtons = listOf(navHome, navForm, navHistory, navProfile)

        navButtons.forEach { button ->
            button.visibility = View.VISIBLE
            button.isEnabled = true
            button.isClickable = true
            button.isFocusable = false
            button.bringToFront()
            button.elevation = 20f
        }

        val navParent = navProfile.parent as? View
        navParent?.bringToFront()
        navParent?.elevation = 25f
        navParent?.requestLayout()
        navParent?.invalidate()

        navProfile.bringToFront()
        navProfile.elevation = 30f
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

    private fun setupButtons() {
        btnCurrent.setOnClickListener {
            isCurrentMode = true
            etSearchHistory.text.clear()
            showCurrentSeminar()
        }

        btnPrevious.setOnClickListener {
            isCurrentMode = false
            etSearchHistory.text.clear()
            showPreviousSeminar()
        }
    }

    private fun setupSearchHistory() {
        etSearchHistory.addTextChangedListener(object : TextWatcher {
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
                filterHistory(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun filterHistory(queryText: String) {
        val query = queryText.trim().lowercase()

        listContainer.removeAllViews()

        val filteredList = if (query.isEmpty()) {
            currentDisplayedList
        } else {
            currentDisplayedList.filter { seminar ->
                val title = seminar.lowercase()
                val desc = seminarDescriptions[seminar]?.lowercase() ?: ""
                val date = seminarDates[seminar]?.lowercase() ?: ""

                title.contains(query) || desc.contains(query) || date.contains(query)
            }
        }

        if (filteredList.isEmpty()) {
            tvEmpty.visibility = View.VISIBLE
            tvEmpty.text = if (query.isEmpty()) {
                if (isCurrentMode) "No current seminar." else "No previous seminar yet."
            } else {
                "History not found."
            }
        } else {
            tvEmpty.visibility = View.GONE

            for (seminar in filteredList) {
                val buttonText = if (isCurrentMode) "See Details" else "View History"
                addSeminarCard(seminar, buttonText)
            }
        }
    }

    private fun showCurrentSeminar() {
        setTabStyle(currentSelected = true)

        val pref = getSharedPreferences("SEMINAR_DATA", MODE_PRIVATE)
        val currentSeminar = pref.getString("CURRENT_SEMINAR", "") ?: ""

        currentDisplayedList = if (currentSeminar.isEmpty()) {
            emptyList()
        } else {
            listOf(currentSeminar)
        }

        filterHistory(etSearchHistory.text.toString())
    }

    private fun showPreviousSeminar() {
        setTabStyle(currentSelected = false)

        val pref = getSharedPreferences("SEMINAR_DATA", MODE_PRIVATE)
        val currentSeminar = pref.getString("CURRENT_SEMINAR", "") ?: ""
        val history = pref.getString("SEMINAR_HISTORY", "") ?: ""

        currentDisplayedList = history
            .split("|||")
            .filter { it.isNotBlank() && it != currentSeminar }

        filterHistory(etSearchHistory.text.toString())
    }

    private fun showSeminarDetailDialog(seminarTitle: String) {
        val description = seminarDescriptions[seminarTitle] ?: "Cat seminar history."
        val date = seminarDates[seminarTitle] ?: "Mon, 18th May"

        AlertDialog.Builder(this)
            .setTitle(seminarTitle)
            .setMessage(
                """
                Date: $date
                
                Description:
                $description
                """.trimIndent()
            )
            .setPositiveButton("OK", null)
            .show()
    }

    private fun setTabStyle(currentSelected: Boolean) {
        if (currentSelected) {
            btnCurrent.background = ContextCompat.getDrawable(this, R.drawable.bg_meow_button_black)
            btnCurrent.setTextColor(ContextCompat.getColor(this, R.color.meow_white))

            btnPrevious.background = ContextCompat.getDrawable(this, R.drawable.bg_meow_button_outline)
            btnPrevious.setTextColor(ContextCompat.getColor(this, R.color.meow_black))
        } else {
            btnCurrent.background = ContextCompat.getDrawable(this, R.drawable.bg_meow_button_outline)
            btnCurrent.setTextColor(ContextCompat.getColor(this, R.color.meow_black))

            btnPrevious.background = ContextCompat.getDrawable(this, R.drawable.bg_meow_button_black)
            btnPrevious.setTextColor(ContextCompat.getColor(this, R.color.meow_white))
        }
    }

    private fun addSeminarCard(seminarTitle: String, buttonText: String) {
        val layoutId = getSeminarLayoutId(seminarTitle)
        val card = layoutInflater.inflate(layoutId, listContainer, false)

        val button = findFirstButton(card)
        button?.text = buttonText
        button?.isAllCaps = false

        button?.setOnClickListener {
            showSeminarDetailDialog(seminarTitle)
        }

        card.setOnClickListener {
            showSeminarDetailDialog(seminarTitle)
        }

        listContainer.addView(card)
    }

    private fun getSeminarLayoutId(seminarTitle: String): Int {
        return when (seminarTitle) {
            "The Origin of Cats" -> R.layout.item_home_seminar_1
            "Cats in Ancient Egypt" -> R.layout.item_home_seminar_2
            "Cats and Human Civilization" -> R.layout.item_home_seminar_3
            "Cat History in Modern Life" -> R.layout.item_home_seminar_4
            "From Wild Cats to House Cats" -> R.layout.item_home_seminar_5
            "Cat Evolution and Human Culture" -> R.layout.item_home_seminar_5
            else -> R.layout.item_home_seminar_4
        }
    }

    private fun findFirstButton(view: View): Button? {
        if (view is Button) {
            return view
        }

        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val foundButton = findFirstButton(view.getChildAt(i))
                if (foundButton != null) {
                    return foundButton
                }
            }
        }

        return null
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
            setActiveNav(navHistory)
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