package com.jgt.wizelinebaz2023.core.gates

import com.jgt.wizelinebaz2023.core.mvi.Action

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 24/04/23.
 * * * * * * * * * * **/
abstract class Gate {
    open val name: String = javaClass.simpleName

    open val subGates: List<Gate> = emptyList()

    abstract val startAction:   Action
    abstract val onErrorAction: Action
    open val lastWillAction:    Action? = null

    abstract fun enterCondition( action: Action ): Boolean
}
