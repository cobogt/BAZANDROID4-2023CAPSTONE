package com.jgt.wizelinebaz2023.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jgt.wizelinebaz2023.domain.AuthenticationViewModel
import com.jgt.wizelinebaz2023.presentation.components.authentication.LoginComponent
import com.jgt.wizelinebaz2023.presentation.components.authentication.SignUpComponent

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
class AuthenticationActivity: ComponentActivity() {
    private val viewModel by viewModels<AuthenticationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            Column {
                NavHost(navController = navController, startDestination = "/login") {
                    composable("/login") { LoginComponent() }
                    composable("/signup") { SignUpComponent() }
                }

                Text("HOLA MUNDO")
            }
        }
    }
}