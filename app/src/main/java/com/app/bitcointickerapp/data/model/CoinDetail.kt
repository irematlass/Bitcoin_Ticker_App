package com.app.bitcointickerapp.data.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class CoinDetail(
    @ColumnInfo(name = "coinName")
    val name: String,
    @ColumnInfo(name = "coinSymbol")
    val symbol: String,
    @SerializedName("market_data")
    val marketData: MarketData?,

    @SerializedName("image")
    val image: CoinImage?,

    @SerializedName("description")
    val description: CoinDescription?,

    @SerializedName("hashing_algorithm")
    val hashing_algorithm: String?
)

data class MarketData(
    @SerializedName("current_price")
    val current_price: CurrentPrice?,

    @SerializedName("price_change_percentage_24h")
    val priceChancePercentage_24h: Double?
)

data class CurrentPrice(
    @SerializedName("usd")
    val usd: Double?
)

data class CoinImage(
    @SerializedName("large")
    val imageLarge: String?

)

data class CoinDescription(
    @SerializedName("en")
    val description_en: String?
)
