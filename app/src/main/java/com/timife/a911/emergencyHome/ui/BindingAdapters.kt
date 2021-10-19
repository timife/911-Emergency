package com.timife.a911.emergencyHome.ui

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter

@BindingAdapter("emergencyStatus")
fun bindStatus(progressBar: ProgressBar, status: EmergencyStatus?) {
    when (status) {
        EmergencyStatus.LOADING -> {
            progressBar.visibility = View.VISIBLE
        }
        EmergencyStatus.ERROR -> {
            progressBar.visibility = View.GONE
        }
        EmergencyStatus.DONE -> {
            progressBar.visibility = View.GONE
        }
    }
}