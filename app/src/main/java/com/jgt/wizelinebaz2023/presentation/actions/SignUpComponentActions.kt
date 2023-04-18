package com.jgt.wizelinebaz2023.presentation.actions

import com.jgt.wizelinebaz2023.core.mvi.Action

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 18/04/23.
 * * * * * * * * * * **/
sealed class SignUpComponentActions: Action() {
    data class SetEmailAction           ( val newEmail: String ):          SignUpComponentActions()
    data class SetPasswordAction        ( val newPassword: String ):       SignUpComponentActions()
    data class SetPasswordRepeatAction  ( val newPasswordRepeat: String ): SignUpComponentActions()
}
