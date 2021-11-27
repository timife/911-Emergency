package com.timife.a911.emergencyHome.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.timife.a911.R
import com.timife.a911.data.model.databasemodel.EmergencyInfo
import com.timife.a911.databinding.FragmentESvBinding


class ESvRecyclerViewAdapter(
    private val context: Context,
    private val emergencyInfo: List<EmergencyInfo>,
    private val clickListener: OnClickListener
) : RecyclerView.Adapter<ESvRecyclerViewAdapter.EmergencyViewHolder>() {

    inner class EmergencyViewHolder(private var binding: FragmentESvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(emergencyInfo: EmergencyInfo) {
            binding.emergencyCardText.text = emergencyInfo.name
            binding.emergencyCardPhone.text = emergencyInfo.phone
            binding.emergencyCardOption.setOnClickListener {
                clickListener.onClick(emergencyInfo)
            }
            when (emergencyInfo.name) {
                "Police" -> {
                    binding.imageEmer.setImageResource(R.drawable.ic_police)
                }
                "Ambulance" -> {
                    binding.imageEmer.setImageResource(R.drawable.ic_hospital)
                }
                "Fire" -> {
                    binding.imageEmer.setImageResource(R.drawable.ic_fire)
                }
                else -> {
                    binding.imageEmer.setImageResource(R.drawable.ic_phone)
                }
            }
            binding.fragmentEsv.setOnClickListener {
                MaterialAlertDialogBuilder(context,R.style.ThemeOverlay_App_MaterialAlertDialog)
                    .setTitle(context.getString(R.string.place_call))
                    .setMessage("Place a call to ${emergencyInfo.name} at ${emergencyInfo.phone}")
                    .setNegativeButton(context.getString(R.string.cancel)){
                            dialog, _ ->
                        dialog.dismiss()
                    }.setPositiveButton(context.getString(R.string.call)){
                            dialog, _ ->
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:${emergencyInfo.phone}")
                        binding.emergencyCardText.context.startActivity(intent)
                        dialog.dismiss()
                    }.show()
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmergencyViewHolder {
        return EmergencyViewHolder(FragmentESvBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: EmergencyViewHolder, position: Int) {
        holder.bind(emergencyInfo[position])
    }

    override fun getItemCount(): Int = emergencyInfo.size

    class OnClickListener(val clickListener: (number: EmergencyInfo) -> Unit) {
        fun onClick(number: EmergencyInfo) {
            clickListener(number)
        }
    }
}