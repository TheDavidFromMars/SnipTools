package com.jaqxues.sniptools.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.underline
import com.jaqxues.sniptools.R


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 06.06.20 - Time 16:09.
 */
class PackDownloaderFragment : BaseFragment() {
    override val menuId = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.frag_pack_downloader, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<TextView>(R.id.txt_last_checked).text = buildSpannedString {
            append(getString(R.string.footer_last_checked))
            append(": ")
            bold { underline { append("14 min ago") } }
        }
    }
}