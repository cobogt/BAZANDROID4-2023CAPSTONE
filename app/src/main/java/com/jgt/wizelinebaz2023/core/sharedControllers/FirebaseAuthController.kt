package com.jgt.wizelinebaz2023.core.sharedControllers

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jgt.wizelinebaz2023.core.mvi.Action
import com.jgt.wizelinebaz2023.core.mvi.Controller
import com.jgt.wizelinebaz2023.core.mvi.Store
import com.jgt.wizelinebaz2023.core.sharedActions.UserActions
import com.jgt.wizelinebaz2023.core.sharedModels.User

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 12/04/23.
 * * * * * * * * * * **/
data class FirebaseAuthController(
    override val continuationStore: Store
): Controller {
    private val TAG = javaClass.simpleName
    private var auth: FirebaseAuth = Firebase.auth

    init {
        val currentUser = auth.currentUser

        if(currentUser != null)
            reload()
    }

    fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener({ p0 -> p0?.run() }) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    auth.currentUser?.let {
                        User(
                            id      = it.uid,
                            name    = "${it.displayName}",
                            email   = "${it.email}",
                        )
                    }?.also {
                        continuationStore.dispatch(
                            UserActions.ResultUserActions.AccountCreatedAction( it ) )
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    continuationStore.dispatch(
                        Action.ErrorAction("Authentication failed.", task.exception) )
                }
            }
    }

    fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener({ p0 -> p0?.run() }) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    auth.currentUser?.let {
                        User(
                            id      = it.uid,
                            name    = "${it.displayName}",
                            email   = "${it.email}",
                        )
                    }?.also {
                        continuationStore.dispatch(
                            UserActions.ResultUserActions.LoggedInAction( it ) )
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    continuationStore.dispatch(
                        Action.ErrorAction("Authentication failed.", task.exception) )
                }
            }
        // [END sign_in_with_email]
    }

    fun sendEmailVerification() {
        // [START send_email_verification]
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnFailureListener {
                continuationStore.dispatch( Action.ErrorAction("VerificationMailSend failed.", it) )
            }
            .addOnCompleteListener({ p0 -> p0?.run() }) { task ->
                if( task.isSuccessful )
                    continuationStore.dispatch(
                        UserActions.ResultUserActions.VerificationEmailSentAction )
                else
                    continuationStore.dispatch(
                        Action.ErrorAction("SendVerificationMail failed.", task.exception ))
            }

        // [END send_email_verification]
    }

    fun signOut() {
        auth.signOut()

        continuationStore.dispatch(
            UserActions.ResultUserActions.LoggedOutAction
        )
    }

    private fun reload() {
        // TODO: Recarga de perfil
    }
}