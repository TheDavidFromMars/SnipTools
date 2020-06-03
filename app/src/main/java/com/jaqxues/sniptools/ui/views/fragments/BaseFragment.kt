package com.jaqxues.sniptools.ui.views.fragments

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 02.06.20 - Time 23:44.
 */
abstract class BaseFragment : Fragment() {
    @get:IdRes
    abstract val menuId: Int
}
