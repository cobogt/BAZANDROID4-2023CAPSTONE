package com.jgt.wizelinebaz2023.presentation.states

import com.jgt.wizelinebaz2023.core.mvi.ProductionRule
import com.jgt.wizelinebaz2023.core.mvi.State
import com.jgt.wizelinebaz2023.presentation.actions.LoginComponentActions

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
sealed class LoginState: State() {
    data class LoginData( val email: String = "", val password: String = ""): LoginState() {
        override val productionRules: List<ProductionRule> = sharedProductionRules }

    data class LoginError( val loginData: LoginData, val errorMessage: String ): LoginState() {
        override val productionRules: List<ProductionRule> = sharedProductionRules }

    protected val sharedProductionRules = listOf<ProductionRule> { state, action ->
        if( state is LoginState && action is LoginComponentActions )
            when( state ) {
                is LoginData  -> when( action ) {
                    is LoginComponentActions.SetEmailAction -> {
                        val emailValidated = validateEmail( action.newEmail )

                        if( emailValidated.first )
                            LoginData(action.newEmail, state.password )
                        else
                            LoginError(state.copy( action.newEmail), emailValidated.second)
                    }
                    is LoginComponentActions.SetPasswordAction -> {
                        val passwordValidated = validatePassword( action.newPassword )

                        if( passwordValidated.first )
                            LoginData(state.email, action.newPassword )
                        else
                            LoginError(
                                state.copy( password = action.newPassword),
                                passwordValidated.second
                            )
                    }
                }
                is LoginError -> {
                    when (action) {
                        is LoginComponentActions.SetEmailAction -> {
                            val emailAndPasswordValidated =
                                validateEmailAndPassword(action.newEmail, state.loginData.password)

                            if ( emailAndPasswordValidated.first )
                                LoginData(
                                    action.newEmail,
                                    state.loginData.password
                                )
                            else
                                LoginError(
                                    state.loginData.copy(email = action.newEmail),
                                    emailAndPasswordValidated.second
                                )
                        }
                        is LoginComponentActions.SetPasswordAction -> {
                            val emailAndPasswordValidated =
                                validateEmailAndPassword(state.loginData.email, action.newPassword)

                            if ( emailAndPasswordValidated.first )
                                LoginData(
                                    state.loginData.email,
                                    password = action.newPassword
                                )
                            else
                                LoginError(
                                    state.loginData.copy(password = action.newPassword),
                                    emailAndPasswordValidated.second
                                )
                        }
                    }
                }
            } else state
    }

    private fun validateEmailAndPassword( email: String, password: String ): Pair<Boolean, String> {
        val emailValidated    = validateEmail( email )
        val passwordValidated = validatePassword( password )

        return Pair(
            emailValidated.first && passwordValidated.first,
            listOf(emailValidated.second, passwordValidated.second)
                .filterNot { it.isEmpty() }
                .joinToString(", ")
        )
    }

    private fun validateEmail(email: String ): Pair<Boolean, String> {
        if (email.isEmpty())
            return false to "El email no debe estar vacío."

        if ( ! email.contains(Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")) )
            return false to "El email no tiene un formato correcto."

        return true to ""
    }

    private fun validatePassword( password: String ): Pair<Boolean, String> {
        if( password.length < 5 )
            return false to "La longitud mínima de la contraseña es de 5 caracteres"

        return true to ""
    }
}
