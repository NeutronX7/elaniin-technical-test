package com.elaniin.technical_test.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elaniin.technical_test.R
import com.elaniin.technical_test.databases.Team
import com.elaniin.technical_test.databinding.ItemTeamBinding
import com.elaniin.technical_test.utils.ClickListener

class PokemonTeamAdapter(context: Context, listener: ClickListener) :
    RecyclerView.Adapter<PokemonTeamAdapter.ViewHolder>() {

    private var listener: ClickListener? = listener
    private var contextAdapter: Context = context
    private val pokemonItemList = mutableListOf<Team>()

    private val adapter = PokemonTeamItemAdapter(context, listener)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = R.layout.item_team
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
    fun setData(position: Int, newData: List<Team>) {
        pokemonItemList.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        fun bind(item: Team, listenerBind: ClickListener) {
            with(ItemTeamBinding.bind(itemView)) {
                listener = listenerBind
                pokemonTeamRecyclerView.adapter = adapter

                adapter.setData(item.pokemonList)
            }
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener?.onClickItemListener(adapterPosition)
        }

    }

}