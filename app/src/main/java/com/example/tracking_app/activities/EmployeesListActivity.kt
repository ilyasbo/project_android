package com.example.tracking_app.activities

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tracking_app.R
import com.example.tracking_app.models.Employee
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class EmployeesListActivity(val employees:MutableList<Employee> = mutableListOf()) : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employees_list)
        val rvemployees = findViewById<View>(R.id.employees_list) as RecyclerView
        val employeesReference : DatabaseReference = FirebaseDatabase.getInstance("https://tracking-app-314517-default-rtdb.firebaseio.com/").getReference().child("employees")
        rvemployees.layoutManager = LinearLayoutManager(this)
        val adapter = EmployeesAdapter(employees,this)
        val employeeListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                rvemployees.adapter = adapter
                employees.clear()
                for (data in dataSnapshot.children) {
                    println(data)
                    val employee = data.getValue(Employee::class.java) as Employee
                    employees.add(employee)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w( "loadPost:onCancelled", databaseError.toException())
            }
        }
        employeesReference.addValueEventListener(employeeListener)
        // search view
        val employeeSearchView = findViewById<View>(R.id.searchEmployeeInputText) as SearchView
        employeeSearchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter.filter(p0)
                return true
                }
        }
        )
        // floating add button
        val addbuton = findViewById<View>(R.id.addEmployeeBtn) as FloatingActionButton
        addbuton.setOnClickListener{navigate2SaveEmployeeActivity()}

    }
    private fun navigate2SaveEmployeeActivity(){
        val i = Intent(this, SaveEmployeeActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, Employee())
        }
        startActivity(i)
    }
}