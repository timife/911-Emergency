package com.timife.a911.emergencyHome.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timife.a911.data.model.databasemodel.EmergencyInfo
import com.timife.a911.databinding.FragmentNonEsvBinding


class NonESvAdapter(
    private val emergencyInfo: List<EmergencyInfo>
) : RecyclerView.Adapter<NonESvAdapter.NonEmergencyViewHolder>() {

    inner class NonEmergencyViewHolder(private var binding: FragmentNonEsvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(emergencyInfo: EmergencyInfo) {
            binding.nonEmergencyText.text = emergencyInfo.name
            binding.nonPhone.text = emergencyInfo.phone
            binding.nonOption.setOnClickListener {
            }

            binding.fragmentNonEsv.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${emergencyInfo.phone}")
                binding.nonEmergencyText.context.startActivity(intent)
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NonEmergencyViewHolder {
        return NonEmergencyViewHolder(FragmentNonEsvBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int = emergencyInfo.size
    override fun onBindViewHolder(
        holder: NonESvAdapter.NonEmergencyViewHolder,
        position: Int
    ) {
        holder.bind(emergencyInfo[position])
    }



}