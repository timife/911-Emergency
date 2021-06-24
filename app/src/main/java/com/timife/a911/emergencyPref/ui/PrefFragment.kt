package com.timife.a911.emergencyPref.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.timife.a911.R

class PrefFragment : Fragment() {

    companion object {
        fun newInstance() = PrefFragment()
    }

    private lateinit var viewModel: PrefViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pref, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PrefViewModel::class.java)
        // TODO: Use the ViewModel
    }

}