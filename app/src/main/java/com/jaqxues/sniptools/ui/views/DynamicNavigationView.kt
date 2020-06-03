package com.jaqxues.sniptools.ui.views

import android.content.Context
import android.util.AttributeSet
import android.util.SparseArray
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.core.view.isNotEmpty
import com.google.android.material.navigation.NavigationView
import com.jaqxues.sniptools.fragments.BaseFragment
import timber.log.Timber


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 02.06.20 - Time 23:05.
 */

class DynamicNavigationView : NavigationView, NavigationView.OnNavigationItemSelectedListener {
    private var currentMenuItem: MenuItem? = null
    private val fragments = SparseArray<BaseFragment>()
    private lateinit var activeFragment: BaseFragment
    private lateinit var listener: NavigationFragmentListener

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    /**
     * @param action: Adds Fragments and returns the currently active Fragment
     */
    fun initialize(listener: NavigationFragmentListener, action: DynamicNavigationView.() -> BaseFragment?) {
        this.listener = listener
        setNavigationItemSelectedListener(this)

        if (menu.isNotEmpty())
            checkMenuItem(menu.getItem(0))

        action()?.let {
            activeFragment = it
            listener.selectedFragment(it)
        }
    }

    private fun checkMenuItem(item: MenuItem) {
        item.isChecked = true
        currentMenuItem?.isChecked = false
        currentMenuItem = item
    }

    fun addFragment(fragment: BaseFragment) {
        fragments.append(fragment.menuId, fragment)
    }

    fun removeFragment(@IdRes menuId: Int) = fragments.remove(menuId)

    fun getFragmentById(@IdRes menuId: Int) =
        fragments.get(menuId) ?: throw IllegalArgumentException("MenuId not associated with a fragment")

    fun navigate(@IdRes menuId: Int): Boolean {
        val item = menu.findItem(menuId)
        if (item == null) {
            Timber.w("Tried to navigate to Id ($menuId) but item was not found")
            return false
        }
        return onNavigationItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val selected = fragments.get(item.itemId) ?: return false
        if (selected == activeFragment) {
            Timber.d("Selected MenuItem of current Fragment")
            return true
        }

        checkMenuItem(item)
        listener.selectedFragment(selected)
        activeFragment = selected

        return true
    }

    operator fun BaseFragment.unaryPlus() {
        addFragment(this)
    }

    interface NavigationFragmentListener {
        fun selectedFragment(fragment: BaseFragment)
    }
}
