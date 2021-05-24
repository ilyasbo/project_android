package com.example.tracking_app.activities

import android.content.Context
import android.content.Intent
import android.provider.AlarmClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.tracking_app.R
import com.example.tracking_app.models.Employee
import com.example.tracking_app.models.Task
import com.google.android.material.card.MaterialCardView
import java.util.stream.Collectors

class EmployeesAdapter(val employees: MutableList<Employee>, val context:Context) : RecyclerView.Adapter<EmployeesAdapter.ViewHolder>(),
    Filterable {
    var employeeFilterList = ArrayList<Employee>()
    init {
        employeeFilterList = employees as ArrayList<Employee>
    }
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val firstNameTextView = itemView.findViewById<TextView>(R.id.firstName)
        val lastNameTextView = itemView.findViewById<TextView>(R.id.lastName)
        val phoneTextView = itemView.findViewById<TextView>(R.id.phone)
        val numCinTextView = itemView.findViewById<TextView>(R.id.numCin)
        val cardItem = itemView.findViewById<MaterialCardView>(R.id.employee_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.activity_employee_item, parent, false)
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return employeeFilterList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employee: Employee = employeeFilterList.get(position)
        val firstNameTextView = holder.firstNameTextView
        val lastNameTextView = holder.lastNameTextView
        val phoneTextView = holder.phoneTextView
        val numCinTextView = holder.numCinTextView
        firstNameTextView.setText(employee.firstName)
        lastNameTextView.setText(employee.lastName)
        phoneTextView.setText(employee.numPhone)
        numCinTextView.setText(employee.numCIN)
        holder.cardItem.setOnClickListener { navigate2EmployeeDetail(employee) }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    employeeFilterList = employees as ArrayList<Employee>
                } else {
                    val searchedEmployees = employees.stream().filter { employee ->
                        ((employee.firstName?.contains(constraint.toString(), true)!!)
                                or (employee.lastName?.contains(constraint.toString(),true)!!) or (employee.numCIN?.contains(constraint.toString(), true)!!)
                                or (employee.numPhone?.contains(constraint.toString(), true)!!))
                    }.collect(Collectors.toList())
                    employeeFilterList = searchedEmployees as ArrayList<Employee>
                }
                val filterResults = FilterResults()
                filterResults.values = employeeFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                employeeFilterList = results?.values as ArrayList<Employee>
                notifyDataSetChanged()
            }

        }
    }

    private fun navigate2EmployeeDetail(employee:Employee){
        val i = Intent(context, EmployeeDetailActivity::class.java).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE,employee)
        }
        startActivity(context,i,null)
    }

}