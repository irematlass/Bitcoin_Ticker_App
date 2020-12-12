package com.app.bitcointickerapp.data.service

import com.app.bitcointickerapp.data.model.Coin
import com.app.bitcointickerapp.data.model.CoinDetail
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class CryptocurrencyAPIService{


    companion object {
        private val BASE_URL = "https://api.coingecko.com/api/v3/"

        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        }
        val api: CryptocurrencyAPI by lazy {
            retrofit.create(CryptocurrencyAPI::class.java)
        }
    }


}