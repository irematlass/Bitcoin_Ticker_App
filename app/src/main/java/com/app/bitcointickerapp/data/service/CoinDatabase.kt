package com.app.bitcointickerapp.data.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.bitcointickerapp.data.model.Coin

@Database(entities = arrayOf(Coin::class), version = 1)
abstract class CoinDatabase  : RoomDatabase() {
    abstract fun coinDao(): CoinDao

    //Singleton
    companion object {
        @Volatile
        private var instance: CoinDatabase? = null

        private val lock = Any()

         fun getDatabase(context: Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, CoinDatabase::class.java, "coindatabase"
        ).build()
    }

}