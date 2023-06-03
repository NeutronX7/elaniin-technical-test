package com.elaniin.technical_test.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elaniin.technical_test.models.regions.Result
import androidx.recyclerview.widget.RecyclerView
import com.elaniin.technical_test.R
import com.elaniin.technical_test.databinding.ItemRegionBinding
import com.elaniin.technical_test.utils.ClickListener

class RegionsAdapter : RecyclerView.Adapter<RegionsAdapter.ViewHolder>() {

    private val listener: ClickListener? = null
    private val regionItemList = mutableListOf<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = R.layout.item_region
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //if (listener != null) {
        Log.d("ENTROONBIND", "ASDSADÑ")
        holder.bind(regionItemList[position])
        //}
    }

    override fun getItemCount(): Int {
        return regionItemList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<Result>) {
        regionItemList.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ClickListener{

        fun bind(item: Result, /*listener: ClickListener*/) {
            Log.d("ERROR", "POSITIVO")
            with(ItemRegionBinding.bind(itemView)) {
                txtRegion.text = item.name
                /*itemView.setOnClickListener {
                    listener.onClickItemListener(1)
                }*/
            }
        }

        override fun onClickItemListener(position: Int) {
            adapterPosition
        }

    }

}