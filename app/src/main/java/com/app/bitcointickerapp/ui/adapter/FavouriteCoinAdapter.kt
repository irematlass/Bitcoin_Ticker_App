package com.app.bitcointickerapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.app.bitcointickerapp.R
import com.app.bitcointickerapp.data.model.Coin
import com.app.bitcointickerapp.ui.view.FavouriteCoinListFragmentDirections
import kotlinx.android.synthetic.main.coin_list_item.view.*
import java.util.ArrayList

class FavouriteCoinAdapter(val favList: ArrayList<Coin>) :
    RecyclerView.Adapter<FavouriteCoinAdapter.CoinViewHolder>() {
    class CoinViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.coin_list_item, parent, false)
        return FavouriteCoinAdapter.CoinViewHolder(view)
    }

    override fun getItemCount(): Int {
        return favList.size
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.view.name.text = favList[position].name
        holder.view.symbol.text = favList[position].symbol

        holder.view.setOnClickListener {
            val action =
                FavouriteCoinListFragmentDirections.actionFavouriteCoinListFragmentToCoinDetailFragment(
                    favList[position].coinId,
                    true
                )
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun updateCoinList(newCoinList: List<Coin>) {
        favList.clear()
        favList.addAll(newCoinList)
        notifyDataSetChanged()

    }
}