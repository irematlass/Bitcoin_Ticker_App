package com.app.bitcointickerapp.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.bitcointickerapp.data.model.CoinDetail
import com.app.bitcointickerapp.data.repository.CoinRepository

class CoinDetailViewModel @ViewModelInject constructor(private val coinRepository: CoinRepository): ViewModel() {
    val coinDetailLiveData=MutableLiveData<CoinDetail>()

    fun getDataFromRoom(){
        //coinDetailLiveData.value=CoinDetail()
    }
}