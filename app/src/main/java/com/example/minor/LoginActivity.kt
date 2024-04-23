package com.example.minor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.minor.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import android.content.Context


import androidx.appcompat.app.AppCompatDelegate
import com.example.minor.com.example.minor.Models.FacultyActivity
import com.example.minor.com.example.minor.Models.StudentActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.firestore


class LoginActivity : AppCompatActivity() {
    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    lateinit var auth: FirebaseAuth
//    override fun onStart() {
//        super.onStart()
//        // Check if user is already logged in
//        val currentUser: FirebaseUser? = auth.currentUser
//        if (currentUser != null) {
//            fetchUserTypeAndRedirect(currentUser.uid)
//            finish()
//        }
//    }
    private fun fetchUserTypeAndRedirect(userId: String) {
        Firebase.firestore.collection("User").document(userId).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val isStudentChecked = document.getBoolean("isStudentChecked") ?: false
                    val isFacultyChecked = document.getBoolean("isFacultyChecked") ?: false
                    val userType = if (isStudentChecked) {
                        "student"
                    } else if (isFacultyChecked) {
                        "faculty"
                    } else {
                        // Handle the case where neither is checked
                        "default" // or any other default value
                    }
                    redirectToUserTypeActivity(this, userType)
                    finish()
                } else {
                    // Document does not exist or is null
                    Toast.makeText(this, "Document does not exist or is null", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                // Handle database read failure
                Toast.makeText(this, "Failed to retrieve user type: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        initialize firebase auth
        auth=FirebaseAuth.getInstance()


        binding.loginbutton.setOnClickListener {
            val email=binding.Email.text.toString()
            val password=binding.Password.text.toString()

            if(email.isEmpty()||password.isEmpty()){
                Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
            }
            else{
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener { task->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
                            loginUser()
                        }
                        else{
                            Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        binding.textView6.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity1::class.java))
            finish()
        }
        binding.forgotpassword.setOnClickListener {
            val email = binding.Email.text.toString().trim()

            if (email.isEmpty()) {
                // Email field is empty
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Send password reset email
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Password reset email sent successfully
                        Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show()
                    } else {
                        // Password reset email sending failed
                        Toast.makeText(this, "Failed to send password reset email", Toast.LENGTH_SHORT).show()
                    }
                }
        }




        val textView5= findViewById<TextView>(R.id.textView5)
        // Determine the current night mode
        val nightMode = AppCompatDelegate.getDefaultNightMode()

        // Set color based on the night mode
        val colorResId = if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            R.color.black
        } else {
            R.color.white
        }

        // Set the text color of myVariableTextView

         textView5.setTextColor(resources.getColor(colorResId,this.theme))
    }
    //    fucntion to redirect to different home activity based on user type

    private fun loginUser() {
        val email = binding.Email.text.toString().trim()
        val password = binding.Password.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        fetchUserTypeAndRedirect(it.uid) // Pass user UID to fetch user type
                    }
                } else {
                    Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun redirectToUserTypeActivity(context: Context,userType: String="student") {
        val intent = when (userType) {
            "student" -> Intent(context, StudentActivity::class.java)
            "faculty" -> Intent(context, FacultyActivity::class.java)
            else -> Intent(context, StudentActivity::class.java) // Default activity for other user types
        }
        context.startActivity(intent)
        finish()
    }
}