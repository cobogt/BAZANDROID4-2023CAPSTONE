package com.jgt.authentication.presentation.activities

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
import com.jgt.authentication.domain.AuthenticationViewModel
import com.jgt.authentication.presentation.components.LoginComponent
import com.jgt.authentication.presentation.components.SignUpComponent
import com.jgt.core.mvi.Action
import com.jgt.core.mvi.ActivityWithViewModelStoreInterface
import com.jgt.core.sharedActions.NavigationActions
import kotlinx.coroutines.launch

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
class AuthenticationActivity:
    ComponentActivity(), ActivityWithViewModelStoreInterface {
    override val viewModelStateStore: AuthenticationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e("LoginComponent", "onCreate")

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
                        if( it is NavigationActions.NavigateToCompose )
                            navController.navigate( it.composePath )
                    }
                }
            }
        }

        // Cargamos el estado inicial de la vista
        viewModelStateStore.dispatch( Action.LoadStateAction )
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
