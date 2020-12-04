package com.app.bitcointickerapp.data.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Coin(
    @PrimaryKey @ColumnInfo(name = "coinId") @SerializedName("id")
    val coinId: String,

    @ColumnInfo(name = "coinSymbol")
    val symbol: String,

    @ColumnInfo(name = "coinName")
    val name: String
)