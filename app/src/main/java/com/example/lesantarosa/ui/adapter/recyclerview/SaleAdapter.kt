package com.example.lesantarosa.ui.adapter.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.lesantarosa.databinding.CardItemBinding
import com.example.lesantarosa.models.data.ProductItem

class SaleAdapter(
    private val context: Context,
    var setItemClick: (productItem: ProductItem) -> Unit = {}
): ListAdapter<ProductItem>() {

    inner class ViewHolder(private val binding: CardItemBinding): ListViewHolder<ProductItem>(binding.root) {

        private lateinit var productItem: ProductItem

        init {
            itemView.setOnClickListener {
                if (::productItem.isInitialized) { setItemClick(productItem) }
            }
        }

        override fun setViewInfo(value: ProductItem) {
            this.productItem = value

            val title = binding.itemTitle
            title.text = productItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder<ProductItem> {
        val inflater = LayoutInflater.from(context)
        val binding = CardItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
}