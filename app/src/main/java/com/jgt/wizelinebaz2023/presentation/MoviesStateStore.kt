package com.jgt.wizelinebaz2023.presentation

import com.jgt.wizelinebaz2023.core.mvi.Action
import com.jgt.wizelinebaz2023.core.mvi.Middleware
import com.jgt.wizelinebaz2023.core.mvi.Store

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
class MoviesStateStore: Store {
    override val middlewareList: List<Middleware> = listOf()

    override fun dispatch(action: Action): Action {
        TODO("Not yet implemented")
    }
}