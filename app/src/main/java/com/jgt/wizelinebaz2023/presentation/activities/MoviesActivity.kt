package com.jgt.wizelinebaz2023.presentation.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jgt.wizelinebaz2023.core.AppStateStore
import com.jgt.wizelinebaz2023.core.mvi.ActivityWithViewModelStoreInterface
import com.jgt.wizelinebaz2023.core.mvi.navigationCatalog.NavigationCatalog
import com.jgt.wizelinebaz2023.core.sharedActions.NavigationActions
import com.jgt.wizelinebaz2023.core.sharedActions.UserActions
import com.jgt.wizelinebaz2023.core.sharedStates.UserState
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

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController   = rememberNavController()
            val coroutineScope  = rememberCoroutineScope()
            val currentUser     = AppStateStore.userState.collectAsState().value
            var selectedItem by remember { mutableStateOf("latest") }
            val categories = mapOf(
                "upcoming"  to "Próximas",
                "top_rated" to "Mejor valoradas",
                "popular"   to "Populares",
            )

            Scaffold(
                topBar = {
                    if( currentUser is UserState.LoggedIn ) {
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
                                    Text(text = currentUser.user.email, fontSize = 3.em, textAlign = TextAlign.Center)
                                }
                            }
                            Button(
                                onClick = {
                                viewModelStateStore.dispatch(
                                    UserActions.LogoutAction
                                )

                                viewModelStateStore.dispatch(
                                    NavigationActions.NavigateToActivity(
                                        NavigationCatalog.AuthActivityTarget().className
                                    )
                                )

                                finish()
                            },
                                Modifier
                                    .fillMaxWidth(.5F)
                                    .padding(10.dp)

                            ) {
                                Text("Cerrar sesión")
                            }
                        }
                    }
                },
                bottomBar = {
                    BottomNavigation {
                        categories.forEach {item ->
                            BottomNavigationItem(
                                icon = {
                                    Icon(
                                        Icons.Filled.Favorite,
                                        contentDescription = null)
                                       },
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
            ) { innerPadding ->

                NavHost(
                    navController = navController,
                    startDestination = "/list/upcoming",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable("/list/upcoming")  { MovieListComponent("upcoming") }
                    composable("/list/top_rated") { MovieListComponent("top_rated") }
                    composable("/list/popular")   { MovieListComponent("popular") }
                    composable("/detail")         { MovieDetailComponent() }
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