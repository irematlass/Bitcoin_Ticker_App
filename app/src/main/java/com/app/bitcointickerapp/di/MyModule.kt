package com.app.bitcointickerapp.di

import com.app.bitcointickerapp.data.model.Coin
import com.app.bitcointickerapp.data.repository.CoinRepository
import com.app.bitcointickerapp.data.service.CoinDao
import com.app.bitcointickerapp.data.service.CryptocurrencyAPIService
import com.app.bitcointickerapp.ui.adapter.CoinAdapter
import com.app.bitcointickerapp.ui.viewmodel.CoinListViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import java.util.ArrayList
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class MyModule {
    @Singleton
    @Provides
    fun provideCoinViewModel(coinApi: CryptocurrencyAPIService/*,coinDao: CoinDao*/): CoinListViewModel {
        return CoinListViewModel(coinApi/*,coinDao*/)
    }

   /* @Provides
    fun provideCoinListAdapter(coinList: ArrayList<Coin>): CoinAdapter {
        return CoinAdapter(coinList)
    }*/
}