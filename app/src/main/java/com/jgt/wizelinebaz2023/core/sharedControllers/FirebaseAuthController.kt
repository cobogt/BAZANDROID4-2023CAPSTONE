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
    private val tag = javaClass.simpleName
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
                    Log.d(tag, "createUserWithEmail:success")
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
                    Log.w(tag, "createUserWithEmail:failure", task.exception)
                    continuationStore.dispatch(
                        Action.ErrorAction("Authentication failed.", task.exception) )
                }
            }
    }

    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener({ p0 -> p0?.run() }) { task ->
                if (task.isSuccessful) {
                    Log.d(tag, "signInWithEmail:success")
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
                    Log.w(tag, "signInWithEmail:failure", task.exception)
                    continuationStore.dispatch(
                        Action.ErrorAction("Authentication failed.", task.exception) )
                }
            }
    }

    fun sendEmailVerification() {
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnFailureListener {
                continuationStore.dispatch(
                    Action.ErrorAction("VerificationMailSend failed.", it)
                )
            }
            .addOnCompleteListener({ p0 -> p0?.run() }) { task ->
                if( task.isSuccessful )
                    continuationStore.dispatch(
                        UserActions.ResultUserActions.VerificationEmailSentAction )
                else
                    continuationStore.dispatch(
                        Action.ErrorAction("SendVerificationMail failed.", task.exception ))
            }
    }

    fun signOut() {
        auth.signOut()

        continuationStore.dispatch(
            UserActions.ResultUserActions.LoggedOutAction
        )
    }

    private fun reload() { /* No aplica */ }
}
