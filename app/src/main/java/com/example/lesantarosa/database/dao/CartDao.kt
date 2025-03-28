package com.example.lesantarosa.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.lesantarosa.models.entities.CartItem
import com.example.lesantarosa.models.data.CartProduct
import com.example.lesantarosa.models.data.CartSummary
import com.example.lesantarosa.models.data.SaleSummary

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(cartItem: CartItem): Long

    @Query("UPDATE cart SET quantity = quantity + :quantity WHERE itemId = :itemId")
    suspend fun update(itemId: Long, quantity: Int)

    @Query("DELETE FROM cart WHERE itemId = :itemId")
    suspend fun remove(itemId: Long)

    @Query("DELETE FROM cart")
    suspend fun removeAll()

    @Transaction
    suspend fun addOrUpdate(cartItem: CartItem) {
        if (save(cartItem) == -1L) { update(cartItem.itemId, cartItem.quantity) }
    }

    @Query("SELECT i.itemId, i.title, i.description, i.image, i.price, i.createdAt, i.updatedAt, p.discountPrice, p.salesCount, c.quantity FROM cart c INNER JOIN product p ON c.itemId = p.itemId INNER JOIN item i ON p.itemId = i.itemId")
    fun getCartProducts(): LiveData<List<CartProduct>>

    @Query("""
        SELECT 
            SUM(quantity) as totalQuantity,
            SUM(quantity * price) as totalPrice
        FROM cart
        INNER JOIN item ON cart.itemId = item.itemId 
        INNER JOIN product ON cart.itemId = product.itemId
    """)
    fun getSaleSummary(): LiveData<SaleSummary>

    @Query("""
        SELECT 
            SUM(quantity) as totalQuantity, 
            SUM(quantity * price) as totalPrice, 
            SUM(quantity * discountedPrice) as totalDiscounts, 
            SUM(quantity * price) - SUM(quantity * COALESCE(discountedPrice, 0)) as finalPrice
        FROM cart 
        INNER JOIN item ON cart.itemId = item.itemId 
        INNER JOIN product ON cart.itemId = product.itemId
    """)
    fun getCartSummary(): LiveData<CartSummary>

}