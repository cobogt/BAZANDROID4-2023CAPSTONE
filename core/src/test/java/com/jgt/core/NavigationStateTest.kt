package com.jgt.core

import com.jgt.core.mvi.Action
import com.jgt.core.mvi.Caretaker
import com.jgt.core.mvi.State
import com.jgt.core.sharedActions.NavigationActions
import com.jgt.core.sharedStates.NavigationState
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 11/05/23.
 * * * * * * * * * * **/
@RunWith(JUnit4::class)
class NavigationStateTest {
    /**
     * Reducción de estado con acción vacía
     */
    @Test
    fun `NavigationState reduccion de estado con accion vacia`() {
        var initialState: NavigationState = NavigationState.Loading
        initialState = initialState.reduce( Action.VoidAction )

        assertEquals( initialState, NavigationState.Loading )
    }
    /**
     * Reducción de estado
     */
    @Test
    fun `NavigationState reduccion de estado`() {
        var initialState: NavigationState = NavigationState.Loading
        val demoPath = "/demoPath"

        initialState = initialState.reduce(
            NavigationActions.NavigateToCompose(demoPath)
        )

        assertNotEquals( initialState, NavigationState.Loading )
    }
    /**
     * Carga de estado
     */
    @Test
    fun `NavigationState carga de estado`() {
        var initialState: NavigationState = NavigationState.Loading
        val demoPath = "/demoPath"
        val loadedState = NavigationState.NavigateCompose( demoPath )

        initialState.caretaker = object: Caretaker {
            override val defaultState: State = NavigationState.Loading

            override fun <T : State> saveState(state: T) { /** Nothing to do */ }

            override fun <T : State> loadState(): T? = loadedState as T?
        }

        initialState = initialState.reduce( Action.LoadStateAction )

        assertEquals( initialState, loadedState )
    }
    /**
     * Almacenamiento de estado
     */
    @Test
    fun `NavigationState almacenamiento de estado`() {
        var initialState: NavigationState = NavigationState.Loading
        val demoPath = "/demoPath"
        var savedState: NavigationState? = null

        initialState.caretaker = object: Caretaker{
            override val defaultState: State = NavigationState.Loading

            override fun <T : State> saveState(state: T) {
                savedState = state as NavigationState
            }

            override fun <T : State> loadState(): T? = initialState as T?
        }

        initialState = initialState.reduce(
            NavigationActions.NavigateToCompose( demoPath )
        )

        assertNotNull( savedState )
        assertEquals( initialState, NavigationState.NavigateCompose(demoPath) )
        assertEquals( savedState, initialState )
    }
    /**
     * Cambio de estado con acción inicial y final
     */
    @Test
    fun `NavigationState cambio de estado con accion inicial y final`() {
        var initialState: NavigationState = NavigationState.Loading
        val demoPath = "/demoPath"

        initialState = initialState.reduce(
            NavigationActions.NavigateToCompose( demoPath )
        )

        assertEquals( initialState, NavigationState.NavigateCompose(demoPath) )
    }
}