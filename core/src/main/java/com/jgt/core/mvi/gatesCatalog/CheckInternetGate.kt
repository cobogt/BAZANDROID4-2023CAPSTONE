package com.jgt.core.mvi.gatesCatalog

import android.util.Log
import com.jgt.core.gates.Gate
import com.jgt.core.mvi.Action
import com.jgt.core.sharedActions.NavigationActions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.InetAddress

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 25/04/23.
 * * * * * * * * * * **/
object CheckInternetGate: Gate() {
    var dispatcher = Dispatchers.IO
    override val onErrorAction: Action = NavigationActions.Back

    override val startAction: Action =
        NavigationActions.NavigateToCompose("/no_network")

    override fun enterCondition(action: Action): Boolean =
        try {
            val pingTimeout = 250
            runBlocking {
                withContext( dispatcher ) {
                    ! InetAddress.getByName("8.8.8.8").isReachable(pingTimeout)
                }
            }
        } catch (e: Exception ) {
            Log.e("CheckInternetGate", e.toString())
            true
        }
}
