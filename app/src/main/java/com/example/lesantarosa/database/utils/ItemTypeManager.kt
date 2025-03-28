package com.example.lesantarosa.database.utils

import com.example.lesantarosa.models.enums.ItemType

object ItemTypeManager {

    private var _itemType: ItemType? = null

    val itemType: ItemType
        get() = _itemType ?: throw IllegalStateException("ItemType has not been initialized")

    fun defineItemType(itemType: ItemType) {
        _itemType = itemType
    }

    fun defineItemType(itemType: String) {
        _itemType = ItemType.valueOf(itemType)
    }
}