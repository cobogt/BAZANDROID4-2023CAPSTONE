package com.jgt.content.movies.presentation.components.movies

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SignalWifiConnectedNoInternet4
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.jgt.core.gates.ExitGateAction
import com.jgt.core.gates.GateResult
import com.jgt.core.mvi.ActivityWithViewModelStoreInterface
import com.jgt.content.movies.domain.MoviesViewModel

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 25/04/23.
 * * * * * * * * * * **/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckConnectionComponent() {
    val currentActivity = LocalContext.current as Activity
    val viewModel: MoviesViewModel = (currentActivity as com.jgt.core.mvi.ActivityWithViewModelStoreInterface)
        .viewModelStateStore as MoviesViewModel

    Scaffold { padding ->
        Column(Modifier.padding( padding ).fillMaxWidth().fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Filled.SignalWifiConnectedNoInternet4, "No internet",
                Modifier.size(140.dp), Color.Gray
            )
            Text(text = "No hay conexión a internet",
                fontWeight = FontWeight.Bold,
                fontSize = 5.em)

            Text("Revise su conexión a internet",
                Modifier.padding(vertical = 10.dp),
                fontSize = 3.em)

            Button(onClick = {
                viewModel.dispatch(com.jgt.core.gates.ExitGateAction(com.jgt.core.gates.GateResult.Error()))
            }, Modifier.height(65.dp).width(160.dp)) {
                Text("Continuar", fontSize = 3.em)
            }
        }
    }
}