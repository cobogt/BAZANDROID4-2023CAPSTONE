package com.jgt.authentication.presentation.components

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jgt.authentication.domain.AuthenticationViewModel
import com.jgt.authentication.presentation.actions.LoginComponentActions
import com.jgt.authentication.presentation.states.LoginState
import com.jgt.content.authentication.R
import com.jgt.core.gates.ExitGateAction
import com.jgt.core.gates.GateResult
import com.jgt.core.sharedActions.NavigationActions
import com.jgt.core.sharedActions.UserActions

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginComponent() {
    // Enlace desde estado del Store y hacia Dispatcher
    val currentActivity = LocalContext.current as Activity
    val viewModel: AuthenticationViewModel = (currentActivity as com.jgt.core.mvi.ActivityWithViewModelStoreInterface)
        .viewModelStateStore as AuthenticationViewModel

    var loginComponentState by remember {
        mutableStateOf<LoginState>( LoginState.LoginData() )
    }

    LaunchedEffect( Unit ) {
        viewModel.currenAction.collect {
            loginComponentState = loginComponentState.reduce( it )

            Log.e("LoginComponent", "Action $it")

            if( it is UserActions.ResultUserActions.LoggedInAction ) {
                viewModel.dispatch(
                    ExitGateAction(
                        GateResult.Success()
                    )
                )

                currentActivity.finish()
            }

        }
    }

    // Variables locales
    var email        by remember { mutableStateOf("") }
    var password     by remember { mutableStateOf("") }
    var hasError     by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Aplanado de estados

    val currentState = loginComponentState

    when ( currentState ) {
        is LoginState.LoginData -> {
            email        = currentState.email
            password     = currentState.password
            hasError     = false
            errorMessage = ""
        }
        is LoginState.LoginError -> {
            hasError     = true
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

        Text(   text = stringResource(id = R.string.login_component_label_header),
                modifier = modifier.padding(vertical = 10.dp),
                fontSize = 8.em )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(value = email, onValueChange = {
            viewModel.dispatch(
                LoginComponentActions.SetEmailAction( it )
            )
        }, modifier, placeholder = {
            Text("Correo electrónico")
        })
        Spacer(modifier = Modifier.height(20.dp))
        TextField(value = password, onValueChange = {
            viewModel.dispatch(
                LoginComponentActions.SetPasswordAction( it )
            )
        }, modifier, placeholder = {
            Text("Contraseña")
        })
        Spacer(modifier = Modifier.height(20.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Spacer(modifier = Modifier.width(5.dp))
            Button(onClick = {
                viewModel.dispatch(
                    UserActions.LogInAction( email, password )
                )
            }, enabled = ! hasError && email.isNotEmpty() && password.isNotEmpty() ) {
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
    val viewModel: AuthenticationViewModel = viewModel()

    viewModel.dispatch( LoginComponentActions.SetEmailAction("Email de prueba"))
    viewModel.dispatch( LoginComponentActions.SetPasswordAction(""))

    LoginComponent()
}

@Composable
@Preview( showBackground = true )
fun LoginComponentErrorPreview() {
    val viewModel: AuthenticationViewModel = viewModel()

    viewModel.dispatch( LoginComponentActions.SetEmailAction("Email"))

    LoginComponent()
}
