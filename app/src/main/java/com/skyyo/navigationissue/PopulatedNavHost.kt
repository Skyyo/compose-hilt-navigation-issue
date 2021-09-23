package com.skyyo.navigationissue

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.skyyo.navigationissue.features.tab1.Tab1Screen
import com.skyyo.navigationissue.features.tab2.Tab2Screen
import com.skyyo.navigationissue.features.tab3.Tab3Screen

@OptIn(
    ExperimentalAnimationApi::class,
    ExperimentalMaterialApi::class,
)
@Composable
fun PopulatedNavHost(
    startDestination: String,
    innerPadding: PaddingValues,
    navController: NavHostController,
    onBackPressIntercepted: () -> Unit
) = NavHost(
    navController = navController,
    startDestination = startDestination,
    modifier = Modifier.padding(innerPadding)
) {

    composable(Destination.Tab1.route) { Tab1Screen() }
    composable(Destination.Tab2.route) {
        BackHandler(onBack = onBackPressIntercepted)
        Tab2Screen()
    }
    composable(Destination.Tab3.route) {
        BackHandler(onBack = onBackPressIntercepted)
        Tab3Screen()
    }
}