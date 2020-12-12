package com.app.bitcointickerapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.app.bitcointickerapp.R
import com.app.bitcointickerapp.data.model.Coin
import com.app.bitcointickerapp.ui.view.CoinListFragmentDirections
import kotlinx.android.synthetic.main.coin_list_item.view.*
import java.util.ArrayList
import javax.inject.Inject

class CoinAdapter (val coinList:ArrayList<Coin>):RecyclerView.Adapter<CoinAdapter.CoinViewHolder>() {
    class CoinViewHolder(var view:View):RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.coin_list_item,parent,false)
        return CoinViewHolder(view)
    }

    override fun getItemCount(): Int {
        return coinList.size
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.view.name.text=coinList[position].name
        holder.view.symbol.text=coinList[position].symbol
        holder.view.setOnClickListener {
            val action = CoinListFragmentDirections.actionCoinListFragmentToCoinDetailFragment(
                coinList[position].coinId
            )


            Navigation.findNavController(it).navigate(action)
        }
    }

    fun updateCoinList(newCoinList:List<Coin>){
        coinList.clear()
        coinList.addAll(newCoinList)
        notifyDataSetChanged()

    }
}