package com.jgt.wizelinebaz2023.core.mvi

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
interface Store {
    val middlewareList: List<Middleware>
    fun dispatch( action: Action ): Action
}