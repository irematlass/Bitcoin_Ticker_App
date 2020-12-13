package com.app.bitcointickerapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.bitcointickerapp.data.model.Coin
import com.app.bitcointickerapp.data.repository.CoinRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteListViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
    application: Application
) : BaseViewModel(application) {

    val favCoins = MutableLiveData<List<Coin>>()
    val favError = MutableLiveData<Boolean>()

    fun getData() {
        viewModelScope.launch {
            getFavList()
        }


    }

    suspend fun getFavList() {
        val collectionReference = coinRepository.getFavoriteCoins()
        collectionReference.get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    favError.value = true
                } else {
                    val coinList = ArrayList<Coin>()
                    for (snapShot in result) {
                        val id = snapShot.data["id"].toString()
                        val name = snapShot.data["name"].toString()
                        val symbol = snapShot.data["symbol"].toString()


                        val coinResponse = Coin(id, symbol, name)
                        coinList.add(coinResponse)
                    }
                    favCoins.value = coinList

                }
            }
            .addOnFailureListener {
                favError.value = true
            }
    }
}