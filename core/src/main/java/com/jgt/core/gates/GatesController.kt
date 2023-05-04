package com.jgt.core.gates

import android.util.Log
import com.jgt.core.mvi.Action
import com.jgt.core.mvi.Controller
import com.jgt.core.mvi.Store
import com.jgt.core.sharedControllers.NavigationController
import java.util.Stack

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 25/04/23.
 * * * * * * * * * * **/
data class GatesController(
    override val continuationStore: Store
): Controller {

    fun evalGate(gate: Gate, originalAction: Action, store: Store? ): Action {
        var replaceAction = originalAction

        val enterInGate =  gate.enterCondition( originalAction )

        if( enterInGate ) {
            replaceAction = gate.startAction
            Log.e("GatesController", "$enterInGate: $gate === $originalAction -> $replaceAction")

            if( replaceAction != originalAction )
                actionsStack.push(
                    ActionStacked(
                        originalAction,
                        gate,
                        store,
                        getCurrentActivityName()
                    )
                )
        }

        gate.subGates
            .filterNot { it.name == gate.name && it.subGates.contains( gate ) }
            .forEach {
                if( it.enterCondition( originalAction ))
                    replaceAction = evalGate( it, replaceAction, store )
            }

        return replaceAction
    }

    fun exitGate( gateResult: GateResult): Action {
        if( actionsStack.isEmpty() )
            return Action.VoidAction

        val actionStacked = actionsStack.pop()
        val stackedStore = actionStacked.store

        if( actionsStack.isEmpty() ) {
            actionStacked.activityName?.also {
                NavigationController.finishCurrentActivity()
            }
        }

        val resultAction: Action = when( gateResult ) {
            is GateResult.Error -> gateResult.errorAction ?: actionStacked.gate.onErrorAction
            is GateResult.Retry -> gateResult.retryAction ?: actionStacked.gate.startAction
            is GateResult.Success -> gateResult.successAction ?: actionStacked.originalAction
        }

        actionStacked.gate.lastWillAction?.also {
            stackedStore?.dispatch( it )
        }

        return resultAction
    }

    private fun getCurrentActivityName(): String? =
        NavigationController.getCurrentActivityName()

    companion object {
        private val actionsStack: Stack<ActionStacked> = Stack()
    }

    private data class ActionStacked(
        val originalAction: Action,
        val gate: Gate,
        val store:          Store? = null,
        val activityName:   String? = null,
    )
}
