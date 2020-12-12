package com.app.bitcointickerapp.di

import android.app.Application
import android.content.Context
import com.app.bitcointickerapp.data.repository.CoinRepository
import com.app.bitcointickerapp.data.db.CoinDao
import com.app.bitcointickerapp.data.db.CoinDatabase
import com.app.bitcointickerapp.data.db.FirebaseManager
import com.app.bitcointickerapp.data.service.CryptocurrencyAPI
import com.app.bitcointickerapp.data.service.CryptocurrencyAPIService
import com.app.bitcointickerapp.ui.viewmodel.CoinDetailViewModel
import com.app.bitcointickerapp.ui.viewmodel.CoinListViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class MyModule {
    @Singleton
    @Provides
    fun provideCoinViewModel(coinRepository: CoinRepository): CoinListViewModel {
        return CoinListViewModel(coinRepository, application = Application())
    }

    @Singleton
    @Provides
    fun provideCoinDetailViewModel(coinRepository: CoinRepository): CoinDetailViewModel {
        return CoinDetailViewModel(coinRepository, application = Application())
    }

    @Singleton
    @Provides
    fun provideRetrofitApi(): CryptocurrencyAPI = CryptocurrencyAPIService.api

    @Singleton
    @Provides
    fun provideCoinDatabase(@ApplicationContext appContext: Context) =
        CoinDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideCoinDao(coinDatabase: CoinDatabase) = coinDatabase.coinDao()

    @Singleton
    @Provides
    fun provideCoinRepository(coinApi: CryptocurrencyAPI, coinDao: CoinDao,firebaseManager:FirebaseManager) =
        CoinRepository(coinApi, coinDao,firebaseManager)

    @Singleton
    @Provides
    fun provideFirebaseManager(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): FirebaseManager {
        return FirebaseManager(firebaseAuth, firebaseFirestore)
    }
    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseDatabase(): FirebaseFirestore = FirebaseFirestore.getInstance()

    /* @Provides
     fun provideCoinListAdapter(coinList: ArrayList<Coin>): CoinAdapter {
         return CoinAdapter(coinList)
     }*/
}