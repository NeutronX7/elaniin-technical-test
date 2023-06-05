package com.elaniin.technical_test.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.elaniin.technical_test.R
import com.elaniin.technical_test.databinding.ItemPokemonBinding
import com.elaniin.technical_test.databinding.ItemRegionBinding
import com.elaniin.technical_test.models.pokedex_region.PokemonEntry
import com.elaniin.technical_test.utils.ClickListener

class PokemonAdapter(listener: ClickListener, context: Context) :
    RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    private var listener: ClickListener? = listener
    private var contextAdapter: Context = context
    private val pokemonItemList = mutableListOf<PokemonEntry>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = R.layout.item_pokemon
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return pokemonItemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pokemonItemList[position], listener!!)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<PokemonEntry>) {
        pokemonItemList.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        fun bind(item: PokemonEntry, listenerBind: ClickListener) {
            Log.d("ERROR", "POSITIVO")
            with(ItemPokemonBinding.bind(itemView)) {
                txtPokemon.text = item.pokemon_species.name
                Glide.with(contextAdapter)
                    .load("https://img.pokemondb.net/sprites/black-white/normal/${item.pokemon_species.name}.png")
                    .placeholder(R.drawable.pokemonempty)
                    .into(imgPokemon)
                listener = listenerBind
            }
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener?.onClickItemListener(adapterPosition)
        }

    }

}