package com.example.lesantarosa.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lesantarosa.database.converter.CartProductConverter
import com.example.lesantarosa.database.converter.DifficultyConverter
import com.example.lesantarosa.database.converter.ItemTypeConverter
import com.example.lesantarosa.database.converter.MeasureUnitConverter
import com.example.lesantarosa.database.converter.OrderStatusConverter
import com.example.lesantarosa.database.converter.PaymentConverter
import com.example.lesantarosa.database.dao.CartDao
import com.example.lesantarosa.database.dao.CategoryDao
import com.example.lesantarosa.database.dao.IngredientDao
import com.example.lesantarosa.database.dao.OrderDao
import com.example.lesantarosa.database.dao.ProductDao
import com.example.lesantarosa.database.dao.RecipeDao
import com.example.lesantarosa.database.dao.StockDao
import com.example.lesantarosa.models.entities.CartItem
import com.example.lesantarosa.models.entities.Category
import com.example.lesantarosa.models.entities.Ingredient
import com.example.lesantarosa.models.entities.Item
import com.example.lesantarosa.models.entities.Order
import com.example.lesantarosa.models.entities.Product
import com.example.lesantarosa.models.entities.Recipe
import com.example.lesantarosa.models.entities.Stock

@Database(entities = [Item::class, Product::class, Recipe::class, Ingredient::class, Stock::class, Category::class, CartItem::class, Order::class], version = 164)
@TypeConverters(ItemTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun productDao(): ProductDao

    abstract fun recipeDao(): RecipeDao

    abstract fun ingredientDao(): IngredientDao

    abstract fun stockDao(): StockDao

    abstract fun categoryDao(): CategoryDao

    abstract fun cartDao(): CartDao

    abstract fun orderDao(): OrderDao

}