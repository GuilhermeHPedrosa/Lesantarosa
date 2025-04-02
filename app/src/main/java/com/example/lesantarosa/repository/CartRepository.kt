package com.example.lesantarosa.repository

import androidx.lifecycle.LiveData
import com.example.lesantarosa.database.dao.CartDao
import com.example.lesantarosa.database.dao.ProductDao
import com.example.lesantarosa.models.entities.CartItem
import com.example.lesantarosa.models.data.CartProduct
import com.example.lesantarosa.models.data.CartSummary
import com.example.lesantarosa.models.data.ProductItem
import com.example.lesantarosa.models.data.SaleSummary

class CartRepository(
    private val productDao: ProductDao,
    private val cartDao: CartDao
) {

    suspend fun addOrUpdate(cartItem: CartItem) {
        cartDao.addOrUpdate(cartItem)
    }

    suspend fun remove(itemId: Long) {
        cartDao.remove(itemId)
    }

    suspend fun removeAll() {
        cartDao.removeAll()
    }

    suspend fun finalizeOrder(): List<CartProduct> {
        return cartDao.finalizeOrder()
    }

    fun getCartProducts(): LiveData<List<CartProduct>> {
        return cartDao.getCartProducts()
    }

    fun searchProductsForSale(search: String): LiveData<List<ProductItem>> {
        return productDao.searchProductsForSale(search)
    }

    fun getSaleSummary(): LiveData<SaleSummary> {
        return cartDao.getSaleSummary()
    }

    fun getCartSummary(): LiveData<CartSummary> {
        return cartDao.getCartSummary()
    }
}