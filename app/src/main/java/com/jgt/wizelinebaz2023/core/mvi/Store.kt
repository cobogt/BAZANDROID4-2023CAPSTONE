package com.jgt.wizelinebaz2023.core.mvi

import kotlinx.coroutines.flow.StateFlow

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
interface Store {
    val middlewareList: List<Middleware>
    val currenAction: StateFlow<Action>
    fun dispatch( action: Action ): Action
}