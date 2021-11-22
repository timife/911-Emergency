package com.timife.a911.emergencyPref.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ravikoradiya.library.CenterTitle
import com.timife.a911.databinding.FragmentFeedBackBinding

class FeedBackFragment : Fragment() {
    private lateinit var binding: FragmentFeedBackBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBackBinding.inflate(inflater)
        CenterTitle.centerTitle(binding.feedbackToolbar, true)
        // Inflate the layout for this fragment
        val navController = findNavController()
        binding.feedbackToolbar.setupWithNavController(navController)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submit.setOnClickListener {
            if (binding.userName.text!!.isNotEmpty()) {
                feedBackEmail(binding.userName.text.toString())
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please fill in the blank space",
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.userName.text!!.clear()
        }
    }

    private fun feedBackEmail(text: String) {
        val addresses: Array<String> = arrayOf("angadc412@gmail.com", "timife007@gmail.com")

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, "911+ Feedback:")
            putExtra(Intent.EXTRA_TEXT, text)
        }
       startActivity(intent)
    }

}