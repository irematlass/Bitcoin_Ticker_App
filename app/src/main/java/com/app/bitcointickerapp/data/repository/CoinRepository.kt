package com.app.bitcointickerapp.data.repository

import com.app.bitcointickerapp.data.model.Coin
import com.app.bitcointickerapp.data.model.CoinDetail
import com.app.bitcointickerapp.data.service.CoinDao
import com.app.bitcointickerapp.data.service.CryptocurrencyAPI
import io.reactivex.Single
import javax.inject.Inject

class CoinRepository  @Inject constructor(
    private val coinApi: CryptocurrencyAPI,
     private val coinDao: CoinDao
){
    fun getCoinsData(): Single<List<Coin>> {
        return coinApi.getCoins()
    }
    fun getCoinDetailData(id:String): Single<CoinDetail> {
        return coinApi.getCoinDetail(id)
    }
     suspend fun insertCoinList(coinList:List<Coin>) :List<Long>{
         return coinDao.insertAll(coinList)
     }
    suspend fun getAllCoins():List<Coin>{
        return coinDao.getAllCoins()
    }
    suspend fun getSearchCoins(searchName:String):List<Coin>{
        return coinDao.getCoins(searchName)
    }
}