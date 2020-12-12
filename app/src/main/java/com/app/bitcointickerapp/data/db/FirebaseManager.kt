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
){
     fun signInFirebase(): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(
            "bitcoin.ticker@gmail.com",
            "bitcoin"
        )
    }

     fun favoriteCoinsList(): CollectionReference {
        return firebaseFirestore.collection("users").document("ewCFUgg4DhuZVW8Or4us")
            .collection("myfavoriteCoins")
    }

}