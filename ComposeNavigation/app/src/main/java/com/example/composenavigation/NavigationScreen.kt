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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun NavigationScreen(){
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()

    val navigationIcon: (@Composable () -> Unit)? =
        if (backStackEntry?.destination?.route != "home") {
            {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        } else {
            null
        }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name))},
                navigationIcon = navigationIcon
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ){
            composable("home"){
                HomeScreen(navController = navController)
            }
            composable(
                "next/{text}",
                arguments = listOf(navArgument("text"){ type = NavType.StringType })
            ){ 
                NextScreen(it.arguments?.getString("text").toString())
            }
        }
    }
}

@Composable
fun HomeScreen(
    navController: NavController
){
    var name by remember { mutableStateOf("")}

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
            onValueChange = {name = it},
            label = { Text(text = "what your name?") }
        )
        Button(onClick = { navController.navigate("next/$name") }) {
            Text(text = "Next")
        }
    }
}

@Composable
fun NextScreen(text:String){
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hello, $text")
    }
}