package com.example.tracking_app.activities

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tracking_app.R
import com.example.tracking_app.models.Employee
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_save_employee.*

class SaveEmployeeActivity : AppCompatActivity() {
    private lateinit var employee:Employee
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_employee)
        employee =intent.getSerializableExtra(EXTRA_MESSAGE) as Employee
        val submitBtn = findViewById<View>(R.id.submitBtn) as Button
        if(employee.uuid.isNullOrBlank()){
            title = "Add New Employee"
        }else{
            val isDetail = intent.getStringExtra("DETAIL").equals("TRUE")
            submitBtn.text = "Modifier"
            title = "Update Employee"
            passDataToForms()
            if(isDetail){
                title = "View Employee Detail"
                submitBtn.visibility = View.INVISIBLE
            }
        }
        submitBtn.setOnClickListener{ saveEmployee()}

    }
    private fun saveEmployee(){
        employee.email=mail_input.editText?.text.toString()
        employee.firstName=firstName_input.editText?.text.toString()
        employee.lastName=lastName_input.editText?.text.toString()
        employee.numPhone=phone_input.editText?.text.toString()
        employee.email=mail_input.editText?.text.toString()
        employee.numCIN=cin_input.editText?.text.toString()
        val employeesReference : DatabaseReference = FirebaseDatabase.getInstance("https://tracking-app-314517-default-rtdb.firebaseio.com/").getReference().child("employees")
        if(employee.uuid.isNullOrBlank()){
            val auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(employee?.email!!, employee?.numCIN!!)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        employee.uuid = user?.uid!!
                        employeesReference.child(user?.uid!!).setValue(employee)
                        navigate2EmployeeListActivity()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                  }
                }
        }else{
            employeesReference.child(employee?.uuid!!).setValue(employee)
            navigate2EmployeeDetailActivity()
        }
    }

    private fun passDataToForms(){
        mail_input.editText?.setText(employee.email)
        firstName_input.editText?.setText(employee.firstName)
        lastName_input.editText?.setText(employee.lastName)
        phone_input.editText?.setText(employee.numPhone)
        cin_input.editText?.setText(employee.numCIN)
    }


    private fun navigate2EmployeeListActivity(){
        val i = Intent(this, EmployeesListActivity::class.java)
        startActivity(i)
    }
    private fun navigate2EmployeeDetailActivity(){
        val i = Intent(this, EmployeeDetailActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, employee)
        }
        startActivity(i)
    }
}