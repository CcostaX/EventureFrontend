package com.example.eventure

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabPageAdapter (activity: FragmentActivity, private val tabCount: Int) : FragmentStateAdapter(activity)
{
    override fun getItemCount(): Int = tabCount


    override fun createFragment(position: Int): Fragment {
        return when (position)
        {
            0 -> DashboardFragment()
            1 -> EateriesFragment()
            2 -> RoutesFragment()
            else -> ProfileFragment()
        }
    }
}