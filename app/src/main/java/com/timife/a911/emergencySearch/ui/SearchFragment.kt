package com.timife.a911.emergencySearch.ui

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.timife.a911.EmergencyApplication
import com.timife.a911.R
import com.timife.a911.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        val text = "Use emergency services if you require \n immediate help and non-emergency services \n if you require non-immediate help."
        val ss = SpannableString(text)
        val clickableSpan1 = object : ClickableSpan(){
            override fun onClick(widget: View) {
                widget.cancelPendingInputEvents()
                try {
                   navController.navigate(R.id.action_searchFragment_to_immediateDialog)
                } catch (e: Exception) {
                }
            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.typeface = Typeface.DEFAULT_BOLD
            }
        }
        val clickableSpan2 = object : ClickableSpan(){
            override fun onClick(widget: View) {
                widget.cancelPendingInputEvents()
                try {
                    navController.navigate(R.id.action_searchFragment_to_nonImmediateDialog)
                } catch (e: Exception) {
                }            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                    ds.isUnderlineText = true
                    ds.typeface = Typeface.DEFAULT_BOLD
            }
        }
        ss.setSpan(clickableSpan1,40,54,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ss.setSpan(clickableSpan2,99,117,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.emergencySearchInfo.text =ss
        binding.emergencySearchInfo.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as EmergencyApplication).emergencyComponent.inject(this)
    }

}