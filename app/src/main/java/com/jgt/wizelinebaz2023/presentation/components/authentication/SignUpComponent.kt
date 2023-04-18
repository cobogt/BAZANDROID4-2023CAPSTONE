package com.jgt.wizelinebaz2023.presentation.components.authentication

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.jgt.wizelinebaz2023.core.mvi.ActivityWithViewModelStoreInterface
import com.jgt.wizelinebaz2023.core.sharedActions.NavigationActions
import com.jgt.wizelinebaz2023.domain.AuthenticationViewModel

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
@Composable
fun SignUpComponent() {
    val currentActivity = LocalContext.current as Activity
    val viewModel: AuthenticationViewModel = (currentActivity as ActivityWithViewModelStoreInterface)
        .viewModelStateStore as AuthenticationViewModel

    Text("SignUp Component", Modifier.clickable {
        viewModel.dispatch(
            NavigationActions.NavigateToCompose("/login")
        )
    })
}