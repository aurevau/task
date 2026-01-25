package com.example.composetask.presentation.sign_in

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.composetask.LoginScreen

@Composable
fun AuthTabs(navController: NavController) {

    val tabs = listOf("sign_in", "signup")
    val titles = listOf("Login", "Sign Up")

    val currentRoute = navController
        .currentBackStackEntryAsState()
        .value?.destination?.route

    val selectedIndex = tabs.indexOf(currentRoute).coerceAtLeast(0)

    Column {
        TabRow(selectedTabIndex = selectedIndex) {
            titles.forEachIndexed { index, title ->
                Tab( selected = selectedIndex == index,
                    onClick = {
                        navController.navigate(tabs[index]) {
                            popUpTo("auth") {inclusive = false}
                            launchSingleTop = true
                        }
                    }, text = {Text(title)}
                    )
            }
        }

    }
}