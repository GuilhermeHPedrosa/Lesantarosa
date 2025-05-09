package com.example.lesantarosa.di.modules

import androidx.room.Room
import com.example.lesantarosa.database.AppDatabase
import com.example.lesantarosa.database.dao.CartDao
import com.example.lesantarosa.database.dao.CategoryDao
import com.example.lesantarosa.database.dao.IngredientDao
import com.example.lesantarosa.database.dao.ItemDao
import com.example.lesantarosa.database.dao.OrderDao
import com.example.lesantarosa.database.dao.ProductDao
import com.example.lesantarosa.database.dao.RecipeDao
import com.example.lesantarosa.database.dao.StockDao
import com.example.lesantarosa.database.utils.FragmentKeys.BASE_URL
import com.example.lesantarosa.models.enums.ItemType
import com.example.lesantarosa.repository.CartRepository
import com.example.lesantarosa.repository.CategoryRepository
import com.example.lesantarosa.repository.ItemRepository
import com.example.lesantarosa.repository.OrderRepository
import com.example.lesantarosa.repository.StockRepository
import com.example.lesantarosa.retrofit.service.CategoryService
import com.example.lesantarosa.retrofit.service.IngredientService
import com.example.lesantarosa.retrofit.service.ItemService
import com.example.lesantarosa.retrofit.service.OrderService
import com.example.lesantarosa.retrofit.service.ProductService
import com.example.lesantarosa.retrofit.service.RecipeService
import com.example.lesantarosa.retrofit.service.StockService
import com.example.lesantarosa.retrofit.webclient.CategoryWebClient
import com.example.lesantarosa.retrofit.webclient.ItemWebClient
import com.example.lesantarosa.retrofit.webclient.OrderWebClient
import com.example.lesantarosa.retrofit.webclient.StockWebClient
import com.example.lesantarosa.ui.viewmodel.CartViewModel
import com.example.lesantarosa.ui.viewmodel.CheckoutViewModel
import com.example.lesantarosa.ui.viewmodel.InventoryViewModel
import com.example.lesantarosa.ui.viewmodel.ManagementViewModel
import com.example.lesantarosa.ui.viewmodel.OrderViewModel
import com.example.lesantarosa.ui.viewmodel.PaymentViewModel
import com.example.lesantarosa.ui.viewmodel.SaleViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val DATABASE_NAME = "Lesantarosa.db"

val appModules = module {

    // ===================== Dao Interfaces =====================

    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    single<ProductDao> { get<AppDatabase>().productDao() }
    single<RecipeDao> { get<AppDatabase>().recipeDao() }
    single<IngredientDao> { get<AppDatabase>().ingredientDao() }

    factory<ItemDao> { (itemType: ItemType) ->
        when(itemType) {
            ItemType.PRODUCT -> get<ProductDao>()
            ItemType.RECIPE -> get<RecipeDao>()
            ItemType.INGREDIENT -> get<IngredientDao>()
        }
    }

    single<StockDao> { get<AppDatabase>().stockDao() }

    single<CategoryDao> { get<AppDatabase>().categoryDao() }

    single<CartDao> { get<AppDatabase>().cartDao() }

    single<OrderDao> { get<AppDatabase>().orderDao() }

    // ===================== Services =====================

    single<OkHttpClient> {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
    }

    single<ProductService> {
        get<Retrofit>().create(ProductService::class.java)
    }

    single<RecipeService> {
        get<Retrofit>().create(RecipeService::class.java)
    }

    single<IngredientService> {
        get<Retrofit>().create(IngredientService::class.java)
    }

    factory<ItemService> { (itemType: ItemType) ->
        when (itemType) {
            ItemType.PRODUCT -> get<ProductService>()
            ItemType.RECIPE -> get<RecipeService>()
            ItemType.INGREDIENT -> get<IngredientService>()
        }
    }

    single<StockService> {
        get<Retrofit>().create(StockService::class.java)
    }

    single<CategoryService> {
        get<Retrofit>().create(CategoryService::class.java)
    }

    single<OrderService> {
        get<Retrofit>().create(OrderService::class.java)
    }

    // ===================== Web Clients =====================

    single<ItemWebClient> { (itemType: ItemType) ->
        ItemWebClient(get<ItemService> { parametersOf(itemType) })
    }

    single<StockWebClient> {
        StockWebClient(get<StockService>())
    }

    single<CategoryWebClient> {
        CategoryWebClient(get<CategoryService>())
    }

    single<OrderWebClient> {
        OrderWebClient(get<OrderService>())
    }

    // ===================== Repositories =====================

    factory<ItemRepository> { (itemType: ItemType) ->
        ItemRepository(
            get<ItemDao> { parametersOf(itemType) },
            get<ItemWebClient> { parametersOf(itemType) }
        )
    }

    single<StockRepository> { StockRepository(get<StockDao>(), get<StockWebClient>()) }

    single<CategoryRepository> { CategoryRepository(get<CategoryDao>(), get<CategoryWebClient>()) }

    single<CartRepository> { CartRepository(get<ProductDao>(), get<CartDao>()) }

    single<OrderRepository> { OrderRepository(get<OrderDao>(), get<OrderWebClient>()) }

    // ===================== ViewModels =====================

    viewModel<InventoryViewModel> { (itemType: ItemType) ->
        InventoryViewModel(
            get<ItemRepository> { parametersOf(itemType) },
            get<StockRepository>(),
            get<CategoryRepository>()
        )
    }

    viewModel<ManagementViewModel> { (itemId: Long?, itemType: ItemType) ->
        ManagementViewModel(
            get<ItemRepository> { parametersOf(itemType) },
            get<StockRepository>(),
            itemId
        )
    }

    viewModel<SaleViewModel> {
        SaleViewModel(get<CartRepository>())
    }

    viewModel<CartViewModel> {
        CartViewModel(get<CartRepository>())
    }

    viewModel<PaymentViewModel> { (finalPaymentPrice: Double) ->
        PaymentViewModel(finalPaymentPrice)
    }

    viewModel<CheckoutViewModel> {
        CheckoutViewModel(get<CartRepository>(), get<OrderRepository>())
    }

    viewModel<OrderViewModel> {
        OrderViewModel(get<OrderRepository>())
    }
}