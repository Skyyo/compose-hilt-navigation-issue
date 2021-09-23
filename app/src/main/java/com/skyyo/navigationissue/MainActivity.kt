package com.skyyo.navigationissue

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.skyyo.navigationissue.ui.theme.NavigationIssueTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay


const val LOGGING_TAG = "navIssue"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bottomBarScreens = listOf(
            Destination.Tab1,
            Destination.Tab2,
            Destination.Tab3,
        )

        setContent {
            NavigationIssueTheme {

                val selectedTab = rememberSaveable { mutableStateOf(0) }
                val navController = rememberNavController()

                LaunchedEffect(Unit) {
                    imitateFastNavigation(navController, selectedTab)
                }

                Scaffold(
                    bottomBar = {
                        BottomNavigation(backgroundColor = Color.DarkGray) {
                            bottomBarScreens.forEachIndexed { index, screen ->
                                BottomNavigationItem(
                                    icon = {
                                        Icon(
                                            Icons.Filled.Favorite,
                                            contentDescription = null
                                        )
                                    },
                                    label = { Text(stringResource(screen.resourceId)) },
                                    selected = index == selectedTab.value,
                                    onClick = {
                                        if (index == selectedTab.value) return@BottomNavigationItem
                                        selectedTab.value = index
                                        navController.navigateToRootDestination(
                                            route = screen.route,
                                            popUpToRoute = Destination.Tab1.route
                                        )
                                    }
                                )
                            }
                        }
                    },
                    content = { innerPadding ->
                        PopulatedNavHost(
                            startDestination = Destination.Tab1.route,
                            innerPadding = innerPadding,
                            navController = navController,
                            onBackPressIntercepted = {
                                selectedTab.value = 0
                                navController.navigateToRootDestination(
                                    route = Destination.Tab1.route,
                                    popUpToRoute = Destination.Tab1.route
                                )
                            }
                        )
                    })
            }
        }
    }


    // please take a look at logs using "navIssue" tag. The viewModel instances won't be the same
    // for second tab
    private suspend fun imitateFastNavigation(
        navController: NavHostController,
        selectedTab: MutableState<Int>
    ) {
        delay(2000)
        repeat(30) { iteration ->
            // if number is even we go navigate to 1 tab, and to 2 tab otherwise
            if (iteration % 2 == 0) {
                delay(200)
                selectedTab.value = 1
                navController.navigateToRootDestination(
                    route = Destination.Tab2.route,
                    popUpToRoute = Destination.Tab1.route
                )
            } else {
                delay(200)
                selectedTab.value = 0
                navController.navigateToRootDestination(
                    route = Destination.Tab1.route,
                    popUpToRoute = Destination.Tab1.route
                )
            }
        }
    }
}

//popUpToRoute - should always be the start destination of the bottomBar, not app
fun NavController.navigateToRootDestination(
    route: String,
    popUpToRoute: String
) {
    navigate(route) {
        popUpTo(popUpToRoute) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
