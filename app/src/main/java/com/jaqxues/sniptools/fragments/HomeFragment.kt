package com.jaqxues.sniptools.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import com.jaqxues.sniptools.BuildConfig
import com.jaqxues.sniptools.R
import com.jaqxues.sniptools.utils.getColorCompat
import com.jaqxues.sniptools.utils.installedScVersion

class HomeFragment : BaseFragment() {
    override val menuId get() = R.id.nav_home

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.frag_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<TextView>(R.id.txt_app_version).text = buildSpannedString {
            append(getString(R.string.footer_app_version))
            append(": ")
            color(requireContext().getColorCompat(R.color.colorPrimaryLight)) {append(BuildConfig.VERSION_NAME) }
        }

        view.findViewById<TextView>(R.id.txt_sc_version).text = buildSpannedString {
            append(getString(R.string.footer_snapchat_version))
            append(": ")
            color(requireContext().getColorCompat(R.color.colorPrimaryLight)) { append(requireContext().installedScVersion ?: "Unknown") }
        }
    }
}
