package com.elaniin.technical_test.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elaniin.technical_test.R
import com.elaniin.technical_test.databases.Team
import com.elaniin.technical_test.databinding.ItemPokemonTeamBinding
import com.elaniin.technical_test.databinding.ItemPokemonTeamSelectedBinding
import com.elaniin.technical_test.utils.ClickListener

class PokemonTeamSelectedAdapter(context: Context, listener: ClickListener)
    : RecyclerView.Adapter<PokemonTeamSelectedAdapter.ViewHolder>(){

    private var listener: ClickListener? = listener
    private var contextAdapter: Context = context
    private val pokemonItemList = mutableListOf<Team>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonTeamSelectedAdapter.ViewHolder {
        val layout = R.layout.item_pokemon_team_selected
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pokemonItemList[position], listener!!)
    }

    override fun getItemCount(): Int {
        return pokemonItemList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<Team>) {
        pokemonItemList.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        fun bind(item: Team, listenerBind: ClickListener) {
            with(ItemPokemonTeamSelectedBinding.bind(itemView)) {
                Glide.with(contextAdapter)
                    .load(item.pokemonList[adapterPosition].pokemonPhoto)
                    .placeholder(R.drawable.pokemonempty)
                    .into(imgPokemon)
                listener = listenerBind
                txtPokemonName.text = item.pokemonList[adapterPosition].pokemonName

            }

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener?.onClickItemListener(adapterPosition)
        }

    }
}