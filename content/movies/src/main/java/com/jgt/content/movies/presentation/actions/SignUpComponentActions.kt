package com.jgt.content.movies.presentation.actions

import com.jgt.core.mvi.Action

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 18/04/23.
 * * * * * * * * * * **/
sealed class SignUpComponentActions: com.jgt.core.mvi.Action() {
    data class SetEmailAction           ( val newEmail: String ):          SignUpComponentActions()
    data class SetPasswordAction        ( val newPassword: String ):       SignUpComponentActions()
    data class SetPasswordRepeatAction  ( val newPasswordRepeat: String ): SignUpComponentActions()
}
