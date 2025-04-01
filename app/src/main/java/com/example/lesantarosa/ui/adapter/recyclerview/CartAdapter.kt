package com.example.lesantarosa.ui.adapter.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.lesantarosa.databinding.CardItemBinding
import com.example.lesantarosa.models.data.CartProduct
import com.example.lesantarosa.models.data.ProductItem

class CartAdapter(
    private val context: Context,
    var setItemClick: () -> Unit = {}
): ListAdapter<CartProduct>() {

    inner class ViewHolder(private val binding: CardItemBinding): ListViewHolder<CartProduct>(binding.root) {

        private lateinit var cartProduct: CartProduct

        init {
            itemView.setOnClickListener {
                if(::cartProduct.isInitialized) { setItemClick() }
            }
        }

        override fun setViewInfo(value: CartProduct) {
            this.cartProduct = value

            val title = binding.itemTitle
            title.text = cartProduct.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder<CartProduct> {
        val inflater = LayoutInflater.from(context)
        val binding = CardItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
}