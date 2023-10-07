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
import com.journey.bangkit.ui.screen.add.AddVacancyScreen
import com.journey.bangkit.ui.screen.home.jobprovider.HomeJobProviderScreen
import com.journey.bangkit.ui.screen.job.applicant.ApplicantDetailScreen
import com.journey.bangkit.ui.screen.job.applicant.JobApplicantScreen
import com.journey.bangkit.ui.screen.profile.jobprovider.ProfileJobProviderScreen

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.jobProviderGraph(navController: NavController, setBottomBarState: (Boolean) -> Unit) {
    navigation(startDestination = Screen.HomeJobProvider.route, route = JobProviderNavigation.JOB_PROVIDER_ROUTE) {
        composable(Screen.HomeJobProvider.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.JobApplicant.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                    Screen.ProfileJobProvider.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                    else -> null
                }
            }
        ) {
            LaunchedEffect(Unit) {
                setBottomBarState(true)
            }
            HomeJobProviderScreen(
                navigateToAdd = {
                    navController.navigate(Screen.AddVacancy.route)
                }
            )
        }
        composable(Screen.JobApplicant.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.HomeJobProvider.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                    Screen.ProfileJobProvider.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                    else -> null
                }
            }
        ) {
            LaunchedEffect(Unit) {
                setBottomBarState(true)
            }
            JobApplicantScreen(
                navigateToDetail = { vacancyId ->
                    navController.navigate(
                        Screen.ApplicantDetail.createRoute(vacancyId)
                    )
                }
            )
        }
        composable(Screen.ProfileJobProvider.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.HomeJobProvider.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                    Screen.JobApplicant.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                    else -> null
                }
            }
        ) {
            ProfileJobProviderScreen(
                navigateToLogin = {
                    navController.navigate(AuthNavigation.AUTH_ROUTE) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(Screen.AddVacancy.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.HomeJobProvider.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, animationSpec = tween(500))
                    else -> null
                }
            },
            exitTransition = {
                when (initialState.destination.route) {
                    Screen.AddVacancy.route ->
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down, animationSpec = tween(500))
                    else -> null
                }
            }
        ) {
            LaunchedEffect(Unit) {
                setBottomBarState(false)
            }
            AddVacancyScreen(
                isPreviousBack = navController.previousBackStackEntry,
                doBack = {
                    navController.navigateUp()
                }
            )
        }
        composable(Screen.ApplicantDetail.route,
            arguments = listOf(navArgument("vacancyId") { type = NavType.StringType }),
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.JobApplicant.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, animationSpec = tween(500))
                    else -> null
                }
            },
            exitTransition = {
                when (initialState.destination.route) {
                    Screen.ApplicantDetail.route ->
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down, animationSpec = tween(500))
                    else -> null
                }
            }
        ) {
            val id = it.arguments?.getString("vacancyId") ?: ""
            LaunchedEffect(Unit) {
                setBottomBarState(false)
            }
            ApplicantDetailScreen(
                vacancyId = id,
                isPreviousBack = navController.previousBackStackEntry,
                doBack = { navController.navigateUp() }
            )
        }
    }
}

object JobProviderNavigation {
    const val JOB_PROVIDER_ROUTE = "job_provider_route"
}