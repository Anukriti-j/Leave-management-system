package com.example.minor.com.example.minor.Models
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.minor.Models.LeaveActivity
import com.example.minor.R
class FacultyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faculty)

        // Find the CardView for leave
        val leaveCardView: CardView = findViewById(R.id.cardviewleave)

        // Set OnClickListener for the leave CardView
        leaveCardView.setOnClickListener {
            // Define the intent to start LeaveActivity
            val intent = Intent(this, LeaveActivity::class.java)
            startActivity(intent)
        }
    }
}