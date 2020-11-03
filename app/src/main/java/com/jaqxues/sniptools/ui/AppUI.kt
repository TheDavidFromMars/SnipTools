package com.jaqxues.sniptools.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.jaqxues.sniptools.R
import com.jaqxues.sniptools.fragments.HomeDivider
import com.jaqxues.sniptools.fragments.HomeScreen
import com.jaqxues.sniptools.fragments.KnownBugsScreen
import com.jaqxues.sniptools.fragments.PackManagerScreen
import com.jaqxues.sniptools.ui.composables.EmptyScreenMessage
import com.jaqxues.sniptools.ui.theme.DarkTheme
import com.jaqxues.sniptools.viewmodel.KnownBugsViewModel
import com.jaqxues.sniptools.viewmodel.PackViewModel
import com.jaqxues.sniptools.viewmodel.ServerPackViewModel


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


@Composable
fun AppUi() {
    DarkTheme {
        val scaffoldState = rememberScaffoldState()
        val navController = rememberNavController()
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val allRoutes = remember { LocalScreen.allScreens.associateBy { it.route } }
        val mainRoutes = remember { LocalScreen.displayable.associateBy { it.route } }
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                val currentRoute =
                    currentBackStackEntry?.arguments?.getString(KEY_ROUTE)?.replaceAfter('/', "")
                        ?.replace("/", "")
                TopAppBar(
                    title = {
                        Column {
                            Text("SnipTools")
                            allRoutes[currentRoute]?.let {
                                ProvideEmphasis(AmbientEmphasisLevels.current.medium) {
                                    Text(
                                        stringResource(it.name),
                                        fontWeight = FontWeight.Normal, fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    },
                    navigationIcon = {
                        if (currentRoute in mainRoutes) {
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
                    DrawerContent(navController) { scaffoldState.drawerState.close() }
                }
            }
        ) {
            Routing(navController)
        }
    }
}

@Composable
fun Routing(navController: NavHostController) {
    val serverPackViewModel = viewModel<ServerPackViewModel>()
    val packViewModel = viewModel<PackViewModel>()
    val knownBugsViewModel = viewModel<KnownBugsViewModel>()
    NavHost(navController, startDestination = LocalScreen.Home.route) {
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
    }
}

@Composable
fun DrawerContent(navController: NavController, closeDrawer: () -> Unit) {
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

        val currentRoute = navController.currentBackStackEntryAsState().value?.arguments
            ?.getString(KEY_ROUTE)?.replaceAfter('/', "")?.replace("/", "")

        LocalScreen.displayable.forEach {
            DrawerButton(
                icon = it.icon,
                label = stringResource(it.name),
                isSelected = it.route == currentRoute,
                action = {
                    if (it.route != currentRoute) {
                        navController.popBackStack(navController.graph.startDestination, false)
                        navController.navigate(it.route)
                    }
                    closeDrawer()
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