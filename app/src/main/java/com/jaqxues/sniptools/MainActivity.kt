package com.jaqxues.sniptools

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.jaqxues.akrolyb.prefs.putPref
import com.jaqxues.akrolyb.utils.Security
import com.jaqxues.sniptools.pack.PackFactory
import com.jaqxues.sniptools.pack.StatefulPackData
import com.jaqxues.sniptools.ui.AppUi
import com.jaqxues.sniptools.utils.CommonSetup
import com.jaqxues.sniptools.utils.Preferences.SELECTED_PACKS
import com.jaqxues.sniptools.viewmodel.PackViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED
        ) {
            init()
        } else {
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                if (granted) {
                    init()
                } else {
                    Timber.e("Storage Permission Denied")
                    Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_LONG).show()
                    finish()
                }
            }.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    private fun init() {
        CommonSetup.initPrefs()

        if (intent.hasExtra("select_new_pack")) {
            val extra = intent.getStringExtra("select_new_pack")
                ?: throw IllegalStateException("Extra cannot be null")
            Timber.i("SnipTools was started with Intent Extra 'select_new_pack' - '$extra'. Selecting new Pack and Starting Snapchat")
            SELECTED_PACKS.putPref(setOf(extra))
            finish()
            return
        }

        val packViewModel: PackViewModel by viewModels()

        lifecycleScope.launch {
            packViewModel.packLoadChanges.collect { (packName, state) ->
                when (state) {
                    is StatefulPackData.LoadedPack -> {
                        val pack = state.pack
                        pack.disabledFeatures.observe(this@MainActivity) {
//                            navView.setPackFragments(menuInflater, packName, pack)
                        }
                    }
                    else -> {
//                        navView.removePackFragments(packName)
                    }
                }
            }
        }
        packViewModel.loadActivatedPacks(
            this@MainActivity,
            if (BuildConfig.DEBUG) null else Security.certificateFromApk(
                this@MainActivity,
                BuildConfig.APPLICATION_ID
            ),
            PackFactory(false)
        )
        setContent {
            AppUi()
        }
    }
}
