package com.jaqxues.sniptools.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.viewModel
import androidx.navigation.*
import androidx.navigation.compose.*
import com.jaqxues.sniptools.R
import com.jaqxues.sniptools.fragments.HomeDivider
import com.jaqxues.sniptools.fragments.HomeScreen
import com.jaqxues.sniptools.fragments.KnownBugsScreen
import com.jaqxues.sniptools.fragments.PackManagerScreen
import com.jaqxues.sniptools.pack.ExternalDestination
import com.jaqxues.sniptools.pack.KnownExternalDestinations
import com.jaqxues.sniptools.pack.ModPack
import com.jaqxues.sniptools.pack.StatefulPackData
import com.jaqxues.sniptools.ui.composables.EmptyScreenMessage
import com.jaqxues.sniptools.ui.theme.DarkTheme
import com.jaqxues.sniptools.viewmodel.KnownBugsViewModel
import com.jaqxues.sniptools.viewmodel.PackViewModel
import com.jaqxues.sniptools.viewmodel.ServerPackViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 31.08.20 - Time 14:32.
 */
@Composable
fun AppScreen(screen: @Composable () -> Unit) {
    DarkTheme {
        Surface(color = MaterialTheme.colors.background) {
            screen()
        }
    }
}


@OptIn(ExperimentalStdlibApi::class)
@Composable
fun AppUi() {
    DarkTheme {
        val scaffoldState = rememberScaffoldState()
        val navController = rememberNavController()
        val currentBackStackEntry by navController.currentBackStackEntryAsState()

        val allLocalRoutes = remember { LocalScreen.allScreens.associateBy { it.route } }

        val packViewModel = viewModel<PackViewModel>()
        val loadedPacks = remember { mutableStateMapOf<String, ModPack>() }

        val coroutineScope = rememberCoroutineScope()
        remember {
            coroutineScope.launch {
                packViewModel.packLoadChanges.collect { (packName, state) ->
                    when (state) {
                        is StatefulPackData.LoadedPack -> {
                            loadedPacks[packName] = state.pack
                        }
                        else -> {
                            loadedPacks -= packName
                        }
                    }
                }
            }
        }

        val loadedPackDestinations = loadedPacks.mapValues { (_, pack) ->
            pack.disabledFeatures.observeAsState().value
            pack.staticFragments + pack.featureManager.getActiveFeatures().flatMap {
                it.getDestinations().toList()
            }
        }

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                val (pack, currentRoute) = currentBackStackEntry.routeInfo

                val currentScreen = if (pack == null) {
                    allLocalRoutes[currentRoute]
                } else {
                    KnownExternalDestinations.byRoute[currentRoute]
                        ?: loadedPackDestinations[pack]?.find { it.route == currentRoute }
                }

                TopAppBar(
                    title = {
                        Column {
                            Text("SnipTools")
                            if (currentScreen != null)
                                ProvideEmphasis(AmbientEmphasisLevels.current.medium) {
                                    Text(
                                        currentScreen.screenName,
                                        fontWeight = FontWeight.Normal, fontSize = 12.sp
                                    )
                                }
                        }
                    },
                    navigationIcon = {
                        if (currentScreen?.isTopLevelScreen == true) {
                            IconButton(onClick = { scaffoldState.drawerState.open() }) {
                                Icon(Icons.Default.Menu)
                            }
                        } else {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.Default.ArrowBack)
                            }
                        }
                    }
                )
            },
            drawerElevation = 2.dp,
            drawerContent = {
                // Stop Drawer from closing when touching on non-clickable elements
                Box(Modifier.fillMaxSize().clickable(onClick = {}, indication = null)) {
                    DrawerContent(
                        navController,
                        loadedPackDestinations
                    ) { scaffoldState.drawerState.close() }
                }
            }
        ) {
            Routing(
                navController,
                packViewModel,
                loadedPackDestinations
            )
        }
    }
}

data class RouteInfo(val packRoute: String? = null, val screenRoute: String? = null)

val NavBackStackEntry?.routeInfo: RouteInfo
    get() =
        this?.arguments?.getString(KEY_ROUTE)?.let {
            if (it.startsWith("pack/")) {
                val split = it.replace("pack/", "").split("/")
                RouteInfo(split[0], split[1])
            } else {
                RouteInfo(null, it.replaceAfter('/', "").replace("/", ""))
            }
        } ?: RouteInfo()

@Composable
fun Routing(
    navController: NavHostController,
    packViewModel: PackViewModel,
    loadedPackDestinations: Map<String, Array<ExternalDestination>>
) {
    val serverPackViewModel = viewModel<ServerPackViewModel>()
    val knownBugsViewModel = viewModel<KnownBugsViewModel>()

    val start = navController.currentBackStackEntry?.arguments?.getString(KEY_ROUTE)
        ?: LocalScreen.Home.route
    NavHost(navController, startDestination = start) {
        composable(LocalScreen.Home.route) { HomeScreen() }
        composable(LocalScreen.PackManager.route) {
            PackManagerScreen(
                navController,
                packViewModel,
                serverPackViewModel
            )
        }
        composable(LocalScreen.Settings.route) { EmptyScreenMessage("Screen not available") }
        composable(LocalScreen.Faqs.route) { EmptyScreenMessage("Screen not available") }
        composable(LocalScreen.Support.route) { EmptyScreenMessage("Screen not available") }
        composable(LocalScreen.AboutUs.route) { EmptyScreenMessage("Screen not available") }
        composable(LocalScreen.Shop.route) { EmptyScreenMessage("Screen not available") }
        composable(LocalScreen.Features.route) { EmptyScreenMessage("Screen not available") }
        composable(LocalScreen.Legal.route) { EmptyScreenMessage("Screen not available") }

        composable(
            "${LocalScreen.KnownBugs.route}/{sc_version}/{pack_version}", listOf(
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
                it.arguments!!.getString("pack_version")!!,
                knownBugsViewModel
            )
        }
        loadedPackDestinations.forEach { (packName, destinations) ->
            destinations.forEach { destination ->
                composable(
                    "pack/$packName/${destination.route}",
                    content = { destination.screenComposable() })
            }
        }
    }
}

@Composable
fun DrawerContent(
    navController: NavController,
    loadedPackDestinations: Map<String, Array<ExternalDestination>>,
    closeDrawer: () -> Unit
) {
    ScrollableColumn(Modifier.fillMaxWidth()) {
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

        val (pack, route) = navController.currentBackStackEntryAsState().value.routeInfo

        LocalScreen.topLevelScreens.forEach {
            DrawerButton(
                label = it.screenName,
                icon = it.icon,
                isSelected = it.route == route,
                action = {
                    if (it.route != route) {
                        navController.popBackStack(navController.graph.startDestination, false)
                        navController.navigate(it.route)
                    }
                    closeDrawer()
                }
            )
        }
        loadedPackDestinations.forEach { (packName, destinations) ->
            Divider(Modifier.padding(horizontal = 16.dp))
            ProvideEmphasis(emphasis = AmbientEmphasisLevels.current.medium) {
                Text(
                    packName,
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp),
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp
                )
            }

            for (destination in destinations) {
                val known = KnownExternalDestinations.byRoute[destination.route]

                val (icon, label) = if (known == null) {
                    null to destination.defaultName
                } else {
                    known.icon to stringResource(known.stringRes)
                }
                DrawerButton(
                    icon = icon,
                    label = label,
                    isSelected = packName == pack && destination.route == route,
                    action = {
                        if (destination.route != route) {
                            navController.popBackStack(navController.graph.startDestination, false)
                            navController.navigate("pack/$packName/${destination.route}")
                        }
                        closeDrawer()
                    }
                )
            }
        }
    }
}


@Composable
private fun DrawerButton(
    label: String,
    icon: VectorAsset? = null,
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
                if (icon != null)
                    Image(
                        asset = icon,
                        colorFilter = ColorFilter.tint(textIconColor),
                        alpha = imageAlpha,
                        modifier = Modifier.size(24.dp)
                    )
                else
                    Spacer(Modifier.size(24.dp))
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