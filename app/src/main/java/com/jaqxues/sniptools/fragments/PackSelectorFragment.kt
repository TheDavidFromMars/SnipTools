package com.jaqxues.sniptools.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jaqxues.sniptools.R
import com.jaqxues.sniptools.viewmodel.PackViewModel
import org.koin.android.ext.android.inject


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 05.06.20 - Time 18:57.
 */
class PackSelectorFragment: BaseFragment() {
    private val packViewModel by inject<PackViewModel>()
    override val menuId = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_pack_selector, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }
}