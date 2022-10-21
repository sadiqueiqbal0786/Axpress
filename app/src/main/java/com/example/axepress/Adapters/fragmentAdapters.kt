package com.example.axepress.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.axepress.Fragments.callFragment
import com.example.axepress.Fragments.chatFragment
import com.example.axepress.Fragments.statusFragment

class fragmentAdapters(fragmentManager: FragmentManager):FragmentPagerAdapter(fragmentManager){
    override fun getItem(position: Int): Fragment {
        return when(position){
            0-> chatFragment()
            1-> statusFragment()
            2-> callFragment()
            else->chatFragment()
        }
    }
    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title:String?=null
        if (position==0){
            title="CHATS"}
        if (position==1){
            title="STATUS"

        }
        if (position==2){
            title="CALLS"
        }
        return title
    }

}