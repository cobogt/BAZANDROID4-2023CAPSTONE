package com.jgt.core.mvi

import okhttp3.internal.toImmutableList

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
abstract class State {
    open val productionRules = listOf<ProductionRule>()
    open var caretaker: Caretaker? = null

    inline fun <reified T: State>reduce(action: Action): T =
        productionRules
            .let {
                caretaker?.let { ct ->
                    it.toMutableList().apply {
                        add { state, action ->
                            if( action is Action.LoadStateAction )
                                ct.loadState() ?: ct.defaultState
                            else
                                state
                        }
                    }.toImmutableList()
                } ?: it
            }
        .map {
            it.invoke( this, action )
        }.firstOrNull { it != this }
        ?.let { it as T }
        ?.also {
            caretaker?.saveState( it )
        }?: this as T
}
