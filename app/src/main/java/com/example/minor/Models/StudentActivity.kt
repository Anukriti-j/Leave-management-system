package com.example.minor.com.example.minor.Models
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.minor.R
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class StudentActivity: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_student)

        drawerLayout = findViewById(R.id.drawer_layout_student)
        navigationView = findViewById(R.id.dashboard_navigation_view_student)
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            item.isChecked=true
            when (item.itemId) {
                R.id.home -> {
                    Toast.makeText(this@StudentActivity, "Home Selected", Toast.LENGTH_SHORT).show()
                }

                R.id.notes -> {
                    Toast.makeText(this@StudentActivity, "Notes Selected", Toast.LENGTH_SHORT).show()
                }

                R.id.profile -> {
                    Toast.makeText(this@StudentActivity, "Profile Selected", Toast.LENGTH_SHORT).show()
                }

                R.id.setting -> {
                    Toast.makeText(this@StudentActivity, "Setting Selected", Toast.LENGTH_SHORT).show()
                }

                R.id.logout -> {
                    Toast.makeText(this@StudentActivity, "Logout Selected", Toast.LENGTH_SHORT).show()
                }

                R.id.contact -> {
                    Toast.makeText(this@StudentActivity, "Contact Selected", Toast.LENGTH_SHORT).show()
                }

                R.id.suggestions -> {
                    Toast.makeText(this@StudentActivity, "Suggestion Selected", Toast.LENGTH_SHORT).show()
                }

                R.id.rate -> {
                    Toast.makeText(this@StudentActivity, "Rate Selected", Toast.LENGTH_SHORT).show()
                }
            }
            true
        })

        val dashboardImageView: ImageView = findViewById(R.id.dashboard_icon)

        // Set OnClickListener for the dashboard ImageView to open the navigation drawer
        dashboardImageView.setOnClickListener {
            drawerLayout.openDrawer(navigationView)
        }
        // Find the CardView for leave
        val leaveCardView: CardView = findViewById(R.id.cardviewleavestudent)

        // Set OnClickListener for the leave CardView
        leaveCardView.setOnClickListener {
            // Define the intent to start LeaveActivity
            val intent = Intent(this, LeaveActivity2::class.java)
            startActivity(intent)
        }
//         Retrieve current user's ID
        val userId = FirebaseAuth.getInstance().currentUser?.uid

// Reference to Firestore
        val db = FirebaseFirestore.getInstance()

// Retrieve user's data from Firestore
        userId?.let { uid ->
            val userDocRef = db.collection("User").document(uid)
            userDocRef.get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val userName = document.getString("name")
                        userName?.let {
                            val welcomeMessage = "Hey $userName, welcome!"
                            findViewById<TextView>(R.id.textView2).text = welcomeMessage
                        }
                    } else {
                        // Document does not exist or is null
                    }
                }
                .addOnFailureListener { exception ->
                    // Handle failures
                }
        }


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Toggle the drawer when the hamburger menu icon is clicked
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}