package com.elaniin.technical_test.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elaniin.technical_test.R
import com.elaniin.technical_test.databinding.ItemRegionBinding
import com.elaniin.technical_test.models.regions.Result
import com.elaniin.technical_test.utils.ClickListener

class RegionsAdapter(listener: ClickListener, context: Context) : RecyclerView.Adapter<RegionsAdapter.ViewHolder>() {

    private var listener: ClickListener? = listener
    private var contextAdapter: Context = context
    private val regionItemList = mutableListOf<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = R.layout.item_region
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //if (listener != null) {
            holder.bind(regionItemList[position], listener!!)
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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        fun bind(item: Result, listenerBind: ClickListener) {
            Log.d("ERROR", "POSITIVO")
            with(ItemRegionBinding.bind(itemView)) {
                txtRegion.text = item.name
                /*itemView.setOnClickListener {
                    //istener?.onClickItemListener(1)
                    Log.d("ERROR", "CLICKEADO"+item.name)
                }*/
                listener = listenerBind
            }
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener?.onClickItemListener(adapterPosition)
        }

    }

}