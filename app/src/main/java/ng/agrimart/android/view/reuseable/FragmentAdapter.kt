/*
 * Created by Joseph Dalughut on 13/06/2021, 09:53
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.view.reuseable

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * An adapter which can be used to display a list of [Fragment] items in a [ViewPager]
 */
class FragmentAdapter(fragmentManager: FragmentManager, behavior: Int):
    FragmentStatePagerAdapter(fragmentManager, behavior) {

    var fragments: List<Fragment>? = null
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        fragments?.let {
            return it.count()
        } ?: run {
            return 0
        }
    }

    override fun getItem(position: Int): Fragment {
        return fragments!![position]
    }
}