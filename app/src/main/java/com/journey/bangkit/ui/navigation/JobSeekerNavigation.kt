package com.journey.bangkit.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.journey.bangkit.ui.screen.detail.DetailScreen
import com.journey.bangkit.ui.screen.home.jobseeker.HomeJobSeekerScreen
import com.journey.bangkit.ui.screen.job.apply.JobApplyScreen
import com.journey.bangkit.ui.screen.profile.jobseeker.ProfileJobSeekerScreen

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.jobSeekerGraph(navController: NavController, setBottomBarState: (Boolean) -> Unit, setSearchActive: (Boolean) -> Unit, isSearchActive: Boolean) {
    navigation(startDestination = Screen.HomeJobSeeker.route, route = JobSeekerNavigation.JOB_SEEKER_ROUTE) {
        composable(Screen.HomeJobSeeker.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.JobApply.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                    Screen.ProfileJobSeeker.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                    else -> null
                }
            }
        ) {
            LaunchedEffect(Unit) {
                setBottomBarState(true)
            }
            HomeJobSeekerScreen(
                navigateToDetail = { vacancyId ->
                    navController.navigate(Screen.DetailVacancy.createRoute(vacancyId))
                },
                setSearchActive = { isSearchActive ->
                    setSearchActive(isSearchActive)
                },
                isSearchActive = isSearchActive
            )
        }
        composable(Screen.ProfileJobSeeker.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.JobApply.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                    Screen.HomeJobSeeker.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                    else -> null
                }
            }
        ) {
            ProfileJobSeekerScreen(
                navigateToLogin = {
                    navController.navigate(AuthNavigation.AUTH_ROUTE) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(Screen.JobApply.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.HomeJobSeeker.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                    Screen.ProfileJobSeeker.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                    else -> null
                }
            }
        ) {
            JobApplyScreen()
        }
        composable(Screen.DetailVacancy.route,
            arguments = listOf(navArgument("vacancyId") { type = NavType.StringType }),
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.HomeJobSeeker.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, animationSpec = tween(500))
                    else -> null
                }
            },
            exitTransition = {
                when (initialState.destination.route) {
                    Screen.DetailVacancy.route ->
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down, animationSpec = tween(500))
                    else -> null
                }
            }
        ) {
            val id = it.arguments?.getString("vacancyId") ?: ""
            LaunchedEffect(Unit) {
                setBottomBarState(false)
            }
            DetailScreen(
                vacancyId = id,
                isPreviousBack = navController.previousBackStackEntry,
                doBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}

object JobSeekerNavigation {
    const val JOB_SEEKER_ROUTE = "job_seeker_route"
}