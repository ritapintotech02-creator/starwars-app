package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.data.model.imageFor
import com.example.myapplication.ui.list.PersonListScreen
import com.example.myapplication.ui.list.PersonListViewModel
import com.example.myapplication.ui.detail.PersonDetailScreen
import com.example.myapplication.ui.home.HomeScreen
import com.example.myapplication.ui.splash.SplashScreen

private const val ROUTE_SPLASH = "splash"
private const val ROUTE_HOME = "home"
private const val ROUTE_LIST = "list"
private const val ROUTE_DETAIL = "detail/{personName}"
private const val ARG_PERSON_NAME = "personName"

@Composable
fun StarWarsNavHost() {
    val navController = rememberNavController()
    val listViewModel: PersonListViewModel = viewModel()

    NavHost(navController = navController, startDestination = ROUTE_LIST) {

        composable(ROUTE_SPLASH) {
            SplashScreen(
                onFinished = {
                    navController.navigate(ROUTE_HOME) {
                        popUpTo(ROUTE_SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(ROUTE_HOME) {
            HomeScreen(onCharactersClick = { navController.navigate(ROUTE_LIST) })
        }

        composable(ROUTE_LIST) {
            PersonListScreen(
                viewModel = listViewModel,
                onPersonClick = { person ->
                    navController.navigate("detail/${person.name}")
                }
            )
        }

        composable(
            route = ROUTE_DETAIL,
            arguments = listOf(navArgument(ARG_PERSON_NAME) { type = NavType.StringType })
        ) { backStackEntry ->
            val personName = backStackEntry.arguments?.getString(ARG_PERSON_NAME)
            val uiState by listViewModel.uiState.collectAsState()
            val imageMap by listViewModel.imageMap.collectAsState()
            val person = uiState.people.find { it.name == personName }

            if (person != null) {
                PersonDetailScreen(
                    person = person,
                    imageUrl = imageMap.imageFor(person),
                    onBackClick = { navController.popBackStack() }
                )
            } else {
                navController.popBackStack()
            }
        }
    }
}