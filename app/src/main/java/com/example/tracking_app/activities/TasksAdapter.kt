package com.example.tracking_app.activities

import android.content.Intent
import android.provider.AlarmClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tracking_app.R
import com.example.tracking_app.models.Employee
import com.example.tracking_app.models.Task
import com.google.android.material.card.MaterialCardView

class TasksAdapter(val tasks:MutableList<Task>) : RecyclerView.Adapter<TasksAdapter.ViewHolder>(){
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val taskName = itemView.findViewById<TextView>(R.id.taskName)
        val dateDepart = itemView.findViewById<TextView>(R.id.departDate)
        val dateFin = itemView.findViewById<TextView>(R.id.endDate)
        val lieuDepart = itemView.findViewById<TextView>(R.id.lieuDepart)
        val endLocalisation = itemView.findViewById<TextView>(R.id.leiuArrive)
        val status = itemView.findViewById<TextView>(R.id.status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.activity_task_item, parent, false)
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task: Task = tasks.get(position)
        holder.taskName.setText(task.name)
        holder.dateDepart.setText(task.dateDepart)
        holder.dateFin.setText(task.dateFin)
        holder.lieuDepart.setText(task.lieuArrive)
        holder.status.setText(task.status)
        holder.endLocalisation.setText(task.lieuArrive)

    }

}