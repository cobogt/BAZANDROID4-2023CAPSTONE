package com.jgt.core

import com.jgt.core.mvi.Action
import com.jgt.core.mvi.Caretaker
import com.jgt.core.mvi.State
import com.jgt.core.sharedActions.UserActions
import com.jgt.core.sharedModels.User
import com.jgt.core.sharedStates.UserState
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 11/05/23.
 * * * * * * * * * * **/
@RunWith(JUnit4::class)
class UserStateTest {
    /**
     * Reducción de estado con acción vacía
     */
    @Test
    fun `UserState reduccion de estado con accion vacia`() {
        val initialState = UserState.Loading
        val reducedState = initialState.reduce<UserState>( Action.VoidAction )

        assertEquals( initialState, reducedState )
    }
    /**
     * Reducción de estado
     */
    @Test
    fun `UserState reduccion de estado`() {
        var initialState: UserState = UserState.Loading
        val userLoggedIn = User("0", "Test", "test@test.com")

        initialState = initialState.reduce(
            UserActions.ResultUserActions.LoggedInAction( userLoggedIn )
        )

        val endState: UserState = UserState.LoggedIn( userLoggedIn )

        assertEquals( initialState, endState )
    }
    /**
     * Carga de estado
     */
    @Test
    fun `UserState carga de estado`() {
        var initialState: UserState = UserState.Loading
        val userLoggedIn = User("0", "Test", "test@test.com")
        val loadedState = UserState.LoggedIn( userLoggedIn )

        initialState.caretaker = object: Caretaker{
            override val defaultState: State = UserState.Loading

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
    fun `UserState almacenamiento de estado`() {
        val userLoggedIn = User("0", "Test", "test@test.com")
        var initialState: UserState = UserState.Loading
        var savedState: UserState? = null

        initialState.caretaker = object: Caretaker{
            override val defaultState: State = UserState.Loading

            override fun <T : State> saveState(state: T) {
                savedState = state as UserState
            }

            override fun <T : State> loadState(): T? = initialState as T?
        }

        initialState = initialState.reduce(
            UserActions.ResultUserActions.LoggedInAction( userLoggedIn )
        )

        assertNotNull( savedState )
        assertEquals( initialState, UserState.LoggedIn( userLoggedIn ))
        assertEquals( savedState, initialState )
    }
    /**
     * Cambio de estado con acción inicial y final
     */
    @Test
    fun `UserState cambio de estado con accion inicial y final`() {
        var initialState: UserState = UserState.Loading
        val userLoggedIn = User("0", "Test", "test@test.com")

        listOf<Action>(
            UserActions.ResultUserActions.LoggedOutAction,
            UserActions.ResultUserActions.AccountCreatedAction( userLoggedIn ),
        ).forEach {
            initialState = initialState.reduce( it )
        }

        assertEquals( initialState, UserState.LoggedIn( userLoggedIn ))
    }
    /**
     * Cambio de estado con secuencia de acciones ( Trazado de grafo )
     */
    @Test
    fun `UserState cambio de estado con secuencia de acciones para trazado de grafo`() {
        var initialState: UserState = UserState.Loading
        val userLoggedIn = User("0", "Test", "test@test.com")

        listOf<Action>(
            UserActions.ResultUserActions.LoggedOutAction,
            UserActions.ResultUserActions.AccountCreatedAction( userLoggedIn ),
            UserActions.ResultUserActions.LoggedOutAction,
            UserActions.ResultUserActions.LoggedInAction( userLoggedIn ),
        ).forEach {
            initialState = initialState.reduce( it )
        }

        assertEquals( initialState, UserState.LoggedIn( userLoggedIn ))
        initialState = initialState.reduce( UserActions.ResultUserActions.LoggedOutAction )
        assertEquals( initialState, UserState.LoggedOut )
    }
}