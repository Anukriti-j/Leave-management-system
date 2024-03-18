package com.example.minor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.minor.Models.User
import com.example.minor.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import androidx.appcompat.app.AppCompatDelegate
import com.example.minor.RegisterActivity1


class LoginActivity : AppCompatActivity() {
    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    lateinit var user:User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        user=User()


        binding.loginbutton.setOnClickListener {
            val password = binding.Password.text.toString()
            val email = binding.Email.text.toString()

            if (password.isEmpty() || email.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Please fill all the information", Toast.LENGTH_SHORT).show()
            } else {
                // Add your logic for handling non-empty password and email
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.Email?.text.toString(),
                    binding.Password?.text.toString()
                ).addOnCompleteListener {
                    result->
                    if (result.isSuccessful){
                        user.email=binding.Email.text.toString()
                        user.password=binding.Password.text.toString()
                        Firebase.firestore.collection("User").document(Firebase.auth.currentUser!!.uid).set(user)
                            .addOnSuccessListener {
                                Toast.makeText(this@LoginActivity, "Login", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        
                        Toast.makeText(this@LoginActivity,"login Successfully",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this@LoginActivity,result.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.textView6.setOnClickListener {

            startActivity(Intent(this@LoginActivity, RegisterActivity1::class.java))
            finish()
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
}