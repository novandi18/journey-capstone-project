package com.journey.bangkit.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.journey.bangkit.data.source.JourneyDataSource
import com.journey.bangkit.ui.navigation.NavigationItem
import com.journey.bangkit.ui.theme.Blue40
import com.journey.bangkit.ui.theme.DarkGray40
import com.journey.bangkit.ui.theme.JourneyTheme
import com.journey.bangkit.ui.theme.Light

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    bottomBarState: Boolean,
    navigationItems: List<NavigationItem>,
    currentRoute: String?
) {
    AnimatedVisibility(
        visible = bottomBarState,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        NavigationBar(
            modifier = modifier
                .fillMaxWidth()
                .shadow(elevation = 16.dp),
            containerColor = Light
        ) {
            navigationItems.map { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = stringResource(id = item.contentDescription)
                        )
                    },
                    selected = currentRoute == item.screen.route,
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Blue40,
                        selectedTextColor = Blue40,
                        selectedIconColor = Light,
                        unselectedTextColor = DarkGray40,
                        unselectedIconColor = DarkGray40
                    ),
                    label = {
                        Text(
                            text = stringResource(id = item.title),
                            fontWeight = FontWeight.Bold
                        )
                    },
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
private fun BottomBarPreview() {
    JourneyTheme {
        BottomBar(
            navController = rememberAnimatedNavController(),
            bottomBarState = true,
            navigationItems = JourneyDataSource.navItemsJobSeeker,
            currentRoute = "home_jobseeker"
        )
    }
}