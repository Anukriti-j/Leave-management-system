package com.example.minor//package com.example.minor
import android.content.Intent
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
import android.widget.CheckBox
import android.widget.TextView
import android.text.SpannableString
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.Spanned
import android.view.View


class RegisterActivity1 : AppCompatActivity() {
    val binding by lazy {
        ActivityRegister1Binding.inflate(layoutInflater)
    }
    lateinit var auth: FirebaseAuth
    lateinit var user: User
    private var isStudentChecked = false
    private var isFacultyChecked = false
    private lateinit var checkboxStudent: CheckBox
    private lateinit var checkboxFaculty: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        checkboxStudent = findViewById(R.id.student)
        checkboxFaculty = findViewById(R.id.faculty)
        checkboxStudent.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                isStudentChecked = true
                isFacultyChecked = false
                checkboxFaculty.isChecked = false
            } else {
                isStudentChecked = false
            }
        }

        checkboxFaculty.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                isFacultyChecked = true
                isStudentChecked = false
                checkboxStudent.isChecked = false
            } else {
                isFacultyChecked = false
            }
        }

//        firebaseauth initialization
        auth=FirebaseAuth.getInstance()
        user=User()

        binding.registerButton.setOnClickListener{
            val name= binding.name.editText?.text.toString()
            val password= binding.password.editText?.text.toString()
            val re_password= binding.repassword.editText?.text.toString()
            val email=binding.email.editText?.text.toString()
            val department =binding.spinner2.selectedItem.toString()
            var isStudent: Boolean= false
            var isFaculty: Boolean=false
            val emailDomain = email.substringAfter('@')

//             check if any field is blank
            if(email.isEmpty() || name.isEmpty() || password.isEmpty() || re_password.isEmpty() ){
                Toast.makeText(this@RegisterActivity1, "Please fill all the information", Toast.LENGTH_SHORT).show()
            }
            else if(password!=re_password){
                Toast.makeText(this, "Re-entered Password must be same", Toast.LENGTH_SHORT).show()
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(this, "Enter valid email address", Toast.LENGTH_SHORT).show()
            }
            else if (emailDomain != "satiengg.in") {
                // Display an error message to the user
                Toast.makeText(this, "Sign-in with email address from @satiengg.in domain only.", Toast.LENGTH_SHORT).show()
            }
            else if(!isStudentChecked && !isFacultyChecked){
                Toast.makeText(this, "Select user checkbox", Toast.LENGTH_SHORT).show()
            }
            else{
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){task->

                    if(task.isSuccessful){

                        user.name=binding.name.editText?.text.toString()
                        user.password=binding.password.editText?.text.toString()
                        user.re_password=binding.repassword.editText?.text.toString()
                        user.email=binding.email.editText?.text.toString()
                        user.department=binding.spinner2.selectedItem.toString()
                        user.isStudent= isStudentChecked
                        user.isFaculty= isFacultyChecked

                        Toast.makeText(this, "Successfully Registered", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,LoginActivity::class.java))
                        finish()


                        Firebase.firestore.collection("User").document(Firebase.auth.currentUser!!.uid).set(user)


                    }
                    else{
                        Toast.makeText(this, "Registration failed:${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }


//        spinner button settings

        val departmentNames = arrayOf("Select department", "CSE", "IT", "EE", "EC")

        // Create an ArrayAdapter using the departmentNames array and a default spinner layout
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, departmentNames)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Find the spinner view
        val spinner: Spinner = findViewById(R.id.spinner2)

        // Apply the adapter to the spinner
        spinner.adapter = adapter
//        spinner.contentDescription = "Select department from the list"

        val textView = findViewById<TextView>(R.id.textView7)
        val spannableString = SpannableString("Already have an account? Login here.")

        // Get the start and end index of the text you want to underline
        val startIndex = 25
        val endIndex = 35

        // Underline the specified part of the text
        spannableString.setSpan(
            UnderlineSpan(), // UnderlineSpan to underline the text
            startIndex,
            endIndex,
            0
        )

        // Set the SpannableString to the TextView
        textView.text = spannableString
        spannableString.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Start the new activity when the underline text is clicked
                startActivity(Intent(this@RegisterActivity1, LoginActivity::class.java))
            }
        }, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Set the SpannableString to the TextView
        textView.text = spannableString
        // Make the TextView clickable
        textView.movementMethod = LinkMovementMethod.getInstance()

    }
}

