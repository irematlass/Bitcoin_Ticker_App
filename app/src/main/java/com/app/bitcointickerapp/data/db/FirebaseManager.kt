package com.app.bitcointickerapp.data.db

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirebaseManager @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) {
    private fun getCurrentUserId(): String? = firebaseAuth.currentUser?.uid
    fun signInFirebase(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(
            email, password
        )
    }

    fun favoriteCoinsList(): CollectionReference {
        getCurrentUserId().also { uid ->
            return firebaseFirestore.collection("Favourites").document(uid!!)
                .collection("Coins")
        }
    }

}