package com.app.bitcointickerapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.bitcointickerapp.R
import com.app.bitcointickerapp.ui.adapter.FavouriteCoinAdapter
import com.app.bitcointickerapp.ui.viewmodel.FavouriteListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_coin_list.*
import kotlinx.android.synthetic.main.fragment_favourite_coin_list.*
import javax.inject.Inject

@AndroidEntryPoint
class FavouriteCoinListFragment : Fragment() {
    @Inject
    lateinit var favViewModel: FavouriteListViewModel

    private var favAdapter = FavouriteCoinAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite_coin_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        favViewModel.getData()

        favList.layoutManager = LinearLayoutManager(context)
        favList.adapter = favAdapter

        observeLiveData()
    }

    fun observeLiveData() {
        favViewModel.favCoins.observe(viewLifecycleOwner, Observer { coins ->
            coins?.let {
                favList.visibility = View.VISIBLE
                favAdapter.updateCoinList(coins)
            }
        })
    }


}