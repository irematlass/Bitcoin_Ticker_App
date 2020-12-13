package com.app.bitcointickerapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.bitcointickerapp.data.model.CoinDetail
import com.app.bitcointickerapp.data.repository.CoinRepository
import com.google.firebase.firestore.SetOptions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinDetailViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
    application: Application
) : BaseViewModel(application) {
    private val disposable = CompositeDisposable()
    val coinDetailLiveData = MutableLiveData<CoinDetail>()

    fun getData(coinId: String) {
        disposable.add(
            coinRepository.getCoinDetailData(coinId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<CoinDetail>() {
                    override fun onSuccess(t: CoinDetail) {
                        coinDetailLiveData.value = t
                    }

                    override fun onError(e: Throwable) {

                        e.printStackTrace()
                    }

                })
        )

    }

    fun addFavouriteCoin(coin: HashMap<String, String>, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            val collectionReference = coinRepository.saveFavoriteCoin()
            collectionReference.document(coin["id"]!!)
                .set(coin, SetOptions.merge())
                .addOnSuccessListener {
                    onResult(null)
                }
                .addOnFailureListener {
                    onResult(it.message)
                }
        }
    }

    fun deleteFavouriteCoin(coin: HashMap<String, String>, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            val collectionReference = coinRepository.deleteFavoriteCoin()
            collectionReference.document(coin["id"]!!)
                .delete()
                .addOnSuccessListener {
                    onResult(null)
                }
                .addOnFailureListener {
                    onResult(it.message)
                }
        }
    }
}