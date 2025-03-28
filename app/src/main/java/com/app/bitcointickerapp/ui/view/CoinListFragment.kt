package com.app.bitcointickerapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.bitcointickerapp.R
import com.app.bitcointickerapp.ui.adapter.CoinAdapter
import com.app.bitcointickerapp.ui.viewmodel.CoinListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_coin_list.*
import javax.inject.Inject

@AndroidEntryPoint
class CoinListFragment : Fragment() {
    @Inject
    lateinit var coinViewModel: CoinListViewModel

    private lateinit var searchView: SearchView


    private val coinAdapter = CoinAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coin_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView = view.findViewById(R.id.coinList_search)
        coinViewModel.refreshData()

        coinList.layoutManager = LinearLayoutManager(context)
        coinList.adapter = coinAdapter

        swipeRefreshLayout.setOnRefreshListener {
            coinList.visibility = View.GONE
            coinListError.visibility = View.GONE
            coinListLoading.visibility = View.VISIBLE
            coinViewModel.refreshData()
            swipeRefreshLayout.isRefreshing = false
        }
        favList_btn.setOnClickListener {
            val action =
                CoinListFragmentDirections.actionCoinListFragmentToFavouriteCoinListFragment()
            Navigation.findNavController(it).navigate(action)
        }
        filterList()
        observeLiveData()

    }

    fun observeLiveData() {
        coinViewModel.coins.observe(viewLifecycleOwner, Observer { coins ->
            coins?.let {
                coinList.visibility = View.VISIBLE
                coinAdapter.updateCoinList(coins)
            }
        })
        coinViewModel.coinLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) {
                    coinListLoading.visibility = View.VISIBLE
                    coinList.visibility = View.GONE
                    coinListError.visibility = View.GONE
                } else {
                    coinListLoading.visibility = View.GONE
                }
            }

        })
        coinViewModel.coinError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (it) {
                    coinListError.visibility = View.VISIBLE
                } else {
                    coinListError.visibility = View.GONE
                }
            }

        })
    }

    fun filterList() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(value: String?): Boolean {
                if (value != null && value != "") {
                    coinViewModel.filterCoinList(value)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == "") {
                    coinViewModel.getCoinList()

                }
                return false

            }
        })

    }
}