package com.jgt.wizelinebaz2023.core.mvi.gatesCatalog

import android.util.Log
import com.jgt.wizelinebaz2023.core.gates.Gate
import com.jgt.wizelinebaz2023.core.mvi.Action
import com.jgt.wizelinebaz2023.core.sharedActions.NavigationActions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.InetAddress

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 25/04/23.
 * * * * * * * * * * **/
object CheckInternetGate: Gate() {
    override val onErrorAction: Action = NavigationActions.Back

    override val startAction: Action =
        NavigationActions.NavigateToCompose("/no_network")

    override fun enterCondition(action: Action): Boolean =
        try {
            runBlocking {
                withContext( Dispatchers.IO ) {
                    ! InetAddress.getByName("8.8.8.8").isReachable(250)
                }
            }
        } catch (e: Exception ) {
            Log.e("CheckInternetGate", e.toString())
            true
        }
}