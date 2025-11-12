package com.example.socialconnect.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.coroutines.tasks.await

class AuthRepository(private val auth: FirebaseAuth = FirebaseAuth.getInstance()) {

    suspend fun signUp(email: String, password: String, displayName: String?): Result<Unit> {
        return try {
            val res = auth.createUserWithEmailAndPassword(email, password).await()
            displayName?.let {
                val profile = userProfileChangeRequest { this.displayName = it }
                res.user?.updateProfile(profile)?.await()
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun signOut() {
        auth.signOut()
    }

    fun currentUserId() = auth.currentUser?.uid
    fun currentUserDisplayName() = auth.currentUser?.displayName
}
