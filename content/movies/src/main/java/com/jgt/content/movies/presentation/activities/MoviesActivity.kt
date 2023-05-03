package com.jgt.content.movies.presentation.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreTime
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jgt.content.movies.R
import com.jgt.core.AppStateStore
import com.jgt.core.mvi.ActivityWithViewModelStoreInterface
import com.jgt.core.mvi.navigationCatalog.NavigationCatalog
import com.jgt.core.sharedActions.NavigationActions
import com.jgt.core.sharedActions.UserActions
import com.jgt.core.sharedStates.UserState
import com.jgt.content.movies.domain.MoviesViewModel
import com.jgt.content.movies.presentation.components.movies.CheckConnectionComponent
import com.jgt.content.movies.presentation.components.movies.MovieDetailComponent
import com.jgt.content.movies.presentation.components.movies.MovieListComponent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
@AndroidEntryPoint
class MoviesActivity: ComponentActivity(), com.jgt.core.mvi.ActivityWithViewModelStoreInterface {
    override val viewModelStateStore by viewModels<MoviesViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController   = rememberNavController()
            val coroutineScope  = rememberCoroutineScope()
            val currentUser     = com.jgt.core.AppStateStore.userState.collectAsState().value
            var selectedItem by remember { mutableStateOf("latest") }
            val categories = mapOf(
                "upcoming"  to Pair("Próximas", Icons.Filled.MoreTime),
                "top_rated" to Pair("Mejor valoradas", Icons.Filled.PlaylistAdd),
                "popular"   to Pair("Populares", Icons.Filled.StarRate),
            )

            Scaffold(
                topBar = {
                    if( currentUser is com.jgt.core.sharedStates.UserState.LoggedIn ) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                Modifier.padding( start = 10.dp, end = 10.dp ),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = "Usuario actual", fontSize = 3.em)
                                Row() {
                                    Icon(imageVector = Icons.Filled.VerifiedUser,
                                        contentDescription = "User icon" )
                                    Text(text = currentUser.user.email,
                                        fontSize = 3.em,
                                        textAlign = TextAlign.Center)
                                }
                            }
                            Button(
                                onClick = {
                                viewModelStateStore.dispatch(
                                    com.jgt.core.sharedActions.UserActions.LogoutAction
                                )

                                viewModelStateStore.dispatch(
                                    com.jgt.core.sharedActions.NavigationActions.NavigateToActivity(
                                        com.jgt.core.mvi.navigationCatalog.NavigationCatalog.AuthActivityTarget().className
                                    )
                                )

                                finish()
                            },
                                Modifier
                                    .fillMaxWidth(fraction = .5F)
                                    .padding(10.dp)
                            ) { Text(
                                stringResource(id = R.string.movies_activity_logout_button),
                                color = Color.White
                            ) }
                        }
                    }
                },
                bottomBar = {
                    BottomNavigation {
                        categories.forEach {item ->
                            BottomNavigationItem(
                                icon = { Icon(item.value.second, tint = Color.White,
                                            contentDescription = null)},
                                label = { Text(item.value.first, color = Color.White) },
                                selected = selectedItem == item.key,
                                onClick = {
                                    selectedItem = item.key
                                    viewModelStateStore.dispatch(
                                        com.jgt.core.sharedActions.NavigationActions.NavigateToCompose(
                                            "/list/${item.key}"
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "/list/upcoming",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable("/no_network")     {CheckConnectionComponent()}
                    composable("/list/upcoming")  {MovieListComponent("upcoming")}
                    composable("/list/top_rated") {MovieListComponent("top_rated")}
                    composable("/list/popular")   {MovieListComponent("popular")}
                    composable(
                        route = "/movies/detail/{movieId}",
                        arguments = listOf( navArgument("movieId") {
                            type         = NavType.IntType
                            defaultValue = 0
                        })
                    ) { backStackEntry ->
                        MovieDetailComponent(
                            movieId = backStackEntry.arguments?.getInt( "movieId" ) ?: 0
                        )
                    }
                }

                LaunchedEffect( lifecycleScope ) {
                    coroutineScope.launch {
                        viewModelStateStore.currenAction.collect {
                            if( it is com.jgt.core.sharedActions.NavigationActions.NavigateToCompose )
                                navController.navigate( it.composePath )

                            if( it is com.jgt.core.sharedActions.NavigationActions.Back && navController.backQueue.size > 2 )
                                navController.popBackStack()
                        }
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.extras?.getString("navigateToCompose")?.also {
            viewModelStateStore.dispatch(
                com.jgt.core.sharedActions.NavigationActions.NavigateToCompose( it )
            )
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        viewModelStateStore.dispatch( com.jgt.core.sharedActions.NavigationActions.Back )
    }
}
