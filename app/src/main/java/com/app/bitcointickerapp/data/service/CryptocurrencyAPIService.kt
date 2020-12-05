package com.app.bitcointickerapp.data.service

import com.app.bitcointickerapp.data.model.Coin
import com.app.bitcointickerapp.data.model.CoinDetail
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class CryptocurrencyAPIService  @Inject constructor(){

    private val BASE_URL = "https://api.coingecko.com/api/v3/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CryptocurrencyAPI::class.java)

    fun getCoinsData():Single<List<Coin>>{
        return api.getCoins()
    }
    fun getCoinDetailData(id:String):Single<List<CoinDetail>>{
        return api.getCoinDetail(id)
    }
}