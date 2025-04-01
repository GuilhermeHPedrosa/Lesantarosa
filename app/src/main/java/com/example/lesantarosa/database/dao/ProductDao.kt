package com.example.lesantarosa.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.lesantarosa.models.entities.Item
import com.example.lesantarosa.models.data.ProductItem
import com.example.lesantarosa.models.entities.Product
import com.example.lesantarosa.ui.fragment.formatEntity

@Dao
interface ProductDao: ItemDao {

    // ===================== Product Queries =====================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProduct(product: Product)

    @Query("DELETE FROM product WHERE itemId = :itemId")
    suspend fun removeProduct(itemId: Long)

    @Query("SELECT * FROM product WHERE itemId = :itemId")
    suspend fun getProductByItemId(itemId: Long): Product?

    @Query("SELECT * FROM item INNER JOIN product ON item.itemId = product.itemId WHERE title LIKE '%' || :search || '%'")
    fun searchProductsForSale(search: String): LiveData<List<ProductItem>>

    // ===================== Overridden ItemDao Queries =====================

    @Transaction
    override suspend fun save(item: Item) {
        if (item !is ProductItem) throw IllegalArgumentException("Invalid Product")

        saveItem(item)
        saveProduct(item.formatEntity())
    }

    @Transaction
    override suspend fun remove(itemId: Long) {
        removeItem(itemId)
        removeProduct(itemId)
    }

    @Transaction
    override suspend fun getItemById(itemId: Long): Item? {
        val item = getItemRawById(itemId)
        val product = getProductByItemId(itemId)

        return if(item != null && product != null) {
            ProductItem.createProduct(item, product.weight, product.packaging, product.salesCount)
        } else null
    }

    @Query("SELECT item.* FROM item INNER JOIN product ON item.itemId = product.itemId WHERE item.title LIKE '%' || :search || '%'")
    override fun searchItems(search: String): LiveData<List<Item>>

}