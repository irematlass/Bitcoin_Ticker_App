package com.app.bitcointickerapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.bitcointickerapp.data.model.Coin
import com.app.bitcointickerapp.data.repository.CoinRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CoinListViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
    application: Application
) : BaseViewModel(application) {

    private val disposable = CompositeDisposable()

    val coins = MutableLiveData<List<Coin>>()
    val coinError = MutableLiveData<Boolean>()
    val coinLoading = MutableLiveData<Boolean>()

    fun refreshData() {
        getDataFromAPI()
    }

    private fun getDataFromAPI() {
        coinLoading.value = true

        disposable.add(
            coinRepository.getCoinsData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Coin>>() {
                    override fun onSuccess(t: List<Coin>) {
                        storeInSQLite(t)
                    }

                    override fun onError(e: Throwable) {
                        coinError.value = true
                        coinLoading.value = false
                        e.printStackTrace()
                    }

                })
        )

    }

    private fun showCoins(coinList: List<Coin>) {
        coins.value = coinList
        coinError.value = false
        coinLoading.value = false
    }

    private fun storeInSQLite(coins: List<Coin>) {

        launch {
            coinRepository.deleteAllCoins()
            var temp = coinRepository.insertCoinList(coins)

        }
        showCoins(coins)
    }

    fun filterCoinList(searchValue: String) {
        viewModelScope.launch {
            coins.value = coinRepository.getSearchCoins(searchValue)
        }

    }


    fun getCoinList() {
        viewModelScope.launch {
            coins.value = coinRepository.getAllCoins()
        }
    }

}