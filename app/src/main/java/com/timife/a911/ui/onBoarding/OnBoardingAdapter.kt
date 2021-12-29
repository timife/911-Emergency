package com.timife.a911.ui.onBoarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timife.a911.data.OnBoardingSlideItem
import com.timife.a911.databinding.SlideItemContainerBinding

/**
 * Created by Timothy Ademola on 22-12-2021
 */
class OnBoardingAdapter (private val sliderItems: List<OnBoardingSlideItem>) :
    RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {


    class OnBoardingViewHolder(private val binding: SlideItemContainerBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(introSlideItem: OnBoardingSlideItem) {
            binding.sliderTitle.text = introSlideItem.title
            binding.sliderTextDescription.text = introSlideItem.description
            binding.sliderImage.setImageResource(introSlideItem.icon)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        val binding = SlideItemContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OnBoardingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bind(sliderItems[position])
    }
}