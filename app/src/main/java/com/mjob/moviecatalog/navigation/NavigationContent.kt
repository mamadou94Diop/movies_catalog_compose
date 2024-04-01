package com.mjob.moviecatalog.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.mjob.moviecatalog.ui.screen.catalog.CatalogViewModel
import com.mjob.moviecatalog.ui.screen.catalog.HomeScreen
import com.mjob.moviecatalog.ui.screen.detail.DetailScreen
import com.mjob.moviecatalog.ui.screen.detail.DetailViewModel
import com.mjob.moviecatalog.ui.screen.discovery.DiscoveryScreen
import com.mjob.moviecatalog.ui.screen.discovery.TrailerViewModel
import com.mjob.moviecatalog.ui.screen.favorite.FavoriteScreen
import com.mjob.moviecatalog.ui.screen.favorite.FavoriteViewModel


sealed class Tab(val route: String, @StringRes val name: Int, val icon: ImageVector) {
    object Catalog : Tab(route = "catalog", name = R.string.home_tab, icon = Icons.Filled.Menu)

    object Discovery :
        Tab(route = "discovery", name = R.string.discovery_tab, icon = Icons.Filled.PlayArrow)

    object Favorite :
        Tab(route = "favorites", name = R.string.favorite_tab, icon = Icons.Filled.Favorite)
}

val tabs = listOf(Tab.Catalog, Tab.Discovery, Tab.Favorite)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation(navController)
        },
        content = { innerPadding ->
            NavigationContent(innerPadding = innerPadding, navController = navController)
        }
    )

}

@Composable
private fun BottomNavigation(
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomBarVisibilityState = rememberSaveable { mutableStateOf(true) }
    bottomBarVisibilityState.value = when (navBackStackEntry?.destination?.route) {
        Tab.Catalog.route, Tab.Discovery.route, Tab.Favorite.route -> true
        else -> false
    }
    if (bottomBarVisibilityState.value) {
        NavigationBar {
            val currentDestination = navBackStackEntry?.destination
            tabs.forEach { screen ->
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    icon = { Icon(screen.icon, contentDescription = null) },
                    label = { Text(text = stringResource(id = screen.name)) },
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                           // launchSingleTop = true
                            //restoreState = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun NavigationContent(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Tab.Catalog.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Tab.Catalog.route) {
            val catalogViewModel = hiltViewModel<CatalogViewModel>()
            val navigationCallback =
                { contentId: Int -> navController.navigate(route = "${Tab.Catalog.route}/$contentId") }
            HomeScreen(catalogViewModel, navigationCallback)
        }

        composable("${Tab.Catalog.route}/{id}") {
            val detailViewModel = hiltViewModel<DetailViewModel>()
            val navigationCallback = { navController.popBackStack() }
            it.arguments?.getString("id")?.let { id ->
                DetailScreen(id = id.toInt(), viewModel = detailViewModel.apply {
                    setId(id.toInt())
                }, navigationCallback)
            }
        }

        composable(Tab.Discovery.route) {
            val trailerViewModel = hiltViewModel<TrailerViewModel>()
            DiscoveryScreen(trailerViewModel)
        }
        composable(Tab.Favorite.route) {
            val favoriteViewModel = hiltViewModel<FavoriteViewModel>()
            FavoriteScreen(favoriteViewModel)
        }
    }
}

