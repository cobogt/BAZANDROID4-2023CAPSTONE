package com.jgt.content.movies.domain

import androidx.lifecycle.ViewModel
import com.jgt.core.AppStateStore
import com.jgt.core.mvi.Action
import com.jgt.core.sharedMiddlewares.FirebaseAuthMiddleware
import com.jgt.content.movies.data.MoviesRepository
import com.jgt.core.mvi.Middleware
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
): com.jgt.core.mvi.Store, ViewModel() {
    override val middlewareList: List<Middleware> = listOf(
        FirebaseAuthMiddleware(this),
    )

    fun getMoviesByCategory( category: String ) =
        moviesRepository.getMovieListByCategory( category )

    fun getMovieDetailById( movieId: Int ) =
        moviesRepository.getMovieDetailById( movieId )

    private val currentActionMutable = MutableStateFlow<Action>(Action.LoadStateAction)
    override val currenAction: StateFlow<Action> = currentActionMutable.asStateFlow()

    init {
        // Cargamos el estado inicial de la vista
        dispatch( Action.LoadStateAction )
    }

    override fun dispatch(action: Action): Action {
        var currentAction = AppStateStore.dispatch( action )

        middlewareList.forEach { currentAction = it.next( currentAction ) }

        currentActionMutable.value = currentAction

        return currentAction
    }
}
