package com.app.bitcointickerapp.data.service

import com.app.bitcointickerapp.data.model.Coin
import com.app.bitcointickerapp.data.model.CoinDetail
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface CryptocurrencyAPI {

    @GET("coins/list")
    fun getCoins():Single<List<Coin>>

    @GET("coins/{id}")
    fun getCoinDetail(@Path("id") id: String):Single<CoinDetail>
}