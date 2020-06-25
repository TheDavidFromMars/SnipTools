package com.jaqxues.sniptools

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.jaqxues.akrolyb.prefs.putPref
import com.jaqxues.sniptools.data.Preferences.SELECTED_PACKS
import com.jaqxues.sniptools.fragments.BaseFragment
import com.jaqxues.sniptools.fragments.HomeFragment
import com.jaqxues.sniptools.fragments.PackManagerFragment
import com.jaqxues.sniptools.ui.views.DynamicNavigationView
import com.jaqxues.sniptools.utils.CommonSetup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity(), DynamicNavigationView.NavigationFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CommonSetup.initPrefs()
        if (intent.hasExtra("select_new_pack")) {
            val extra = intent.getStringExtra("select_new_pack") ?: throw IllegalStateException("Extra cannot be null")
            Timber.i("SnipTools was started with Intent Extra 'select_new_pack' - '$extra'. Selecting new Pack and Starting Snapchat")
            SELECTED_PACKS.putPref(listOf(extra))
            finish()
            return
        }

        setContentView(R.layout.activity_main)

        val toolbar = toolbar
        setSupportActionBar(toolbar)

        val drawerLayout = drawer_layout
        val navView = nav_view

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name).let {
            drawerLayout.addDrawerListener(it)
            it.syncState()
        }
        navView.initialize(this) {
            val homeFragment = HomeFragment()

            +homeFragment
            +PackManagerFragment()
            
            homeFragment
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment)
//        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
        return false || super.onSupportNavigateUp()
    }

    override fun selectedFragment(fragment: BaseFragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frag_container_main, fragment)
            .commit()
        toolbar.subtitle = nav_view.menu.findItem(fragment.menuId).title
        drawer_layout.closeDrawers()
    }
}
