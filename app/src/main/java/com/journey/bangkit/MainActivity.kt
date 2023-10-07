package com.journey.bangkit

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.journey.bangkit.data.source.JourneyDataSource
import com.journey.bangkit.ui.navigation.AuthNavigation
import com.journey.bangkit.ui.navigation.IntroNavigation
import com.journey.bangkit.ui.navigation.JobProviderNavigation
import com.journey.bangkit.ui.navigation.JobSeekerNavigation
import com.journey.bangkit.ui.theme.JourneyTheme
import com.journey.bangkit.viewmodel.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val viewModel: OnBoardingViewModel by viewModels()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var keepSplashOnScreen = true
        installSplashScreen().setKeepOnScreenCondition { keepSplashOnScreen }
        Handler(Looper.getMainLooper()).postDelayed({
            keepSplashOnScreen = false
        }, 1500L)

        lifecycleScope.launch {
            viewModel.upsertAll()
            val state = viewModel.getAll()
            val currentBottomBarState = state.isLoginAsJobSeeker || state.isLoginAsJobProvider

            val currentNavigationItem = if (state.isLoginAsJobSeeker)
                JourneyDataSource.navItemsJobSeeker else if (state.isLoginAsJobProvider)
                JourneyDataSource.navItemsJobProvider else listOf()

            val stateStart = if (state.isLoginAsJobSeeker) JobSeekerNavigation.JOB_SEEKER_ROUTE else if (state.isLoginAsJobProvider)
                JobProviderNavigation.JOB_PROVIDER_ROUTE else if (state.isOnBoardingCompleted) AuthNavigation.AUTH_ROUTE
            else IntroNavigation.INTRO_ROUTE

            setContent {
                JourneyTheme {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        navController = rememberAnimatedNavController()
                        val bottomBarState = rememberSaveable { mutableStateOf(currentBottomBarState) }
                        val navigationItems = rememberSaveable { mutableStateOf(currentNavigationItem) }

                        JourneyApp(
                            stateStart = stateStart,
                            navController = navController,
                            bottomBarState = bottomBarState.value,
                            setBottomBarState = {
                                bottomBarState.value = it
                            },
                            setNavigationItem = {
                                navigationItems.value = it
                            },
                            navigationItem = navigationItems.value
                        )
                    }
                }
            }
        }

    }
}