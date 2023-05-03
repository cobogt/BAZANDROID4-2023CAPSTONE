package com.jgt.content.movies.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jgt.core.AppStateStore
import com.jgt.core.mvi.Action
import com.jgt.core.mvi.ActivityWithViewModelStoreInterface
import com.jgt.core.mvi.navigationCatalog.NavigationCatalog
import com.jgt.core.sharedActions.NavigationActions
import com.jgt.core.sharedStates.UserState
import com.jgt.content.movies.domain.AuthenticationViewModel
import com.jgt.content.movies.presentation.components.authentication.LoginComponent
import com.jgt.content.movies.presentation.components.authentication.SignUpComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
class AuthenticationActivity:
    ComponentActivity(), com.jgt.core.mvi.ActivityWithViewModelStoreInterface {
    override val viewModelStateStore: AuthenticationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val coroutineScope = rememberCoroutineScope()

            Column {
                NavHost(navController = navController, startDestination = "/login") {
                    composable("/login") { LoginComponent() }
                    composable("/signup") { SignUpComponent() }
                }
            }

            LaunchedEffect( lifecycleScope ) {
                coroutineScope.launch {
                    viewModelStateStore.currenAction.collect {
                        if( it is com.jgt.core.sharedActions.NavigationActions.NavigateToCompose )
                            navController.navigate( it.composePath )
                    }
                }
            }
        }

        // Cargamos el estado inicial de la vista
        viewModelStateStore
            .dispatch( com.jgt.core.mvi.Action.LoadStateAction )

        CoroutineScope( Dispatchers.IO ).launch {
            com.jgt.core.AppStateStore.userState.first().also {
                Log.e("AuthenticationActivity", "$it")

                if( it is com.jgt.core.sharedStates.UserState.LoggedIn ) {
                    viewModelStateStore.dispatch(
                        com.jgt.core.sharedActions.NavigationActions.NavigateToActivity(
                            com.jgt.core.mvi.navigationCatalog.NavigationCatalog.MoviesActivityTarget().className
                        )
                    )

                    finish()
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
}
