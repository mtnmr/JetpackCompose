package com.example.composenavigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


enum class NavigationScreen() {
    Home,
    Next
}

@Composable
fun NavigationApp() {
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        topBar = {
            MyAppBar(
                canNavigateBack = backStackEntry?.destination?.route != NavigationScreen.Home.name,
                onClick = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationScreen.Home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavigationScreen.Home.name) {
                HomeScreen(
                    onClick = { navController.navigate("${NavigationScreen.Next.name}/$it") }
                )
            }
            composable(
                "${NavigationScreen.Next.name}/{text}",
                arguments = listOf(navArgument("text") { type = NavType.StringType })
            ) {
                NextScreen(it.arguments?.getString("text").toString())
            }
        }
    }
}

@Composable
fun MyAppBar(
    canNavigateBack: Boolean,
    onClick: () -> Unit
) {
    val navigationIcon: (@Composable () -> Unit)? = {
        if (canNavigateBack) {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back"
                )
            }
        } else {
            null
        }
    }

    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        navigationIcon = navigationIcon
    )
}

@Composable
fun HomeScreen(
    onClick: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome Home!")

        Spacer(modifier = Modifier.padding(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = {
                Text(text = "Enter your name")
            },
            singleLine = true
        )
        Button(
            onClick = { onClick(name) }
        ) {
            Text(text = "Next")
        }
    }
}

@Composable
fun NextScreen(text: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hello, $text")
    }
}