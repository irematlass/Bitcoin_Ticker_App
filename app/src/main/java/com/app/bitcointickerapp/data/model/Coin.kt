package com.app.bitcointickerapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Coin(
    @PrimaryKey @ColumnInfo(name = "coinId") @SerializedName("id")
    val coinId: String,

    @ColumnInfo(name = "coinSymbol")
    val symbol: String,

    @ColumnInfo(name = "coinName")
    val name: String
)