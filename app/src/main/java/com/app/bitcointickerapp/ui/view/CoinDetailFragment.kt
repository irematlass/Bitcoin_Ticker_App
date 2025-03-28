package com.app.bitcointickerapp.ui.view


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.app.bitcointickerapp.R
import com.app.bitcointickerapp.ui.viewmodel.CoinDetailViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_coin_detail.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class CoinDetailFragment : Fragment() {
    @Inject
    lateinit var coinDetailViewModel: CoinDetailViewModel

    private var coinUUID = ""
    private var isfavDetail = false
    private var coinSymbol = ""
    private var coinName = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coin_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            coinUUID = CoinDetailFragmentArgs.fromBundle(it).coinUuid
            isfavDetail = CoinDetailFragmentArgs.fromBundle(it).isFavDetail
        }
        coinDetailViewModel.getData(coinUUID)
        observeLiveData()

        coinDetail_done_btn.setOnClickListener {
            val value = coinDetail_interval_et.text.toString().trim()

            if (value.isEmpty()) {
                coinDetail_interval_et.error = "Enter a value"
                coinDetail_interval_et.requestFocus()
                return@setOnClickListener
            } else {
                setInterval(value.toInt())
                Toast.makeText(
                    requireContext(),
                    "Refresh interval set to $value minute",
                    Toast.LENGTH_SHORT
                ).show()


            }
        }
        coinDetail_favorite_btn.setOnClickListener {
            val coin = hashMapOf(
                "id" to coinUUID,
                "name" to coinName,
                "symbol" to coinSymbol

            )
            if (isfavDetail) {
                coinDetailViewModel.deleteFavouriteCoin(coin) {
                    if (it == null) {
                        Toast.makeText(
                            requireContext(),
                            "Delete your favourite List",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Error",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("faverror", it)
                    }
                }
            } else {
                coinDetailViewModel.addFavouriteCoin(coin) {
                    if (it == null) {
                        Toast.makeText(
                            requireContext(),
                            "Added your favourite List",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Error",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("faverror", it)
                    }
                }
            }
        }
    }

    private fun observeLiveData() {
        coinDetailViewModel.coinDetailLiveData.observe(viewLifecycleOwner, Observer { detail ->
            detail?.let {
                Glide.with(requireContext()).load(detail.image?.imageLarge)
                    .into(coinDetail_imageView)
                coinDetail_hash_txt.text = detail.hashing_algorithm
                coinDetail_description_txt.text = detail.description?.description_en
                coinDetail_price_txt.text = detail.marketData?.current_price?.usd.toString()
                coinDetail_changePrice_txt.text =
                    detail.marketData?.priceChancePercentage_24h.toString()
                coinDetail_name_txt.text = detail.name
                coinSymbol = detail.symbol
                coinName = detail.name


            }
        })
    }

    private fun setInterval(interval: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            while (true) {
                coinDetailViewModel.getData(coinUUID)
                //as a minute
                val delay = interval * 60000
                delay(delay.toLong())
            }
        }
    }
}


