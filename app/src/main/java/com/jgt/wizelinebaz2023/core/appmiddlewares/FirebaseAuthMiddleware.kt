package com.jgt.wizelinebaz2023.core.appmiddlewares

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jgt.wizelinebaz2023.core.appactions.UserActions
import com.jgt.wizelinebaz2023.core.appmodels.User
import com.jgt.wizelinebaz2023.core.mvi.Action
import com.jgt.wizelinebaz2023.core.mvi.Middleware
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
object FirebaseAuthMiddleware: Middleware {
    private var auth: FirebaseAuth = Firebase.auth

    private val TAG = javaClass.simpleName

    init {
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload()
        }
    }

    override fun next( action: Action ): Action {
        if( action is UserActions ) {
            return runBlocking {
                when( action ) {
                    is UserActions.CreateAccountAction ->
                        createAccount( action.email, action.password ).first()

                    is UserActions.SignInAction -> TODO()
                    UserActions.LogoutAction -> {
                        auth.signOut()
                        UserActions.ResultUserActions.LoggedOutAction
                    }
                    UserActions.SendEmailVerificationAction -> TODO()
                    else -> action
                }
            }
        }

        return action
    }

    private fun createAccount(email: String, password: String) = callbackFlow<Action> {
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
                        trySend( UserActions.ResultUserActions.AccountCreatedAction( it ) )
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    trySend( Action.ErrorAction("Authentication failed.", task.exception) )
                }
            }
    }

    private fun signIn(email: String, password: String) = callbackFlow<Action>{
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
                        trySend( UserActions.ResultUserActions.SignedInAction( it ) )
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    trySend( Action.ErrorAction("Authentication failed.", task.exception) )
                }
            }
        // [END sign_in_with_email]
    }

    private fun sendEmailVerification() = callbackFlow<Action>{
        // [START send_email_verification]
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnFailureListener {
                trySend( Action.ErrorAction("VerificationMailSend failed.", it) )
            }
            .addOnCompleteListener({ p0 -> p0?.run() }) { task ->
                if( task.isSuccessful )
                    trySend( UserActions.ResultUserActions.VerificationEmailSentAction )
                else
                    trySend( Action.ErrorAction("VerificationMailSend failed.", task.exception ) )
            }

        // [END send_email_verification]
    }

    private fun reload() {
        // TODO: Recarga de perfil
    }
}