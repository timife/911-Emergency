package com.timife.a911.createProfile.ui

import android.util.Patterns
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import com.timife.a911.data.model.OnlineUser
import com.timife.a911.utils.Constants
import com.timife.a911.utils.FirestoreKey
import com.timife.a911.utils.Messages
import com.timife.a911.utils.states.AuthResult
import com.timife.a911.utils.states.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val remoteDatabase: FirebaseFirestore
) : AuthRepository {
    override fun createUser(
        fullName: String,
        email: String,
        password: String
    ): Flow<AuthResult<String>> = flow {
        emit(AuthResult.Loading)
        if (password.length < Constants.PASSWORD_LENGTH) {
            emit(AuthResult.Failed(Messages.SHORT_PASSWORD))
            return@flow
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emit(AuthResult.Failed(Messages.INVALID_CREDENTIAL))
            return@flow
        }

        val registerUser = auth.createUserWithEmailAndPassword(email, password).result
        val firebaseUser = registerUser.user!!
        val user = OnlineUser(
            fullName = fullName,
            email = email,
            userId = firebaseUser.uid
        )
        remoteDatabase.collection(FirestoreKey.NODE_USERS).document(firebaseUser.uid)
            .set(user).await()
        sendEmailVerificationLink(firebaseUser)
        emit(AuthResult.Success(Messages.ACCOUNT_CREATION_SUCCESS))
        logOut()
    }.catch { e ->
        when (e) {
            is FirebaseAuthUserCollisionException -> emit(AuthResult.Failed(Messages.ACCOUNT_IN_USE_FAILURE))
            else -> emit(AuthResult.Failed(Messages.ACCOUNT_CREATION_FAILURE))
        }
    }.flowOn(Dispatchers.IO)

    override fun currentUserId(): String {
        return auth.currentUser!!.uid
    }

    override fun loginUser(email: String, password: String): Flow<AuthResult<String>> = flow {
        emit(AuthResult.Loading)
        val login = auth.signInWithEmailAndPassword(email, password).await()
        val user = login.user!!

        if (user.isEmailVerified) {
            emit(AuthResult.Success(Messages.GENERIC_SUCCESS))
        } else {
            emit(AuthResult.UnAuthenticated)
            logOut()
            return@flow
        }

    }.catch { e ->
        when (e) {
            is FirebaseNetworkException -> emit(AuthResult.Failed(Messages.NETWORK_FAILURE))
            is FirebaseAuthInvalidCredentialsException -> emit(AuthResult.Failed(Messages.INVALID_CREDENTIAL))
            else -> emit(AuthResult.Failed(Messages.LOGIN_FAILED))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun sendEmailVerificationLink(firebaseUser: FirebaseUser) {
        withContext(Dispatchers.IO) {
            firebaseUser.sendEmailVerification().await()
        }
    }

    override fun sendEmailVerificationLink(
        email: String,
        password: String
    ): Flow<DataState<String>> = flow {
        emit(DataState.Loading)
        val credential: AuthCredential = EmailAuthProvider.getCredential(email, password)
        auth.signInWithCredential(credential).await()
        auth.currentUser?.let { user ->
            user.sendEmailVerification().await()
            auth.signOut()
            emit(DataState.Success(Messages.VERIFICATION_LINK_SUCCESS))
        }
    }.catch {
        auth.signOut()
        emit(DataState.Error(null, Messages.VERIFICATION_LINK_FAILURE))
    }.flowOn(Dispatchers.IO)

    override fun logOut() {
        auth.signOut()
    }

    override fun sendPasswordResetLink(email: String): Flow<DataState<String>> = flow {
        emit(DataState.Loading)
        auth.sendPasswordResetEmail(email).await()
        emit(DataState.Success(Messages.PASSWORD_LINK_SUCCESS))
    }.catch {
        emit(DataState.Error(null, Messages.PASSWORD_RESET_LINK_FAILURE))
    }.flowOn(Dispatchers.IO)
}