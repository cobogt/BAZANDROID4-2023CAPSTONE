package com.jgt.content.movies.presentation.states

import com.jgt.core.mvi.Action
import com.jgt.core.mvi.ProductionRule
import com.jgt.core.mvi.State
import com.jgt.content.movies.presentation.actions.SignUpComponentActions

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 18/04/23.
 * * * * * * * * * * **/
sealed class SignupState: com.jgt.core.mvi.State() {
    data class SignupData(
        val email:          String = "",
        val password:       String = "",
        val passwordRepeat: String = "",
    ): SignupState() {
        override val productionRules: List<com.jgt.core.mvi.ProductionRule> = sharedProductionRules }

    data class SignupError( val signupData: SignupData, val errorMessage: String ): SignupState() {
        override val productionRules: List<com.jgt.core.mvi.ProductionRule> = sharedProductionRules }

    protected val sharedProductionRules = listOf<com.jgt.core.mvi.ProductionRule> { state, action ->
        if( state is SignupState && action is SignUpComponentActions)
            when( state ) {
                is SignupState.SignupData -> when( action ) {
                    is SignUpComponentActions.SetEmailAction -> {
                        val emailValidated = validateEmail( action.newEmail )
                        val signupData = state.copy(email = action.newEmail)

                        if( emailValidated.first )
                            signupData
                        else
                            SignupError(signupData, emailValidated.second)
                    }
                    is SignUpComponentActions.SetPasswordAction -> {
                        val passwordValidated = validatePassword( action.newPassword )
                        val signupData = state.copy(password = action.newPassword)

                        if( passwordValidated.first )
                            signupData
                        else
                            SignupError(
                                signupData,
                                passwordValidated.second
                            )
                    }
                    is SignUpComponentActions.SetPasswordRepeatAction -> {
                        val passwordValidated = validatePassword( action.newPasswordRepeat )
                        val signupData = state.copy(passwordRepeat = action.newPasswordRepeat)

                        if( passwordValidated.first )
                            signupData
                        else
                            SignupError(
                                signupData,
                                passwordValidated.second
                            )
                    }
                }
                is SignupError -> {
                    when (action) {
                        is SignUpComponentActions.SetEmailAction -> {
                            val emailAndPasswordValidated =
                                validateEmailAndPassword(action.newEmail, state.signupData.password)
                            val signupData = state.signupData.copy(email = action.newEmail)

                            if ( emailAndPasswordValidated.first )
                                signupData
                            else
                                SignupError(
                                    signupData,
                                    emailAndPasswordValidated.second
                                )
                        }
                        is SignUpComponentActions.SetPasswordAction -> {
                            val emailAndPasswordValidated =
                                validateEmailAndPassword(state.signupData.email, action.newPassword)
                            val signupData = state.signupData.copy(password = action.newPassword)

                            if ( emailAndPasswordValidated.first )
                                signupData
                            else
                                SignupError(
                                    signupData,
                                    emailAndPasswordValidated.second
                                )
                        }
                        is SignUpComponentActions.SetPasswordRepeatAction -> {
                            val passwordRepeatValidated = validatePasswordRepeat(
                                state.signupData.password,
                                action.newPasswordRepeat
                            )
                            val signupData = state.signupData.copy(passwordRepeat = action.newPasswordRepeat)

                            if( passwordRepeatValidated.first )
                                signupData
                            else
                                SignupError(
                                    signupData,
                                    passwordRepeatValidated.second
                                )
                        }
                    }
                }
            } else
                if( state is SignupState && action is com.jgt.core.mvi.Action.ErrorAction ) {
                    val errorMessage = "${action.text} ${action.exception?.message}"

                    when (state) {
                        is SignupData -> SignupError(state, errorMessage)
                        is SignupError -> SignupError(state.signupData, errorMessage)
                    }
                }
                else
                    state
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

    private fun validateEmail(email: String ): Pair<Boolean, String> =
        when {
            email.isEmpty() -> false to "El email no debe estar vacío."
            ! email.contains(Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")) ->
                false to "El email no tiene un formato correcto."
            else -> true to ""
        }


    private fun validatePassword( password: String ): Pair<Boolean, String> {
        if( password.length < minPasswordLength)
            return false to "La longitud mínima de la contraseña es de 5 caracteres"

        return true to ""
    }

    private fun validatePasswordRepeat( password: String, passwordRepeat: String ):
        Pair<Boolean, String> {
        val validatePassword = validatePassword( passwordRepeat )

        return when {
            password != passwordRepeat -> false to "Las contraseñas no coinciden"
            ! validatePassword.first   -> false to validatePassword.second
            else                       -> true  to ""
        }
    }

    companion object {
        const val minPasswordLength = 5
    }
}
