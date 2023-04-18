package com.jgt.wizelinebaz2023.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jgt.wizelinebaz2023.core.mvi.ActivityWithViewModelStoreInterface
import com.jgt.wizelinebaz2023.core.sharedActions.NavigationActions
import com.jgt.wizelinebaz2023.domain.AuthenticationViewModel
import com.jgt.wizelinebaz2023.presentation.components.authentication.LoginComponent
import com.jgt.wizelinebaz2023.presentation.components.authentication.SignUpComponent
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

        setContent {
            val navController = rememberNavController()
            val coroutineScope = rememberCoroutineScope()

            Column {
                NavHost(navController = navController, startDestination = "/login") {
                    composable("/login") { LoginComponent() }
                    composable("/signup") { SignUpComponent() }
                }

                Text("HOLA MUNDO")
            }

            LaunchedEffect( lifecycleScope ) {
                coroutineScope.launch {
                    viewModelStateStore.currenAction.collect {
                        Log.e("AuthenticationActivity", "$it")
                        if( it is NavigationActions.NavigateToCompose ) {
                            Log.e("AuthenticationActivity", "$it")

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