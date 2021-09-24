package com.timife.a911.emergencyHome.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private var mFragmentList: ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int {
        return mFragmentList.size
    }

    fun addFragment(fragments: Fragment) {
        mFragmentList.add(fragments)
        notifyDataSetChanged()
    }

    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }

}
