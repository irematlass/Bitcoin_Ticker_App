package com.app.bitcointickerapp.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.bitcointickerapp.data.model.Coin
import com.app.bitcointickerapp.data.repository.CoinRepository
import com.app.bitcointickerapp.data.service.CryptocurrencyAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CoinListViewModel @ViewModelInject constructor(private val coinApi: CryptocurrencyAPIService):ViewModel() {

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
                        coins.value=t
                        coinError.value=false
                        coinLoading.value=false
                    }

                    override fun onError(e: Throwable) {
                        coinError.value=true
                        coinLoading.value=false
                        e.printStackTrace()
                    }

                })
        )

    }
}