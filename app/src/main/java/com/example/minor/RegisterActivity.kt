package com.example.minor

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.minor.databinding.ActivityRegisterBinding
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*




class RegisterActivity : AppCompatActivity() {
    val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginbutton.setOnClickListener {
            val password = binding.Password.EditText?.text.toString()
            val email = binding.Email.EditText?.text.toString()

            if (password.isNullOrEmpty() || email.isNullOrEmpty()) {
                Toast.makeText(this@RegisterActivity, "Please fill all the information", Toast.LENGTH_SHORT).show()
            } else {
                // Add your logic for handling non-empty password and email
            }
        }
    }
}