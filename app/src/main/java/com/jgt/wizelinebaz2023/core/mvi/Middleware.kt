package com.jgt.wizelinebaz2023.core.mvi

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
interface Middleware {
    val store: Store
    fun next( action: Action ): Action
}
