package com.inverse.core.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentManager: FragmentActivity): FragmentStateAdapter(fragmentManager) {
    private val mFragmentList: ArrayList<Fragment> = ArrayList()

    fun addFragment(fragment:Fragment){
        mFragmentList.add(fragment)
        notifyDataSetChanged()
    }
    fun clear(){
        mFragmentList.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = mFragmentList.size

    override fun createFragment(position: Int): Fragment = mFragmentList.get(position)
}