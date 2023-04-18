package com.jgt.wizelinebaz2023.core.mvi

import android.util.Log
import okhttp3.internal.toImmutableList

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
abstract class State {
    open val productionRules = listOf<ProductionRule>()
    open val caretaker: Caretaker? = null

    inline fun <reified T: State>reduce( action: Action ): T =
        productionRules
            .let {
                caretaker?.let { ct ->
                    it.toMutableList().apply {
                        Log.e("StateBASE", "$caretaker")

                        add { state, action ->
                            if( action is Action.LoadStateAction )
                                ct.loadState() ?: ct.defaultState
                            else
                                state.also { ct.saveState( state ) }
                        }
                    }.toImmutableList()
                } ?: it
            }
            .also {
            Log.e("StateBASE", "$this -> $action")
        }
        .map {
            it.invoke( this, action )
        }.firstOrNull { it != this }
        ?.let { it as T } ?: this as T
}