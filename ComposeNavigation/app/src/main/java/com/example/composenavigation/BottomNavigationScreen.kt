package com.example.composenavigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composenavigation.ui.theme.ComposeNavigationTheme

@Composable
fun FirstScreen(){
    Text(text = "first screen")
}

@Composable
fun SecondScreen(){
    Text(text = "second screen")
}

@Composable
fun ThirdScreen(){
    Text(text = "third screen")
}

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector
){
    object First : Screen("first", R.string.first_screen, Icons.Filled.Home)
    object Second : Screen("second", R.string.second_screen, Icons.Filled.Favorite)
    object Third : Screen("third", R.string.third_screen, Icons.Filled.Settings)
}

val items = listOf(Screen.First, Screen.Second, Screen.Third)

@Composable
fun BottomNavigationScreen(){
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigation() {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(stringResource(screen.resourceId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
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
    ) {innerPadding ->
        NavHost(navController, startDestination = Screen.First.route, Modifier.padding(innerPadding)) {
            composable(Screen.First.route) { FirstScreen() }
            composable(Screen.Second.route) { SecondScreen() }
            composable(Screen.Third.route) { ThirdScreen() }
        }
    }
}

@Preview
@Composable
fun BottomNavigationScreenPreview(){
    ComposeNavigationTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            BottomNavigationScreen()
        }
    }
}

