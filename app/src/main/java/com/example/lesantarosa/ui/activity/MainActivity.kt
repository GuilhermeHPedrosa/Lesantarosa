package com.example.lesantarosa.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.lesantarosa.R
import com.example.lesantarosa.database.dao.IngredientDao
import com.example.lesantarosa.database.dao.ProductDao
import com.example.lesantarosa.database.dao.RecipeDao
import com.example.lesantarosa.databinding.ActivityMainBinding
import com.example.lesantarosa.models.data.IngredientItem
import com.example.lesantarosa.models.data.ProductItem
import com.example.lesantarosa.models.data.RecipeItem
import com.example.lesantarosa.models.enums.Difficulty
import com.example.lesantarosa.models.enums.ItemType
import com.example.lesantarosa.ui.navigation.NavigationConfig.drawerDestinations
import com.example.lesantarosa.ui.navigation.NavigationConfig.drawerNavigationActions
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.Date
import kotlin.random.Random

class MainActivity: AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val drawerLayout by lazy { binding.drawerLayout }
    private val drawerNavigation by lazy { binding.drawerNavigation }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        setupNavigation()

        setupDrawerNavigationListener()
        setupDrawerDestinationListener()

        refreshRepositories()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(drawerDestinations, drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)

        drawerNavigation.setupWithNavController(navController)
        drawerNavigation.setCheckedItem(R.id.menu_home)
    }

    private fun setupDrawerNavigationListener() {
        drawerNavigation.setNavigationItemSelectedListener{ menuItem ->
            drawerNavigationActions[menuItem.itemId]
                ?.let { navigateTo(it) ; true } ?: false
        }
    }

    private fun setupDrawerDestinationListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in drawerDestinations) {
                setupNavigationIcon(R.drawable.ic_menu)
                drawerLayout.setDrawerLockMode(LOCK_MODE_UNLOCKED)
            }  else {
                setupNavigationIcon(R.drawable.ic_back)
                drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
            }
        }
    }

    private fun setupNavigationIcon(icon: Int) {
        val toolbar = binding.appBarMain.toolbar
        toolbar.setNavigationIcon(icon)
    }

    private fun navigateTo(direction: NavDirections) {
        navController.navigate(direction)

        drawerLayout.postDelayed({ drawerLayout.closeDrawer(GravityCompat.START) }, 200)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun refreshRepositories() {
        val pDao = inject<ProductDao>()
        val rDao = inject<RecipeDao>()
        val iDao = inject<IngredientDao>()

        lifecycleScope.launch {
            listOf(
                ProductItem(Random.nextLong(), "Bala Brigadeiro", "Delicia", "", 3.5, Date().time, Date().time, null, ItemType.PRODUCT, null, null, 0),
                ProductItem(Random.nextLong(), "Bala Morango", "Delicia", "", 3.5, Date().time, Date().time, null, ItemType.PRODUCT, null, null, 0),
                ProductItem(Random.nextLong(), "Bala Ninho", "Delicia", "", 3.5, Date().time, Date().time, null, ItemType.PRODUCT, null, null, 0),
                ProductItem(Random.nextLong(), "Bala Laranja", "Delicia", "", 3.5, Date().time, Date().time, null, ItemType.PRODUCT, null, null, 0),
            ).forEach { pDao.value.save(it) }

            listOf(
                RecipeItem(Random.nextLong(), "Brigadeiro Chocolate", "Delicia", "", 3.5, Date().time, Date().time, null, ItemType.RECIPE, 3, 31, 1, Difficulty.MEDIUM),
                RecipeItem(Random.nextLong(), "Brigadeiro Morango", "Delicia", "", 3.5, Date().time, Date().time, null, ItemType.RECIPE, 3, 31, 1, Difficulty.MEDIUM),
                RecipeItem(Random.nextLong(), "Brigadeiro Ninho", "Delicia", "", 3.5, Date().time, Date().time, null, ItemType.RECIPE, 3, 31, 1, Difficulty.MEDIUM),
                RecipeItem(Random.nextLong(), "Brigadeiro Laranja", "Delicia", "", 3.5, Date().time, Date().time, null, ItemType.RECIPE, 3, 31, 1, Difficulty.MEDIUM)
            ).forEach { rDao.value.save(it) }

            listOf(
                IngredientItem(Random.nextLong(), "Chocolate", "Delicia", "", 3.5, Date().time, Date().time, null, ItemType.INGREDIENT, null, null, null),
                IngredientItem(Random.nextLong(), "Morango", "Delicia", "", 3.5, Date().time, Date().time, null, ItemType.INGREDIENT, null, null, null),
                IngredientItem(Random.nextLong(), "Ninho", "Delicia", "", 3.5, Date().time, Date().time, null, ItemType.INGREDIENT, null, null, null),
                IngredientItem(Random.nextLong(), "Laranja", "Delicia", "", 3.5, Date().time, Date().time, null, ItemType.INGREDIENT, null, null, null)
            ).forEach { iDao.value.save(it) }
        }
    }
}