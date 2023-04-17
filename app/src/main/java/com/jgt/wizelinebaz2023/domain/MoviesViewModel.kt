package com.jgt.wizelinebaz2023.domain

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.jgt.wizelinebaz2023.core.AppStateStore
import com.jgt.wizelinebaz2023.core.mvi.Action
import com.jgt.wizelinebaz2023.core.mvi.Middleware
import com.jgt.wizelinebaz2023.core.mvi.Store

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
class MoviesViewModel( application: Application ): AndroidViewModel( application ), Store {
    override val middlewareList: List<Middleware> = listOf()

    override fun dispatch(action: Action): Action {
        var currentAction = AppStateStore.dispatch( action )

        middlewareList.forEach { currentAction = it.next( currentAction ) }

        // Reducción de estados

        return currentAction
    }

}