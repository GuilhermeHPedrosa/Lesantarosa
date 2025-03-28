package com.example.lesantarosa.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
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
import com.example.lesantarosa.ui.navigation.NavigationConfig.drawerDestinations
import com.example.lesantarosa.ui.navigation.NavigationConfig.drawerNavigationActions
import com.example.lesantarosa.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Date
import kotlin.random.Random

class MainActivity: AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    private val viewModel: MainViewModel by viewModel()

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val drawerLayout by lazy { binding.drawerLayout }
    private val drawerNavigation by lazy { binding.drawerNavigation }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        setupNavigation()

        setupDrawerAccessListener()
        setupDrawerNavigationListener()

        observeVisualComponents()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(drawerDestinations, drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)
        drawerNavigation.setupWithNavController(navController)
    }

    private fun setupDrawerNavigationListener() {
        drawerNavigation.setNavigationItemSelectedListener{ menuItem ->
            drawerNavigationActions[menuItem.itemId]
                ?.let { navigateTo(it) ; true } ?: false
        }
    }

    private fun setupDrawerAccessListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val mode = if (destination.id in drawerDestinations) LOCK_MODE_UNLOCKED else LOCK_MODE_LOCKED_CLOSED
            drawerLayout.setDrawerLockMode(mode)
        }
    }

    private fun observeVisualComponents() {
        val toolbar = binding.appBarMain.toolbar
        val tabLayout = binding.appBarMain.tabLayout

        viewModel.visualComponents.observe(this) { visualComponents ->
            toolbar.visibility = if (visualComponents.toolbar) { View.VISIBLE } else View.GONE
            tabLayout.visibility = if (visualComponents.tabLayout) { View.VISIBLE } else View.GONE
        }
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
                ProductItem(Random.nextLong(), "Bala Brigadeiro", "Delicia", "", 3.5, Date().time, Date().time, null, null, null, null, 0),
                ProductItem(Random.nextLong(), "Bala Morango", "Delicia", "", 3.5, Date().time, Date().time, null, null, null, null, 0),
                ProductItem(Random.nextLong(), "Bala Ninho", "Delicia", "", 3.5, Date().time, Date().time, null, null, null, null, 0),
                ProductItem(Random.nextLong(), "Bala Laranja", "Delicia", "", 3.5, Date().time, Date().time, null, null, null, null, 0),
            ).forEach { pDao.value.save(it) }

            listOf(
                RecipeItem(Random.nextLong(), "Brigadeiro Chocolate", "Delicia", "", 3.5, Date().time, Date().time, null, 3, 31, 1, Difficulty.MEDIUM),
                RecipeItem(Random.nextLong(), "Brigadeiro Morango", "Delicia", "", 3.5, Date().time, Date().time, null, 3, 31, 1, Difficulty.MEDIUM),
                RecipeItem(Random.nextLong(), "Brigadeiro Ninho", "Delicia", "", 3.5, Date().time, Date().time, null, 3, 31, 1, Difficulty.MEDIUM),
                RecipeItem(Random.nextLong(), "Brigadeiro Laranja", "Delicia", "", 3.5, Date().time, Date().time, null, 3, 31, 1, Difficulty.MEDIUM)
            ).forEach { rDao.value.save(it) }

            listOf(
                IngredientItem(Random.nextLong(), "Chocolate", "Delicia", "", 3.5, Date().time, Date().time, null, null, null, null),
                IngredientItem(Random.nextLong(), "Morango", "Delicia", "", 3.5, Date().time, Date().time, null, null, null, null),
                IngredientItem(Random.nextLong(), "Ninho", "Delicia", "", 3.5, Date().time, Date().time, null, null, null, null),
                IngredientItem(Random.nextLong(), "Laranja", "Delicia", "", 3.5, Date().time, Date().time, null, null, null, null)
            ).forEach { iDao.value.save(it) }
        }
    }
}