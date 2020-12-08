package com.app.bitcointickerapp.ui.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.bitcointickerapp.data.model.Coin
import com.app.bitcointickerapp.data.repository.CoinRepository
import com.app.bitcointickerapp.data.service.CoinDao
import com.app.bitcointickerapp.data.service.CryptocurrencyAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CoinListViewModel @Inject constructor(private val coinApi: CryptocurrencyAPIService/*,
                                            private val coinDao: CoinDao*/):ViewModel(),CoroutineScope {

    private val disposable=CompositeDisposable()

    val coins=MutableLiveData<List<Coin>>()
    val coinError=MutableLiveData<Boolean>()
    val coinLoading=MutableLiveData<Boolean>()

    fun refreshData(){
      getDataFromAPI()
    }
    private fun getDataFromAPI(){
        coinLoading.value=true

        disposable.add(
            coinApi.getCoinsData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object:DisposableSingleObserver<List<Coin>>(){
                    override fun onSuccess(t: List<Coin>) {
                          storeInSQLite(t)
                    }

                    override fun onError(e: Throwable) {
                        coinError.value=true
                        coinLoading.value=false
                        e.printStackTrace()
                    }

                })
        )

    }
    private fun showCoins(coinList:List<Coin>){
        coins.value=coinList
        coinError.value=false
        coinLoading.value=false
    }
    private fun storeInSQLite(coins:List<Coin>){

        launch {
           // var temp=coinDao.insertAll(*coins.toTypedArray())
        }
        showCoins(coins)
    }
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}