package com.jgt.wizelinebaz2023.presentation.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jgt.wizelinebaz2023.core.mvi.ActivityWithViewModelStoreInterface
import com.jgt.wizelinebaz2023.core.sharedActions.NavigationActions
import com.jgt.wizelinebaz2023.domain.MoviesViewModel
import com.jgt.wizelinebaz2023.presentation.components.movies.MovieDetailComponent
import com.jgt.wizelinebaz2023.presentation.components.movies.MovieListComponent
import kotlinx.coroutines.launch

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
class MoviesActivity: ComponentActivity(), ActivityWithViewModelStoreInterface {
    override val viewModelStateStore by viewModels<MoviesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val coroutineScope = rememberCoroutineScope()

            Column {
                NavHost(navController = navController, startDestination = "/list/latest") {
                    composable("/list/latest")    { MovieListComponent("latest") }
                    composable("/list/top_rated") { MovieListComponent("top_rated") }
                    composable("/list/popular")   { MovieListComponent("popular") }
                    composable("/detail")         { MovieDetailComponent() }
                }

                var selectedItem by remember { mutableStateOf("latest") }
                val categories = mapOf(
                    "latest"    to "Últimas",
                    "top_rated" to "Mejor valoradas",
                    "popular"   to "Populares",
                )

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomStart) {
                    BottomNavigation(modifier = Modifier.clip(
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    ) {
                        categories.forEach {item ->
                            BottomNavigationItem(
                                icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                                label = { Text(item.value) },
                                selected = selectedItem == item.key,
                                onClick = {
                                    selectedItem = item.key
                                    viewModelStateStore.dispatch(
                                        NavigationActions.NavigateToCompose(
                                            "/list/${item.key}"
                                        )
                                    )
                                }
                            )
                        }
                    }
                }

            }

            LaunchedEffect( lifecycleScope ) {
                coroutineScope.launch {
                    viewModelStateStore.currenAction.collect {
                        if( it is NavigationActions.NavigateToCompose )
                            navController.navigate( it.composePath )
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.extras?.getString("navigateToCompose")?.also {
            viewModelStateStore.dispatch(
                NavigationActions.NavigateToCompose( it )
            )
        }
    }
}