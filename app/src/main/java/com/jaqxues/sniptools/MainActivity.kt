package com.jaqxues.sniptools

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.ui.tooling.preview.Preview
import com.jaqxues.akrolyb.prefs.putPref
import com.jaqxues.akrolyb.utils.Security
import com.jaqxues.sniptools.fragments.*
import com.jaqxues.sniptools.pack.PackFactory
import com.jaqxues.sniptools.pack.StatefulPackData
import com.jaqxues.sniptools.ui.AppScreen
import com.jaqxues.sniptools.ui.theme.DarkTheme
import com.jaqxues.sniptools.ui.views.DynamicNavigationView
import com.jaqxues.sniptools.utils.CommonSetup
import com.jaqxues.sniptools.utils.Preferences.SELECTED_PACKS
import com.jaqxues.sniptools.viewmodel.PackViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity(), DynamicNavigationView.NavigationFragmentListener {

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

        val packViewModel by viewModel<PackViewModel>()

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
        findViewById<Toolbar>(R.id.toolbar).subtitle =
            findViewById<DynamicNavigationView>(R.id.nav_view).menu.findItem(fragment.menuId)?.title
                ?: (fragment as PackFragment).name
        findViewById<DrawerLayout>(R.id.drawer_layout).closeDrawers()
    }
}

@Preview
@Composable
fun AppUi() {
    DarkTheme {
        val scaffoldState = rememberScaffoldState()
        val navController = rememberNavController()
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = {
                        Text("SnipTools")
                    },
                    navigationIcon = {
                        IconButton(onClick = { scaffoldState.drawerState.open() }) {
                            Icon(Icons.Default.Menu)
                        }
                    }
                )
            },
            drawerElevation = 2.dp,
            drawerContent = {
                // Stop Drawer from closing when touching on non-clickable elements
                Box(Modifier.fillMaxSize().clickable(onClick = {}, indication = null)) {
                    DrawerContent(LocalScreen.Home, navController)
                }
            }
        ) {
            Routing(navController)
        }
    }
}

@Composable
fun Routing(navController: NavHostController) {
    NavHost(navController, startDestination = LocalScreen.Home.route) {
        composable(LocalScreen.Home.route) { HomeScreen() }
        composable(LocalScreen.PackManager.route) { PackManagerScreen() }
        composable(LocalScreen.Settings.route) {}
        composable(LocalScreen.Faqs.route) {}
        composable(LocalScreen.Support.route) {}
        composable(LocalScreen.AboutUs.route) {}
        composable(LocalScreen.Shop.route) {}
        composable(LocalScreen.Features.route) {}
        composable(LocalScreen.Legal.route) {}

        composable(
            "known_bugs", listOf(
                navArgument("sc_version") {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument("pack_version") {
                    type = NavType.StringType
                    nullable = false
                })
        ) {
            KnownBugsScreen(
                it.arguments!!.getString("sc_version")!!,
                it.arguments!!.getString("pack_version")!!
            )
        }
    }
}

@Composable
fun DrawerContent(selectedItem: LocalScreen, navController: NavController) {
    Column(Modifier.fillMaxWidth()) {
        Row(
            Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageResource(R.drawable.sniptools_logo),
                Modifier.padding(8.dp)
            )
            Column(Modifier.padding(16.dp)) {
                ProvideEmphasis(emphasis = AmbientEmphasisLevels.current.high) {
                    Text("SnipTools")
                }
                ProvideEmphasis(emphasis = AmbientEmphasisLevels.current.medium) {
                    Text("I hope you're happy now", fontSize = 12.sp)
                }
            }
        }

        HomeDivider()

        LocalScreen.displayable.forEach {
            DrawerButton(
                icon = it.icon,
                label = stringResource(it.name),
                isSelected = it == selectedItem,
                action = {
                    navController.navigate(it.route)
                }
            )
        }
    }
}

@Composable
private fun DrawerButton(
    icon: VectorAsset,
    label: String,
    isSelected: Boolean,
    action: () -> Unit
) {
    val colors = MaterialTheme.colors

    val (imageAlpha, textIconColor, backgroundColor) =
        if (isSelected) {
            Triple(1f, colors.primary, colors.primary.copy(alpha = 0.12f))
        } else {
            Triple(0.6f, colors.onSurface, Color.Transparent)
        }

    Surface(
        modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp).fillMaxWidth(),
        color = backgroundColor,
        shape = MaterialTheme.shapes.small
    ) {
        TextButton(
            onClick = action,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth()
            ) {
                Image(
                    asset = icon,
                    colorFilter = ColorFilter.tint(textIconColor),
                    alpha = imageAlpha,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.preferredWidth(16.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.body2,
                    color = textIconColor
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewDrawerContent() {
    AppScreen {
        DrawerContent(LocalScreen.Settings, rememberNavController())
    }
}

sealed class LocalScreen(val route: String, @StringRes val name: Int, private val _icon: Any) {
    object Home : LocalScreen("home", R.string.menu_home, Icons.Default.Home)
    object PackManager : LocalScreen("pack_manager", R.string.menu_packs, R.drawable.ic_pack)
    object Settings : LocalScreen("settings", R.string.menu_settings, Icons.Default.Settings)
    object Faqs : LocalScreen("faqs", R.string.menu_faqs, R.drawable.ic_question_answer_black_48dp)
    object Support :
        LocalScreen("support", R.string.menu_support, R.drawable.ic_support_agent_black_48dp)

    object AboutUs : LocalScreen("about_us", R.string.menu_about_us, R.drawable.ic_group_black_48dp)
    object Shop : LocalScreen("shop", R.string.menu_shop, R.drawable.ic_payment_black_48dp)
    object Features : LocalScreen("features", R.string.menu_features, Icons.Default.List)
    object Legal : LocalScreen("legal", R.string.menu_legal, Icons.Default.Info)

    init {
        check(_icon is VectorAsset || _icon is @DrawableRes Int) {
            "Unknown Type for Icon ('${_icon::javaClass.name}' - '${_icon}')"
        }
    }

    @Composable
    val icon: VectorAsset
        get() {
            return when (_icon) {
                is VectorAsset -> _icon
                is @DrawableRes Int -> vectorResource(_icon)
                else -> error("Unknown Type for Icon")
            }
        }

    companion object {
        val displayable
            // fixme Bug where array is modified and Home set to null. Used a getter to fix issue.
          get()= arrayOf(Home, PackManager, Settings, Faqs, Support, AboutUs, Shop, Features, Legal)
    }
}
