package com.timife.a911.ui.emergencyPref

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.timife.a911.databinding.FragmentOtherIssueBinding


class OtherIssueFragment : Fragment() {
    private lateinit var binding: FragmentOtherIssueBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOtherIssueBinding.inflate(inflater)
        val navController = findNavController()
        binding.otherIssueToolbar.setupWithNavController(navController)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submitIssueTicket.setOnClickListener {
            if (binding.otherIssueComment.text!!.isNotEmpty() && binding.otherIssueEmail.text!!.isNotEmpty() ) {
                feedBackEmail(binding.otherIssueComment.text.toString())
            } else {
                binding.otherIssueComment.error = "Please fill in the blank space"
                binding.otherIssueComment.requestFocus()
                binding.otherIssueEmail.error = "Please fill in the required email"
                binding.otherIssueEmail.requestFocus()
            }
        }

        binding.otherIssueComment.text!!.clear()
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