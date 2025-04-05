package com.example.lesantarosa.ui.adapter.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.example.lesantarosa.databinding.CardItemBinding
import com.example.lesantarosa.models.entities.Item
import com.example.lesantarosa.ui.fragment.formatPrice

class ItemAdapter(
    private val context: Context,
    var setItemClick: (itemId: Long) -> Unit = {}
): ListAdapter<Item>() {

    inner class ViewHolder(private val binding: CardItemBinding): ListViewHolder<Item>(binding.root) {

        private lateinit var item: Item

        init {
            itemView.setOnClickListener {
                if (::item.isInitialized) { setItemClick(item.itemId) }
            }
        }

        override fun setViewInfo(value: Item) {
            this.item = value

            val image = binding.itemImage
            image.load(item.image)

            val title = binding.itemTitle
            title.text = item.title

            val category = binding.itemCategory
            category.text = item.description

            val price = binding.itemPrice
            price.text = item.price.formatPrice()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = CardItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
}