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

    @Transaction
    suspend fun addOrUpdate(cartItem: CartItem) {
        if (save(cartItem) == -1L) { update(cartItem.itemId, cartItem.quantity) }
    }

    @Query("DELETE FROM cart WHERE itemId = :itemId")
    suspend fun remove(itemId: Long)

    @Query("DELETE FROM cart")
    suspend fun removeAll()

    @Transaction
    suspend fun finalizeOrder(): List<CartProduct> {
        val cartProducts = getCurrentCartProducts()
        removeAll()
        return cartProducts
    }

    @Query("SELECT i.title, i.image, i.price, c.quantity, c.discountedPrice, c.note FROM cart c INNER JOIN item i ON c.itemId = i.itemId")
    suspend fun getCurrentCartProducts(): List<CartProduct>

    @Query("SELECT i.title, i.image, i.price, c.quantity, c.discountedPrice, c.note FROM cart c INNER JOIN item i ON c.itemId = i.itemId")
    fun getCartProducts(): LiveData<List<CartProduct>>

    @Query("""
        SELECT 
            SUM(quantity) as itemCount,
            SUM(quantity * price) as totalAmount
        FROM cart
        INNER JOIN item ON cart.itemId = item.itemId 
        INNER JOIN product ON cart.itemId = product.itemId
    """)
    fun getSaleSummary(): LiveData<SaleSummary>

    @Query("""
        SELECT 
            SUM(quantity) as itemCount, 
            SUM(quantity * price) as totalAmount, 
            SUM(quantity * discountedPrice) as totalDiscounts
        FROM cart 
        INNER JOIN item ON cart.itemId = item.itemId 
        INNER JOIN product ON cart.itemId = product.itemId
    """)
    fun getCartSummary(): LiveData<CartSummary>

}