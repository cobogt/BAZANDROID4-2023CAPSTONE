package com.jgt.wizelinebaz2023

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.jgt.authentication.gates.AuthGate
import com.jgt.core.AppStateStore
import com.jgt.core.mvi.Action
import com.jgt.core.mvi.navigationCatalog.NavigationCatalog
import com.jgt.core.sharedActions.NavigationActions

class LauncherActivity : AppCompatActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Scaffold { padding ->
                Text("Cargando...", Modifier.padding( padding ))
            }
        }

        AppStateStore.dispatch(Action.LoadStateAction)

        AppStateStore.dispatch(
            NavigationActions.NavigateToActivity(
                NavigationCatalog.MoviesActivityTarget().className
            ).apply { gate = AuthGate() }
        )
    }
}