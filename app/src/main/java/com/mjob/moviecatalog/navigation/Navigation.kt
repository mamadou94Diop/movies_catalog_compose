package com.mjob.moviecatalog.navigation

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.primarySurface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mjob.moviecatalog.R
import com.mjob.moviecatalog.ui.screen.discovery.DiscoveryScreen
import com.mjob.moviecatalog.ui.screen.favorite.FavoriteScreen
import com.mjob.moviecatalog.ui.screen.home.HomeScreen
import com.mjob.moviecatalog.ui.screen.home.HomeViewModel
import com.mjob.moviecatalog.ui.theme.md_theme_dark_background
import com.mjob.moviecatalog.ui.theme.md_theme_light_background


sealed class Screen(val route: String, @StringRes val name: Int, val icon: ImageVector) {
    object Home : Screen(route = "route", name = R.string.home_tab, icon = Icons.Filled.Menu)
    object Discovery :
        Screen(route = "discovery", name = R.string.discovery_tab, icon = Icons.Filled.PlayArrow)

    object Favorite :
        Screen(route = "favorites", name = R.string.favorite_tab, icon = Icons.Filled.Favorite)
}

val screens = listOf(Screen.Home, Screen.Discovery, Screen.Favorite)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Scaffold() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar{
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                screens.forEach { screen ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        icon = { Icon(screen.icon, contentDescription = null ) },
                        label = { Text(text = stringResource(id = screen.name)) },
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Navigation(navController = navController, innerPadding = innerPadding)
    }

}

@Composable
fun Navigation(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Screen.Home.route) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(homeViewModel)
        }
        composable(Screen.Discovery.route) {
            DiscoveryScreen()
        }
        composable(Screen.Favorite.route) {
            FavoriteScreen()
        }
    }
}
