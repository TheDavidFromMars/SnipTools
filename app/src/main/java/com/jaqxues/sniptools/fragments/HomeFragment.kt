package com.jaqxues.sniptools.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import com.jaqxues.sniptools.BuildConfig
import com.jaqxues.sniptools.R
import kotlinx.android.synthetic.main.frag_home.*

class HomeFragment : BaseFragment() {
    override val menuId get() = R.id.nav_home

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.frag_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        txt_app_version.text = buildSpannedString {
            append(getString(R.string.footer_app_version))
            append(": ")
            color(requireContext().getColor(R.color.colorPrimaryLight)) {append(BuildConfig.VERSION_NAME) }
        }
    }
}
