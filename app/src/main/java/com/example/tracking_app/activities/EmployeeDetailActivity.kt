package com.example.tracking_app.activities

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tracking_app.R
import com.example.tracking_app.models.Employee
import com.example.tracking_app.models.Task
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_employee_detail.*

class EmployeeDetailActivity : AppCompatActivity() {
    private lateinit var employee:Employee
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_detail)
        val rvTasks = findViewById<View>(R.id.tasksRecycleView) as RecyclerView
        rvTasks.layoutManager = LinearLayoutManager(this)
        val tasks = mutableListOf<Task>()
        tasks.add(Task("Test","04-08-2021","20-08-2021","Agadir","Tanger","filled"))
        val adapter = TasksAdapter(tasks)
        rvTasks.adapter = adapter
        employee =intent.getSerializableExtra(EXTRA_MESSAGE) as Employee
        title = employee.lastName + " " + employee.firstName
        if (FirebaseAuth.getInstance().currentUser?.uid != LoginActivity.ADMIN_UUID ){
            title = "Welcome Mr."+ employee.lastName + " " + employee.firstName
            if (supportActionBar != null) {
                val actionBar: ActionBar? = supportActionBar
                actionBar!!.setDisplayHomeAsUpEnabled(false)
            }
            removeEmployeeBtn.visibility = View.INVISIBLE
            editEmployeeBtn.visibility = View.INVISIBLE
            textView.visibility = View.INVISIBLE
            textView3.visibility = View.INVISIBLE
            localisationEmployeeBtn.visibility = View.INVISIBLE
            localisationTextView.visibility = View.INVISIBLE
        }
    }

     fun onEditEmployee(view:View){
         val i = Intent(this, SaveEmployeeActivity::class.java).apply {
             putExtra(AlarmClock.EXTRA_MESSAGE,employee)
         }
         startActivity(i)
    }
    fun onDetailEmployee(view:View){
        val i = Intent(this, SaveEmployeeActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE,employee)
            putExtra("DETAIL","TRUE")
        }
        startActivity(i)
    }
    fun onDeleteEmployee(view:View){
        MaterialAlertDialogBuilder(this)
            .setTitle("Confirm Delete")
            .setMessage("Are you Sure")
            .setNegativeButton("DECLINE") { dialog, which ->
            }
            .setPositiveButton("ACCEPT") { dialog, which ->
                val employeesReference : DatabaseReference = FirebaseDatabase.getInstance("https://tracking-app-314517-default-rtdb.firebaseio.com/").getReference().child("employees")
                employeesReference.child(employee.uuid!!).removeValue()
                val i = Intent(this, EmployeesListActivity::class.java)
                startActivity(i)
            }
            .show()
    }
}