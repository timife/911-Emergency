package com.timife.a911.ui.onBoarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.timife.a911.EmergencyApplication
import com.timife.a911.R
import com.timife.a911.data.OnBoardingSlideItem
import com.timife.a911.databinding.FragmentOnBoardingBinding
import com.timife.a911.extensions.navigateSafe
import com.timife.a911.utils.DepthPageTransformer

/**
 * Created by Timothy Ademola on 22-12-21
 */
class OnBoardingFragment : Fragment() {
 private lateinit var binding : FragmentOnBoardingBinding
    private lateinit var adapter: OnBoardingAdapter


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as EmergencyApplication).emergencyComponent.inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardingBinding.inflate(inflater)
        adapter = OnBoardingAdapter(onBoardingItems())
        binding.onboardSliderViewPager.adapter = adapter
        binding.onboardSliderViewPager.setPageTransformer(DepthPageTransformer())
        setupIndicators()
        setCurrentIndicator(index = 0)
        binding.onboardSliderViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(index = position)
            }
        })

        binding.next.setOnClickListener {
            if (binding.onboardSliderViewPager.currentItem + 1 < adapter.itemCount) {
                binding.onboardSliderViewPager.currentItem += 1
            } else {
                navigateToLogin()
            }
        }

        binding.skipOnboarding.setOnClickListener {
            navigateToLogin()
        }
        return binding.root
    }

    private fun navigateToLogin() {
        navigateSafe(OnBoardingFragmentDirections
            .actionOnBoardingFragmentToAuthNavigation())
    }

    private fun onBoardingItems(): List<OnBoardingSlideItem> {
        return listOf(
            OnBoardingSlideItem(
                getString(R.string.onboardingTitle1),
                getString(R.string.onboardingSubtitle1),
                R.drawable.onboarding_call
            ),
            OnBoardingSlideItem(
                getString(R.string.onboardingTitle2),
                getString(R.string.onboardingSubtitle2),
                R.drawable.onboard_search
            ),
            OnBoardingSlideItem(
                getString(R.string.onboardingTitle3),
                getString(R.string.onboardingSubtitle3),
                R.drawable.onboard_save
            )
        )
    }


    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(adapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(requireContext())
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.onboarding_indicator_inactive
                    )
                )

                this?.layoutParams = layoutParams
            }
            binding.indicatorContainer.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = binding.indicatorContainer.childCount
        for (i in 0 until childCount) {
            val imageView = binding.indicatorContainer[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.onboarding_indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.onboarding_indicator_inactive
                    )
                )
            }

        }
    }

}