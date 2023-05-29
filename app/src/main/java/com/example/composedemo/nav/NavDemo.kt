package com.example.composedemo.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composedemo.nav.sample.AccountScreen
import com.example.composedemo.nav.sample.ActionType
import com.example.composedemo.nav.sample.LogInScreen
import com.example.composedemo.nav.sample.WelcomeScreen

@Composable
fun NavDemo(navController: NavHostController = rememberNavController()) {
    Box {
        NavHost(navController = navController, startDestination = "welcome") {
            composable("welcome") {
                WelcomeScreen {
                    when (it) {
                        ActionType.Account -> navController.navigate("account")
                        ActionType.Login -> navController.navigate("logIn")
                        else -> {
                        }
                    }
                }
            }
            composable("logIn") {
                LogInScreen { userName, password ->
                    navController.navigate("account")
                }
            }
            composable("account") {
                AccountScreen(userName = "", password = "") {
                    navController.popBackStack()
                }
            }
        }
    }
}