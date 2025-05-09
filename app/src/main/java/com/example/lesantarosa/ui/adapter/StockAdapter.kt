package com.example.lesantarosa.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.lesantarosa.databinding.CardItemBinding
import com.example.lesantarosa.models.data.StockItem

class StockAdapter(
    private val context: Context,
    var setItemClick: (stockItem: StockItem) -> Unit = {}
): ListAdapter<StockItem>() {

    inner class ViewHolder(private val binding: CardItemBinding): ListViewHolder<StockItem>(binding.root) {

        private lateinit var stock: StockItem

        init {
            itemView.setOnClickListener {
                if (::stock.isInitialized) { setItemClick(stock) }
            }
        }

        override fun setViewInfo(value: StockItem) {
            this.stock = value

            val title = binding.itemTitle
            title.text = stock.title

            val description = binding.itemDescription
            description.text = stock.measureUnit?.getUnitName(context)

            val info = binding.itemInfo
            stock.getCurrentStock()?.let { info.text = it }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = CardItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
}