package com.app.bitcointickerapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.bitcointickerapp.data.model.Coin

@Dao
interface CoinDao {
    //Data Access Object
    @Insert
    suspend fun insertAll(coins: List<Coin>): List<Long>

     @Query("SELECT * FROM coin")
     suspend fun getAllCoins(): List<Coin>

     @Query("SELECT * FROM coin WHERE coinName=:name ")
     suspend fun getCoins(name: String): List<Coin>
}