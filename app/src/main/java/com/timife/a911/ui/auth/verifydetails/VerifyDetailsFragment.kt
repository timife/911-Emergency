package com.timife.a911.ui.auth.verifydetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.timife.a911.R

class VerifyDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = VerifyDetailsFragment()
    }

    private lateinit var viewModel: VerifyDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_verify_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VerifyDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}