package com.jaqxues.sniptools.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.jaqxues.sniptools.BuildConfig
import com.jaqxues.sniptools.R
import com.jaqxues.sniptools.utils.installedScVersion
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

        txt_app_version.text = buildSpannedString {
            append(getString(R.string.footer_app_version))
            append(": ")
            bold { color(requireContext().getColor(R.color.colorPrimaryLight)) { append(BuildConfig.VERSION_NAME) } }
        }
        txt_sc_version.text = buildSpannedString {
            append(getString(R.string.footer_snapchat_version))
            append(": ")
            bold { color(requireContext().getColor(R.color.colorPrimaryLight)) { append(requireContext().installedScVersion ?: "Unknown") } }
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
