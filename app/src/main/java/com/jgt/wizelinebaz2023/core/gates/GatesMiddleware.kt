package com.jgt.wizelinebaz2023.core.gates

import com.jgt.wizelinebaz2023.core.mvi.Action
import com.jgt.wizelinebaz2023.core.mvi.Middleware
import com.jgt.wizelinebaz2023.core.mvi.Store

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 25/04/23.
 * * * * * * * * * * **/
data class GatesMiddleware(
    override val store: Store
): Middleware {
    override fun next( action: Action ): Action {
        val gatesController = GatesController( store )

        return if( action is ExitGateAction )
            gatesController.exitGate( action.result )
        else
            action.gate?.let {
                gatesController.evalGate(
                    it, action, store
                )
            } ?: action
    }
}
