package com.jaqxues.sniptools.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.jaqxues.sniptools.BuildConfig
import com.jaqxues.sniptools.R
import com.jaqxues.sniptools.utils.getColorCompat
import com.jaqxues.sniptools.utils.installedScVersion


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
        val viewPager = view.findViewById<ViewPager2>(R.id.view_pager)
        viewPager.isSaveEnabled = false
        viewPager.adapter = PackManagerPagerAdapter(this)
        TabLayoutMediator(view.findViewById(R.id.tab_layout), viewPager) { tab, position ->
            val id = when (position) {
                0 -> R.string.tab_pack_selector_title
                1 -> R.string.tab_pack_downloader_title
                else -> throw IllegalStateException("Position $position not registered")
            }
            tab.setText(id)
        }.attach()

        view.findViewById<TextView>(R.id.txt_app_version).text = buildSpannedString {
            append(getString(R.string.footer_app_version))
            append(": ")
            bold { color(requireContext().getColorCompat(R.color.colorPrimaryLight)) { append(BuildConfig.VERSION_NAME) } }
        }
        view.findViewById<TextView>(R.id.txt_sc_version).text = buildSpannedString {
            append(getString(R.string.footer_snapchat_version))
            append(": ")
            bold { color(requireContext().getColorCompat(R.color.colorPrimaryLight)) { append(requireContext().installedScVersion ?: "Unknown") } }
        }
    }
}

class PackManagerPagerAdapter(frag: Fragment): FragmentStateAdapter(frag) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int) =
        when (position) {
            0 -> PackSelectorFragment()
            1 -> PackDownloaderFragment()
            else -> throw IllegalArgumentException("Unknown item for position $position")
        }
}
