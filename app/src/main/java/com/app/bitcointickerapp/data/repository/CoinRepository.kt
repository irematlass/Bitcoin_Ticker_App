package com.app.bitcointickerapp.data.repository

import com.app.bitcointickerapp.data.model.Coin
import com.app.bitcointickerapp.data.model.CoinDetail
import com.app.bitcointickerapp.data.db.CoinDao
import com.app.bitcointickerapp.data.db.FirebaseManager
import com.app.bitcointickerapp.data.service.CryptocurrencyAPI
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.CollectionReference
import io.reactivex.Single
import javax.inject.Inject

class CoinRepository @Inject constructor(
    private val coinApi: CryptocurrencyAPI,
    private val coinDao: CoinDao,
    private val firebaseManager: FirebaseManager
) {
    fun getCoinsData(): Single<List<Coin>> {
        return coinApi.getCoins()
    }

    fun getCoinDetailData(id: String): Single<CoinDetail> {
        return coinApi.getCoinDetail(id)
    }

    suspend fun insertCoinList(coinList: List<Coin>): List<Long> {
        return coinDao.insertAll(coinList)
    }

    suspend fun getAllCoins(): List<Coin> {
        return coinDao.getAllCoins()
    }

    suspend fun deleteAllCoins() {
        return coinDao.deleteAllCoins()
    }

    suspend fun getSearchCoins(searchName: String): List<Coin> {
        return coinDao.getCoins(searchName)
    }


    suspend fun signInFirebase(email: String, password: String): Task<AuthResult> {
        return firebaseManager.signInFirebase(email, password)
    }

    suspend fun saveFavoriteCoin(): CollectionReference {
        return firebaseManager.favoriteCoinsList()
    }

    suspend fun deleteFavoriteCoin(): CollectionReference {
        return firebaseManager.favoriteCoinsList()
    }

    suspend fun getFavoriteCoins(): CollectionReference {
        return firebaseManager.favoriteCoinsList()
    }
}