package com.loc.newsapp.presentation.news_navigator

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.loc.newsapp.R
import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.presentation.bookmark.BookMarkViewModel
import com.loc.newsapp.presentation.bookmark.BookmarkScreen
import com.loc.newsapp.presentation.details.DetailScreen
import com.loc.newsapp.presentation.details.DetailsEvent
import com.loc.newsapp.presentation.details.DetailsViewModel
import com.loc.newsapp.presentation.home.HomeScreen
import com.loc.newsapp.presentation.home.HomeViewModel
import com.loc.newsapp.presentation.navgraph.Route
import com.loc.newsapp.presentation.navgraph.Route.HomeScreen
import com.loc.newsapp.presentation.news_navigator.components.BottomNavigationItem
import com.loc.newsapp.presentation.news_navigator.components.NewsBottomNavigation
import com.loc.newsapp.presentation.search.SearchScreen
import com.loc.newsapp.presentation.search.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsNavigator() {
    val bottomNavigationItem = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmark")
        )
    }

    val navController = rememberNavController()
    val backstackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }

    selectedItem = when (backstackState?.destination?.route) {
        "homeScreen" -> 0
        "searchScreen" -> 1
        "bookmarkScreen" -> 2
        else -> 0
    }

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        NewsBottomNavigation(items = bottomNavigationItem, selected = selectedItem, onItemClick = { index ->
            when (index) {
                0 -> navigateToTab(navController = navController, route = HomeScreen.route)
                1 -> navigateToTab(navController = navController, route = Route.SearchScreen.route)
                2 -> navigateToTab(
                    navController = navController,
                    route = Route.BookmarkScreen.route
                )

            }
        })
    }) {
        val bottomPadding = it.calculateBottomPadding()

        NavHost(
            navController = navController,
            startDestination = HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = HomeScreen.route) {
                val homeViewModel: HomeViewModel = hiltViewModel()
                val articles = homeViewModel.news.collectAsLazyPagingItems()
                HomeScreen(
                    articles = articles, navigateToSearch = {
                        navigateToTab(
                            navController = navController,
                            route = Route.SearchScreen.route
                        )
                    }, navigateToDetails = { article ->
                        navigateToDetails(navController = navController, article = article)
                    }
                )
            }
            composable(route = Route.SearchScreen.route) {
                val searchViewModel: SearchViewModel = hiltViewModel()
                val state = searchViewModel.state.value
                SearchScreen(
                    state = state,
                    event = searchViewModel::onEvent,
                    navigateToDetails = {
                        navigateToDetails(navController = navController, article = it)
                    })
            }
            composable(route = Route.DetailsScreen.route) {
                val detailViewModel: DetailsViewModel = hiltViewModel()
                // TODO: Handle sideEffect
                navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article") //get the data from the previous screen
                    ?.let { article ->
                        DetailScreen(
                            article = article,
                            event = detailViewModel::onEvent,
                            navigateUp = {
                                navController.navigateUp()
                                detailViewModel.onEvent(DetailsEvent.RemoveSideEffect)
                            })
                    }
            }
            composable(route = Route.BookmarkScreen.route) {
                val bookmarkViewModel: BookMarkViewModel = hiltViewModel()
                BookmarkScreen(
                    state = bookmarkViewModel.state.value,
                    navigateToDetails = {
                        navigateToDetails(navController = navController, article = it)
                    }
                )
            }
        }
    }
}


private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true //create a new instance of homeScreen
        }
    }

}

private fun navigateToDetails(navController: NavController, article: Article) {
    navController.currentBackStackEntry?.savedStateHandle?.set(
        "article",
        article
    ) // adding de the article
    navController.navigate(route = Route.DetailsScreen.route)
}



