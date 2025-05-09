package com.example.lesantarosa.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.lesantarosa.databinding.CardCategoryBinding
import com.example.lesantarosa.models.entities.Category

class CategoryAdapter(
    private val context: Context,
    var setItemClick: (category: Category) -> Unit = {}
): ListAdapter<Category>() {

    inner class ViewHolder(private val binding: CardCategoryBinding): ListViewHolder<Category>(binding.root) {

        private lateinit var category: Category

        init {
            itemView.setOnClickListener {
                if (::category.isInitialized) { setItemClick(category) }
            }
        }

        override fun setViewInfo(value: Category) {
            this.category = value

            val title = binding.categoryTitle
            title.text = value.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = CardCategoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
}