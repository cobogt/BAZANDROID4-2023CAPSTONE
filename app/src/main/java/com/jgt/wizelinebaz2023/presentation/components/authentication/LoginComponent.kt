package com.jgt.wizelinebaz2023.presentation.components.authentication

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jgt.wizelinebaz2023.domain.AuthenticationViewModel

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginComponent() {
    val viewModel: AuthenticationViewModel = viewModel()
    val loginState = viewModel.loginComponentState.collectAsState()

    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .size(48.dp)
    ) {

        TextField(value = "Email", onValueChange = {})
        TextField(value = "Password", onValueChange = {})
        Spacer(modifier = Modifier.height(20.dp))
        Row(Modifier.fillMaxWidth()) {
            Button(onClick = { /*TODO*/ }) {
                Text("Registrarse")
            }
            Spacer(modifier = Modifier.width(5.dp))
            Button(onClick = { /*TODO*/ }) {
                Text("Iniciar sesión")
            }
        }
    }
}