package com.example.lesantarosa.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.lesantarosa.databinding.CardStockMovementBinding
import com.example.lesantarosa.models.entities.StockMovement

class StockMovementAdapter(
    private val context: Context,
    var setDeleteClick: (stockMovementId: Long) -> Unit = {}
): ListAdapter<StockMovement>() {

    inner class ViewHolder(private val binding: CardStockMovementBinding): ListViewHolder<StockMovement>(binding.root) {

        private lateinit var stockMovement: StockMovement

        init {
            binding.deleteButton.setOnClickListener {
                if (::stockMovement.isInitialized) { setDeleteClick(stockMovement.stockMovementId) }
            }
        }

        override fun setViewInfo(value: StockMovement) {
            this.stockMovement = value

            val quantity = binding.stockMovementQuantity
            quantity.text = stockMovement.quantity.toString()

            val date = binding.stockMovementDate
            date.text = stockMovement.movementAt.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder<StockMovement> {
        val inflater = LayoutInflater.from(context)
        val binding = CardStockMovementBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
}