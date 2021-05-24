package com.example.tracking_app.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tracking_app.R
import com.example.tracking_app.models.Employee
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    companion object {
        const val ADMIN_UUID = "zzZqAFgCgKMWNLtOlYoHqB9gpRS2"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
     fun onSignIn(view : View){
        val auth = FirebaseAuth.getInstance()
        val password = password_input.editText?.text.toString()
        val email = email_input.editText?.text.toString()
        auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                updateUI(user?.uid!!)
            } else {
                // If sign in fails, display a message to the user.
                Log.w("signInWithEmail:failure", task.exception)
                Toast.makeText(baseContext, "Authentication failed.",
                    Toast.LENGTH_SHORT).show()
//                updateUI(null)
            }
        }
    }
    private fun updateUI(userUID:String){
        if(userUID == ADMIN_UUID){
            val i = Intent(this, EmployeesListActivity::class.java)
            startActivity(i)
        }else {
            val employeesReference : DatabaseReference = FirebaseDatabase.getInstance("https://tracking-app-314517-default-rtdb.firebaseio.com/").getReference().child("employees")
            val Context = this
            employeesReference.child(userUID).addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val i = Intent(Context, EmployeeDetailActivity::class.java).apply {
                        putExtra(AlarmClock.EXTRA_MESSAGE,snapshot.getValue(Employee::class.java) as Employee)
                    }
                    startActivity(i)
                }

            })
        }
    }
}