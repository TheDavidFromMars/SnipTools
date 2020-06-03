package com.jaqxues.sniptools.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TabHost
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jaqxues.sniptools.BuildConfig
import com.jaqxues.sniptools.R
import kotlinx.android.synthetic.main.frag_pack_manager.*


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 03.06.20 - Time 10:56.
 */
class PackManagerFragment: BaseFragment() {
    override val menuId get() = R.id.nav_pack_manager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.frag_pack_manager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view_pager.isSaveEnabled = false
        view_pager.adapter = PackManagerPagerAdapter(this)
        TabLayoutMediator(tab_layout, view_pager) { tab, position ->
            val id = when (position) {
                0 -> R.string.tab_pack_selector_title
                1 -> R.string.tab_pack_downloader_title
                else -> throw IllegalStateException("Position $position not registered")
            }
            tab.setText(id)
        }.attach()
        txt_app_version.text = BuildConfig.VERSION_NAME
        txt_sc_version.text = "10.80.0.0"
    }
}

class PackManagerPagerAdapter(frag: Fragment): FragmentStateAdapter(frag) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int) =
        when (position) {
            0 -> PackSelectorFrag(0)
            1 -> PackDownloaderFrag(0)
            else -> throw IllegalArgumentException("Unknown item for position $position")
        }
}

class PackSelectorFrag(override val menuId: Int) : BaseFragment()
class PackDownloaderFrag(override val menuId: Int) : BaseFragment()
