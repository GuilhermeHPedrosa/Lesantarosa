package com.example.lesantarosa.ui.adapter.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.lesantarosa.databinding.CardStockItemBinding
import com.example.lesantarosa.models.entities.Stock
import com.example.lesantarosa.ui.fragment.formatStock

class StockAdapter(
    private val context: Context,
    var setItemClick: (itemId: Long) -> Unit = {}
): ListAdapter<Stock>() {

    inner class ViewHolder(private val binding: CardStockItemBinding): ListViewHolder<Stock>(binding.root) {

        private lateinit var stock: Stock

        init {
            itemView.setOnClickListener {
                if (::stock.isInitialized) { setItemClick(stock.itemId) }
            }
        }

        override fun setViewInfo(value: Stock) {
            this.stock = value

            val currentStock = binding.itemActualStock
            currentStock.text = stock.currentStock.formatStock()

            val minStock = binding.itemMinStock
            minStock.text = stock.minStock.formatStock()

            val maxStock = binding.itemMaxStock
            maxStock.text = stock.maxStock.formatStock()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = CardStockItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
}