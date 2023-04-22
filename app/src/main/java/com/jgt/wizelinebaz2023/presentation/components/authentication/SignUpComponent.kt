package com.jgt.wizelinebaz2023.presentation.components.authentication

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.jgt.wizelinebaz2023.R
import com.jgt.wizelinebaz2023.core.mvi.ActivityWithViewModelStoreInterface
import com.jgt.wizelinebaz2023.core.mvi.navigationCatalog.NavigationCatalog
import com.jgt.wizelinebaz2023.core.sharedActions.NavigationActions
import com.jgt.wizelinebaz2023.core.sharedActions.UserActions
import com.jgt.wizelinebaz2023.domain.AuthenticationViewModel
import com.jgt.wizelinebaz2023.presentation.actions.SignUpComponentActions
import com.jgt.wizelinebaz2023.presentation.states.SignupState
/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpComponent() {
    val currentActivity = LocalContext.current as Activity
    val viewModel: AuthenticationViewModel = (currentActivity as ActivityWithViewModelStoreInterface)
        .viewModelStateStore as AuthenticationViewModel

    var signUpComponentState by remember {
        mutableStateOf<SignupState>( SignupState.SignupData() )
    }

    LaunchedEffect( Unit ) {
        viewModel.currenAction.collect {
            signUpComponentState = signUpComponentState.reduce( it )

            if( it is UserActions.ResultUserActions.AccountCreatedAction ) {
                viewModel.dispatch(
                    NavigationActions.NavigateToActivity(
                        NavigationCatalog.MoviesActivityTarget().className
                    )
                )

                currentActivity.finish()
            }

            Log.e("SignUpComponent", "Action $it")
        }
    }

    // Variables locales
    var email           by rememberSaveable { mutableStateOf("") }
    var password        by rememberSaveable { mutableStateOf("") }
    var hasError        by rememberSaveable { mutableStateOf(false) }
    var errorMessage    by rememberSaveable { mutableStateOf("") }
    var passwordRepeat  by rememberSaveable { mutableStateOf("") }

    // Aplanado de estados

    val currentState = signUpComponentState

    when ( currentState ) {
        is SignupState.SignupData -> {
            email           = currentState.email
            password        = currentState.password
            passwordRepeat  = currentState.passwordRepeat
            hasError        = false
            errorMessage    = ""
        }
        is SignupState.SignupError -> {
            hasError       = true
            email          = currentState.signupData.email
            password       = currentState.signupData.password
            passwordRepeat = currentState.signupData.passwordRepeat
            errorMessage  = currentState.errorMessage
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

        Text(stringResource(id = R.string.signup_component_label_header),
            modifier.padding(vertical = 10.dp), fontSize = 8.em)
        Spacer(modifier = Modifier.height(20.dp))
        TextField(value = email, onValueChange = {
            viewModel.dispatch(
                SignUpComponentActions.SetEmailAction( it )
            )
        }, modifier, placeholder = {
            Text("Correo electrónico")
        })

        Spacer(modifier = Modifier.height(20.dp))
        TextField(value = password, onValueChange = {
            viewModel.dispatch(
                SignUpComponentActions.SetPasswordAction( it )
            )
        }, modifier, placeholder = {
            Text("Contraseña")
        })

        Spacer(modifier = Modifier.height(20.dp))
        TextField(value = passwordRepeat, onValueChange = {
            viewModel.dispatch(
                SignUpComponentActions.SetPasswordRepeatAction( it )
            )
        }, modifier, placeholder = {
            Text("Confirmar contraseña")
        })
        Spacer(modifier = Modifier.height(20.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Spacer(modifier = Modifier.width(5.dp))
            Button(onClick = {
                viewModel.dispatch(
                    UserActions.CreateAccountAction( email, password )
                )
            }, enabled = ! hasError &&
                    email.isNotEmpty() &&
                    password.isNotEmpty() &&
                    passwordRepeat.isNotEmpty() ) {
                Text(stringResource(id = R.string.signup_component_button_signup))
            }
        }

        // Determina si muestra el mensaje de error
        if( hasError ) {
            Spacer(modifier = Modifier.width(5.dp))
            Text(errorMessage, color = Color.Red)
        }

        Spacer(modifier = Modifier.width(5.dp))
        Text(
            stringResource(id = R.string.signup_component_label_login),
            color = Color.Blue,
            modifier = Modifier
                .padding(vertical = 15.dp)
                .fillMaxWidth()
                .clickable {
                    viewModel.dispatch(
                        NavigationActions.NavigateToCompose("/login")
                    )
                }
        )
    }
}

@Composable
@Preview( showBackground = true )
fun SignupComponentePreview() {
    SignUpComponent()
}
