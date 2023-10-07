package com.journey.bangkit.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.journey.bangkit.data.model.UserJobSeeker
import com.journey.bangkit.data.source.JourneyDataSource
import com.journey.bangkit.ui.screen.login.jobprovider.LoginJobProviderScreen
import com.journey.bangkit.ui.screen.login.jobseeker.LoginJobSeekerScreen
import com.journey.bangkit.ui.screen.register.jobprovider.RegisterJobProviderScreen
import com.journey.bangkit.ui.screen.register.jobseeker.RegisterJobSeekerScreen
import com.journey.bangkit.ui.screen.skill.SkillScreen
import com.journey.bangkit.ui.screen.started.StartedScreen

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.authGraph(navController: NavController, setNavigationItem: (List<NavigationItem>) -> Unit) {
    navigation(startDestination = Screen.Started.route, route = AuthNavigation.AUTH_ROUTE) {
        composable(Screen.Started.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.OnBoarding.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                    Screen.LoginJobSeeker.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                    Screen.LoginJobProvider.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                    else -> null
                }
            }
        ) {
            StartedScreen(
                navigateToJobSeeker = {
                    navController.navigate(Screen.LoginJobSeeker.route)
                },
                navigateToJobProvider = {
                    navController.navigate(Screen.LoginJobProvider.route)
                }
            )
        }
        composable(Screen.LoginJobSeeker.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.Started.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                    Screen.RegisterJobSeeker.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                    else -> null
                }
            }
        ) {
            LoginJobSeekerScreen(
                backToStarted = {
                    navController.navigateUp()
                },
                navigateToRegister = {
                    navController.navigate(Screen.RegisterJobSeeker.route)
                },
                navigateToHome = {
                    setNavigationItem(JourneyDataSource.navItemsJobSeeker)
                    navController.navigate(Screen.HomeJobSeeker.route)
                }
            )
        }
        composable(Screen.LoginJobProvider.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.Started.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                    Screen.RegisterJobProvider.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                    else -> null
                }
            }
        ) {
            LoginJobProviderScreen(
                backToStarted = {
                    navController.navigateUp()
                },
                navigateToRegister = {
                    navController.navigate(Screen.RegisterJobProvider.route)
                },
                navigateToHome = {
                    setNavigationItem(JourneyDataSource.navItemsJobProvider)
                    navController.navigate(Screen.HomeJobProvider.route)
                }
            )
        }
        composable(Screen.RegisterJobSeeker.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.LoginJobSeeker.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                    else -> null
                }
            },
        ) {
            RegisterJobSeekerScreen(
                backToLogin = {
                    navController.navigateUp()
                },
                navigateToSkill = { dataFromRegister ->
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "dataFromRegister",
                        value = dataFromRegister
                    )
                    navController.navigate(Screen.SkillJobSeeker.route)
                }
            )
        }
        composable(Screen.RegisterJobProvider.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.LoginJobProvider.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                    else -> null
                }
            }
        ) {
            RegisterJobProviderScreen(
                backToLogin = {
                    navController.navigateUp()
                }
            )
        }
        composable(Screen.SkillJobSeeker.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.LoginJobSeeker.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                    Screen.HomeJobSeeker.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                    else -> null
                }
            },
        ) {
            val dataFromRegister = navController.previousBackStackEntry?.savedStateHandle?.get<UserJobSeeker>("dataFromRegister")
            SkillScreen(
                dataFromRegister = dataFromRegister,
                backToLogin = {
                    navController.navigate(Screen.LoginJobSeeker.route)
                }
            )
        }
    }
}

object AuthNavigation {
    const val AUTH_ROUTE = "auth_route"
}