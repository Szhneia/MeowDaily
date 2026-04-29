package com.example.loginregist

import android.content.Intent
import android.graphics.Outline
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var tvWelcome: TextView
    private lateinit var btnProfileTop: FrameLayout
    private lateinit var imgProfileTop: ImageView
    private lateinit var layoutSearch: LinearLayout

    private lateinit var btnJoin1: Button
    private lateinit var btnJoin2: Button
    private lateinit var btnJoin3: Button

    private var navHome: ImageButton? = null
    private var navForm: ImageButton? = null
    private var navHistory: ImageButton? = null
    private var navProfile: ImageButton? = null

    private val seminar1 = "The Origin of Cats"
    private val seminar2 = "Cats in Ancient Egypt"
    private val seminar3 = "Cats and Human Civilization"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        tvWelcome = findViewById(R.id.tvWelcome)
        btnProfileTop = findViewById(R.id.btnProfileTop)
        imgProfileTop = findViewById(R.id.imgProfileTop)
        layoutSearch = findViewById(R.id.layoutSearch)

        btnJoin1 = findViewById(R.id.btnJoin1)
        btnJoin2 = findViewById(R.id.btnJoin2)
        btnJoin3 = findViewById(R.id.btnJoin3)

        navHome = findViewById(R.id.navHome)
        navForm = findViewById(R.id.navForm)
        navHistory = findViewById(R.id.navHistory)
        navProfile = findViewById(R.id.navProfile)

        setupTopProfileButton()
        setupSearchBar()
        setupJoinButtons()
        setupNavbar()

        loadProfileData()
        resetNavbarProfileIcon()
        navHome?.let { setActiveNav(it) }
    }

    override fun onResume() {
        super.onResume()

        loadProfileData()
        resetNavbarProfileIcon()
        navHome?.let { setActiveNav(it) }
    }

    private fun loadProfileData() {
        loadUserName()
        loadTopProfileImage()
    }

    private fun loadUserName() {
        val name = ProfileHelper.getName(this)
        tvWelcome.text = "Welcome Meowie,\n$name!"
    }

    private fun loadTopProfileImage() {
        makeImageCircular(imgProfileTop)
        ProfileHelper.applyProfileImage(this, imgProfileTop)
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
        navProfile?.let { button ->
            button.visibility = View.VISIBLE
            button.isEnabled = true
            button.isClickable = true
            button.isFocusable = false

            ProfileHelper.resetNavbarProfileIcon(this, button)

            button.bringToFront()
            button.elevation = 20f
            button.invalidate()
        }
    }

    private fun setupTopProfileButton() {
        btnProfileTop.setOnClickListener {
            openProfilePage()
        }

        imgProfileTop.setOnClickListener {
            openProfilePage()
        }
    }

    private fun setupSearchBar() {
        layoutSearch.setOnClickListener {
            startActivity(Intent(this, SeminarListActivity::class.java))
            overridePendingTransition(0, 0)
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
    }

    private fun openForm(seminarTitle: String) {
        val intent = Intent(this, SeminarFormActivity::class.java)
        intent.putExtra("SELECTED_SEMINAR", seminarTitle)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    private fun setupNavbar() {
        navHome?.setOnClickListener {
            navHome?.let { button ->
                setActiveNav(button)
            }
        }

        navForm?.setOnClickListener {
            startActivity(Intent(this, SeminarListActivity::class.java))
            overridePendingTransition(0, 0)
        }

        navHistory?.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
            overridePendingTransition(0, 0)
        }

        navProfile?.setOnClickListener {
            openProfilePage()
        }
    }

    private fun openProfilePage() {
        startActivity(Intent(this, ProfileActivity::class.java))
        overridePendingTransition(0, 0)
    }

    private fun setActiveNav(activeNav: ImageButton) {
        val navButtons = listOfNotNull(navHome, navForm, navHistory, navProfile)

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