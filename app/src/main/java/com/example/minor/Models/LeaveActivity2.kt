package com.example.minor.Models

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.minor.R
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.CalendarView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class LeaveActivity2 : AppCompatActivity() {
    private lateinit var leaveDescriptionEditText: TextInputEditText
    private lateinit var submitLeaveButton: Button
    private lateinit var firestore: FirebaseFirestore
    private val userId: String? = FirebaseAuth.getInstance().currentUser?.uid
    private var startDate: Date? = null
    private var endDate: Date? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leave2)
        leaveDescriptionEditText = findViewById(R.id.leaveDescriptionEditText)
        submitLeaveButton = findViewById(R.id.submitLeaveButton)

        firestore = FirebaseFirestore.getInstance()

//        calendar handling: setting current date
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        // Initialize CalendarView and set current date
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView.setDate(calendar.timeInMillis, false, true)
//        handling selection of dates in calendar
        // Set date change listener
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)

            if (startDate == null || selectedDate.time.before(startDate)) {
                startDate = selectedDate.time
                endDate = null
            } else if (endDate == null && !selectedDate.time.before(startDate)) {
                endDate = selectedDate.time
            } else {
                // Reset selection and select new start date
                startDate = selectedDate.time
                endDate = null
            }

            resetCalendar()
            highlightSelectedDate(startDate, endDate)

        }
        submitLeaveButton.setOnClickListener {
            submitLeaveRequest(startDate!!, endDate!!)
        }
    }

    private fun formatDate(date: Date): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(date)
    }
    private fun highlightSelectedDate(startDate: Date?, endDate: Date?) {
        // Convert the dates to milliseconds
        val startCalendar = Calendar.getInstance()
        val endCalendar = Calendar.getInstance()

        if (startDate != null) {
            startCalendar.time = startDate
        }

        if (endDate != null) {
            endCalendar.time = endDate
        }

        // Highlight the selected start date on the calendar
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView.setDate(startDate?.time ?: System.currentTimeMillis(), true, true)

        // Highlight the selected end date on the calendar
        if (endDate != null) {
            calendarView.setDate(endDate.time, true, true)
        }
    }


    private fun resetCalendar() {
        // Reset the calendar to the current date
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView.setDate(System.currentTimeMillis(), false, true)
    }

    private fun submitLeaveRequest(startDate: Date, endDate: Date) {
        val leaveDescription = leaveDescriptionEditText.text.toString().trim()
        val startDateString = formatDate(startDate)
        val endDateString = formatDate(endDate)


        if (leaveDescription.isNotEmpty() && userId != null) {
            val leaveRequest = hashMapOf(
                "userId" to userId,
                "description" to leaveDescription,
                "timestamp" to System.currentTimeMillis(),
                "startDate" to startDateString,
                "endDate" to endDateString
            )

            firestore.collection("leave_requests")
                .add(leaveRequest)
                .addOnSuccessListener {
                    Toast.makeText(this, "Leave request submitted successfully", Toast.LENGTH_SHORT).show()
                    leaveDescriptionEditText.text = null
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to submit leave request: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Leave description cannot be empty", Toast.LENGTH_SHORT).show()
        }
    }
}
