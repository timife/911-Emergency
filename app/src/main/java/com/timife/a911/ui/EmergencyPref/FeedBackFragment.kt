package com.timife.a911.ui.EmergencyPref

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ravikoradiya.library.CenterTitle
import com.timife.a911.databinding.FragmentFeedBackBinding
import com.timife.a911.utils.Constants

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
        binding.submitFeedback.setOnClickListener {
            if (binding.feedbackText.text!!.isNotEmpty()) {
                feedBackEmail(binding.feedbackText.text.toString())
            } else {
              binding.feedbackText.error = "Please fill in the blank space"
                binding.feedbackText.requestFocus()
            }
            binding.feedbackText.text!!.clear()
        }
    }

    private fun feedBackEmail(text: String) {
        val addresses: Array<String> = arrayOf(Constants.FEEDBACK_EMAIL_1,Constants.FEEDBACK_EMAIL_2)
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, "911+ Feedback:")
            putExtra(Intent.EXTRA_TEXT, text)
        }
       startActivity(intent)
    }

}