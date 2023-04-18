package com.jgt.wizelinebaz2023.presentation.components.authentication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jgt.wizelinebaz2023.domain.AuthenticationViewModel
import com.jgt.wizelinebaz2023.presentation.states.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.jgt.wizelinebaz2023.R
import com.jgt.wizelinebaz2023.core.sharedActions.NavigationActions
import com.jgt.wizelinebaz2023.core.sharedActions.UserActions
import com.jgt.wizelinebaz2023.presentation.actions.LoginComponentActions
import kotlinx.coroutines.flow.asStateFlow

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginComponent( loginState: StateFlow<LoginState> ) {
    // Enlace desde estado del Store y hacia Dispatcher
    val viewModel: AuthenticationViewModel = viewModel()
    val localLoginState = loginState.collectAsState()

    // Variables locales
    var email        = ""
    var password     = ""
    var hasError    = false
    var errorMessage = ""

    // Aplanado de estados
    when ( val currentState = localLoginState.value ) {
        is LoginState.LoginData -> {
            email    = currentState.email
            password = currentState.password
        }
        is LoginState.LoginError -> {
            hasError    = true
            email        = currentState.loginData.email
            password     = currentState.loginData.password
            errorMessage = currentState.errorMessage
        }
    }

    // Contenido del componente
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .fillMaxHeight()
    ) {
        val modifier = Modifier.fillMaxWidth()

        Text(stringResource(id = R.string.login_component_label_header), modifier.padding(vertical = 10.dp), fontSize = 8.em)

        TextField(value = email, onValueChange = {
            viewModel.dispatch(
                LoginComponentActions.SetEmailAction( it )
            )
        }, modifier)
        TextField(value = password, onValueChange = {
            viewModel.dispatch(
                LoginComponentActions.SetPasswordAction( it )
            )
        }, modifier)
        Spacer(modifier = Modifier.height(20.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Spacer(modifier = Modifier.width(5.dp))
            Button(onClick = {
                viewModel.dispatch(
                    UserActions.SignInAction( email, password )
                )
            }, enabled = ! hasError ) {
                Text(stringResource(id = R.string.login_component_button_login))
            }
        }

        // Determina si muestra el mensaje de error
        if( hasError ) {
            Spacer(modifier = Modifier.width(5.dp))
            Text(errorMessage, color = Color.Red)
        }
        
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            stringResource(id = R.string.login_component_label_signup),
            color = Color.Blue,
            modifier = Modifier
                .padding(vertical = 15.dp)
                .fillMaxWidth()
                .clickable {
                    viewModel.dispatch(
                        NavigationActions.NavigateToCompose("/signup")
                    )
                }
        )
    }
}

@Composable
@Preview( showBackground = true )
fun LoginComponentPreview() {
    val loginData = LoginState.LoginData("Email de prueba", "Password123")
    val loginState = MutableStateFlow<LoginState>(
        loginData
    ).asStateFlow()

    LoginComponent( loginState )
}

@Composable
@Preview( showBackground = true )
fun LoginComponentErrorPreview() {
    val loginData = LoginState.LoginData("Email de prueba", "Password123")
    val loginState = MutableStateFlow<LoginState>(
        // loginData
        LoginState.LoginError(
            loginData, "No se puede iniciar sesión"
        )
    ).asStateFlow()

    LoginComponent( loginState )
}