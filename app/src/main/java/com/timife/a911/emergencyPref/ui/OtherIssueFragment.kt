package com.timife.a911.emergencyPref.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.timife.a911.R
import com.timife.a911.databinding.FragmentOtherIssueBinding


class OtherIssueFragment : Fragment() {
    private lateinit var binding: FragmentOtherIssueBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOtherIssueBinding.inflate(inflater)
        val navController =findNavController()
        binding.reportIssueToolbar.setupWithNavController(navController)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (binding.comment.text!!.isNotEmpty()) {
            feedBackEmail(binding.comment.text.toString())
        } else {
            Toast.makeText(
                requireContext(),
                "Please fill in the blank space",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.comment.text!!.clear()
    }


    private fun feedBackEmail(text: String) {
        val addresses: Array<String> = arrayOf("angadc412@gmail.com", "timife007@gmail.com")

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, "Other 911+ issues:")
            putExtra(Intent.EXTRA_TEXT, text)
        }
        startActivity(intent)
    }
}