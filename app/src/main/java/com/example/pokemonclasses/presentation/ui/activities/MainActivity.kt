package com.example.pokemonclasses.presentation.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pokemonclasses.R
import com.example.pokemonclasses.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
    }

    // Click left toolbar icon
    override fun onSupportNavigateUp(): Boolean {
        return getNavController().navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun getNavController() =
        (supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment).navController

    private fun setupViews() {
        setSupportActionBar(binding.toolbar)
        setupDrawerMenu()
    }

    private fun setupDrawerMenu() {
        val navController = getNavController()

        binding.navView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment), binding.drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)

        //Hide toolbar icon in some screens
        navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.show()
            when {
                FRAGMENTS_WITHOUT_TOOLBAR_ICON.all { it == destination.id } -> supportActionBar?.setDisplayHomeAsUpEnabled(
                    false
                )
                R.id.logoutFragment == destination.id -> supportActionBar?.hide()
                else -> supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }
    }

    companion object {
        private val FRAGMENTS_WITHOUT_TOOLBAR_ICON =
            listOf(R.id.loginFragment, R.id.registerFragment)
    }
}