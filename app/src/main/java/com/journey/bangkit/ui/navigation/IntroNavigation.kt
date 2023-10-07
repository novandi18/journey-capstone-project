package com.journey.bangkit.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import androidx.navigation.navigation
import com.journey.bangkit.ui.screen.onboarding.OnBoardingScreen

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.introGraph(navController: NavController) {
    navigation(startDestination = Screen.OnBoarding.route, route = IntroNavigation.INTRO_ROUTE) {
        composable(Screen.OnBoarding.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.Started.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                    else -> null
                }
            }
        ) {
            OnBoardingScreen(
                navigateToStarted = {
                    navController.navigate(AuthNavigation.AUTH_ROUTE) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

object IntroNavigation {
    const val INTRO_ROUTE = "intro_route"
}