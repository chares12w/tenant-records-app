package com.charlesenterprise.tenantrecords

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var tenantList: MutableList<Tenant>
    private lateinit var tenantAdapter: TenantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tenantList = mutableListOf(
            Tenant("Salon - Najjigo Maxey", "120,000 UGX", "14th", "December 2024", "0"),
            Tenant("Namusisi Victoria (Mama Mawadda)", "100,000 UGX", "10th", "November 2024", "3"),
            Tenant("Namazzi Hellen", "120,000 UGX", "7th", "December 2024", "2"),
            Tenant("Miss Fatuma Moses", "100,000 UGX", "7th", "January 2025", "1")
        )

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        tenantAdapter = TenantAdapter(tenantList) { position ->
            showEditDeleteDialog(position)
        }
        recyclerView.adapter = tenantAdapter

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            AddTenantDialog(this) { newTenant ->
                tenantList.add(newTenant)
                tenantAdapter.notifyDataSetChanged()
            }.show()
        }
    }

    private fun showEditDeleteDialog(position: Int) {
        val tenant = tenantList[position]
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_tenant, null)
        val nameField = dialogView.findViewById<EditText>(R.id.editName)
        val rentField = dialogView.findViewById<EditText>(R.id.editRent)

        nameField.setText(tenant.name)
        rentField.setText(tenant.rent)

        AlertDialog.Builder(this)
            .setTitle("Edit/Delete Tenant")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                tenantList[position] = tenant.copy(
                    name = nameField.text.toString(),
                    rent = rentField.text.toString()
                )
                tenantAdapter.notifyDataSetChanged()
            }
            .setNegativeButton("Delete") { _, _ ->
                tenantList.removeAt(position)
                tenantAdapter.notifyDataSetChanged()
            }
            .setNeutralButton("Cancel", null)
            .show()
    }
}

// Tenant Data Class
data class Tenant(
    val name: String,
    val rent: String,
    val paymentDate: String,
    val lastPayment: String,
    val monthsDue: String
)

// Tenant Adapter
class TenantAdapter(
    private val tenants: List<Tenant>,
    private val onItemClicked: (Int) -> Unit
) : RecyclerView.Adapter<TenantViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tenant, parent, false)
        return TenantViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: TenantViewHolder, position: Int) {
        holder.bind(tenants[position])
    }

    override fun getItemCount() = tenants.size
}

// Tenant ViewHolder
class TenantViewHolder(itemView: View, private val onItemClicked: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
    fun bind(tenant: Tenant) {
        itemView.findViewById<TextView>(R.id.tenantName).text = tenant.name
        itemView.findViewById<TextView>(R.id.tenantRent).text = "Rent: ${tenant.rent}"
        itemView.findViewById<TextView>(R.id.paymentDate).text = "Payment Date: ${tenant.paymentDate}"
        itemView.findViewById<TextView>(R.id.lastPayment).text = "Last Paid: ${tenant.lastPayment}"
        itemView.findViewById<TextView>(R.id.monthsDue).text = "Months Due: ${tenant.monthsDue}"

        itemView.setOnClickListener {
            onItemClicked(adapterPosition)
        }
    }
}
