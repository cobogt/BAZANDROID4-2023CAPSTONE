package com.jgt.wizelinebaz2023.core.mvi

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
abstract class State {
    open val productionRules = listOf<ProductionRule>()
    open val caretaker: Caretaker<State>? = null

    inline fun <reified T: State>reduce( action: Action ): T =
        productionRules.map {
            it.invoke( this, action )
        }.firstOrNull { it != this }
        ?.let {
            caretaker?.let { caretaker ->
                if( action is Action.LoadStateAction )
                    caretaker.loadState() ?: caretaker.defaultState
                else
                    it.also { caretaker.saveState( it ) }
            } ?: it
        }?.let { it as T } ?: this as T
}