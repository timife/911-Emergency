package com.timife.a911.utils.auth

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseUserLiveData(private val auth: FirebaseAuth) : LiveData<FirebaseUser>() {


    private val authStateListener = FirebaseAuth.AuthStateListener {
        value = it.currentUser
    }

    //When this object has an active observer,
    // start observing the FirebaseAuth state
    // to see if there is currently a logged in user.

    override fun onActive() {
        super.onActive()
        auth.addAuthStateListener(authStateListener)
    }
    //When this object no longer has an active observer,
    // stop observing the FirebaseAuth state
    // to prevent memory leaks.

    override fun onInactive() {
        super.onInactive()
        auth.removeAuthStateListener(authStateListener)
    }


}

