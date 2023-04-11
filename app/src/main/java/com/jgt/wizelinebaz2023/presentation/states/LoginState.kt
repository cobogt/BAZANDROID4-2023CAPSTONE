package com.jgt.wizelinebaz2023.presentation.states

import com.jgt.wizelinebaz2023.core.mvi.ProductionRule
import com.jgt.wizelinebaz2023.core.mvi.State

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
sealed class LoginState: State() {
    data class LoginData( val email: String = "", val password: String = ""): LoginState() {
        override val productionRules: List<ProductionRule> = sharedProductionRules }

    data class LoginError( val loginData: LoginData, val errorMessage: String ): LoginState() {
        override val productionRules: List<ProductionRule> = sharedProductionRules }

    internal val sharedProductionRules = listOf<ProductionRule>()
}
