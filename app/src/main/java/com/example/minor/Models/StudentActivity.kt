package com.example.minor.com.example.minor.Models
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.minor.R
import com.google.firebase.auth.FirebaseAuth

class StudentActivity: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_student)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        displaying current username in home activity

        // In Home Activity

// Retrieve current user's ID
//        val userId = FirebaseAuth.getInstance().currentUser?.uid

// Reference to Firestore
//        val db = FirebaseFirestore.getInstance()

// Retrieve user's data from Firestore
//        userId?.let { uid ->
//            val userDocRef = db.collection("users").document(uid)
//            userDocRef.get()
//                .addOnSuccessListener { document ->
//                    if (document != null && document.exists()) {
//                        val userName = document.getString("name")
//                        userName?.let {
//                            val welcomeMessage = "Hey $userName, welcome!"
//                            findViewById<TextView>(R.id.textView2).text = welcomeMessage
//                        }
//                    } else {
//                        // Document does not exist or is null
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    // Handle failures
//                }
//        }


    }

}