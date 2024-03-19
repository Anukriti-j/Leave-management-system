package com.example.minor//package com.example.minor
import android.widget.AdapterView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.minor.databinding.ActivityRegister1Binding
import com.google.firebase.auth.FirebaseAuth
import android.util.Patterns
import com.example.minor.Models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore


class RegisterActivity1 : AppCompatActivity() {
    val binding by lazy {
        ActivityRegister1Binding.inflate(layoutInflater)
    }
    lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register1)
       user=User()

        val departmentNames = arrayOf("Select department", "CSE", "IT", "EE","EC")

        // Create an ArrayAdapter using the departmentNames array and a default spinner layout
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, departmentNames)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Find the spinner view
        val spinner: Spinner = findViewById(R.id.spinner2)

        // Apply the adapter to the spinner
        spinner.adapter = adapter
//        spinner.contentDescription = "Select department from the list"

        // Set an item selected listener on the spinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                // Get the selected item from the spinner
                val selectedDepartment = departmentNames[position]

                // Do something with the selected department
                // For example, display it in a toast message
                showToast("Selected Department: $selectedDepartment")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do something when nothing is selected
            }
        }
    }

    // Function to display a toast message
    private fun showToast(message: String) {
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
        binding.button3.setOnClickListener{
            val password = binding.password.editText.toString()
            val email = binding.email.editText.toString()
            val name= binding.name.editText.toString()
            val re_password= binding.rePassword.editText.toString()
            val department = binding.spinner2.selectedItem.toString()

            if (password.isEmpty() || email.isEmpty() || name.isEmpty() || re_password.isEmpty() || department.isEmpty()) {
                Toast.makeText(this@RegisterActivity1, "Please fill all the information", Toast.LENGTH_SHORT).show()
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(this@RegisterActivity1, "Enter valid E-mail address", Toast.LENGTH_SHORT).show()
            }
            if (password.isBlank() || password.length < 6) {
                Toast.makeText(this@RegisterActivity1, "Password is weak", Toast.LENGTH_SHORT).show()

            }
            if (password != re_password) {
                Toast.makeText(this@RegisterActivity1, "Password does not match, re-enter it!", Toast.LENGTH_SHORT).show()
            }
            else{
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.email?.editText.toString(),
                    binding.password?.editText.toString()
                ).addOnCompleteListener {
                    result->
                    if(result.isSuccessful){
                        user.name=binding.name.editText.toString()
                        user.email=binding.email.editText.toString()
                        user.password= binding.password.editText.toString()
                        user.department= binding.spinner2.selectedItem.toString()
                        user.re_password=binding.rePassword.editText.toString()
                        Firebase.firestore.collection("user").document(Firebase.auth.currentUser!!.uid).set(user)
                            .addOnSuccessListener {
                                Toast.makeText(this@RegisterActivity1, "Just few more steps to go", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
            }

        }








    }
}