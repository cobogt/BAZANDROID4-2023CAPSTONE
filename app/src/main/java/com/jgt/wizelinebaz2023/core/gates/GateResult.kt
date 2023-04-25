package com.jgt.wizelinebaz2023.core.gates

import com.jgt.wizelinebaz2023.core.mvi.Action

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 25/04/23.
 * * * * * * * * * * **/
sealed interface GateResult {
    data class Success( val successAction: Action? = null ): GateResult
    data class Error( val errorAction: Action? = null ): GateResult
    data class Retry( val retryAction: Action? = null ): GateResult
}
