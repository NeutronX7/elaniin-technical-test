package com.elaniin.technical_test.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elaniin.technical_test.R
import com.elaniin.technical_test.databases.PokemonTeam
import com.elaniin.technical_test.databinding.ItemPokemonTeamBinding
import com.elaniin.technical_test.utils.ClickListener

class PokemonTeamItemAdapter(context: Context, listener: ClickListener)
    : RecyclerView.Adapter<PokemonTeamItemAdapter.ViewHolder>() {

    private var listener: ClickListener? = listener
    private var contextAdapter: Context = context
    private val pokemonItemList = mutableListOf<PokemonTeam>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonTeamItemAdapter.ViewHolder {
        val layout = R.layout.item_pokemon_team
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PokemonTeamItemAdapter.ViewHolder, position: Int) {
        holder.bind(pokemonItemList[position], listener!!)
    }

    override fun getItemCount(): Int {
        return pokemonItemList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<PokemonTeam>) {
        pokemonItemList.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        fun bind(item: PokemonTeam, listenerBind: ClickListener) {
            with(ItemPokemonTeamBinding.bind(itemView)) {
                Glide.with(contextAdapter)
                    .load(item.pokemonPhoto)
                    .placeholder(R.drawable.pokemonempty)
                    .into(imgPokemon)
                listener = listenerBind
                txtPokemonName.text = item.pokemonName

            }

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener?.onClickItemListener(adapterPosition)
        }

    }

}