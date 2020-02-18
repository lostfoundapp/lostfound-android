package com.lostfoundapp.ui.main

import android.content.Context
import android.database.DataSetObserver
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.lostfoundapp.ConfigActivity
import com.lostfoundapp.NotifActivity
import com.lostfoundapp.R
import com.lostfoundapp.presentation.posts.PostActivity

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        when(position){
            0 ->{
                return PostActivity()
            }
            1 ->{
                return NotifActivity()
            }
            2 ->{
                return ConfigActivity()
            }
            else -> return PostActivity()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return context.resources.getString(TAB_TITLES[position])
    }


    override fun getCount(): Int {
        // Show 3 total pages.
        return 3
    }
}