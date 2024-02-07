package com.example.pokemonclasses.presentation.ui.activities

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pokemonclasses.R
import com.example.pokemonclasses.databinding.ActivityMainBinding
import com.example.pokemonclasses.presentation.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObservers()
        setupViews()
    }

    // Click left toolbar icon
    override fun onSupportNavigateUp(): Boolean {
        return getNavController().navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun setupObservers() {
        mainViewModel.showDrawerData.observe(this) {
            it.getContentIfNotHandled()?.let { uri ->
                setupProfileImage(uri)
            }
        }
    }

    private fun getNavController() =
        (supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment).navController

    private fun setupViews() {
        setSupportActionBar(binding.toolbar)
        setupDrawerMenu()
        val headerView = binding.navView.getHeaderView(0)
        val profileImage = headerView.findViewById<ImageView>(R.id.img_photo)
        profileImage.setOnClickListener {
            mainViewModel.onProfileImageClicked()
        }
    }

    private fun setupDrawerMenu() {
        val navController = getNavController()

        binding.navView.setupWithNavController(navController)
        // setOf([Id de fragment de nivel superior -> icon de hamburguesa])
        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment), binding.drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)

        //Hide toolbar icon in some screens
        navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.show()
            when {
                FRAGMENTS_WITHOUT_TOOLBAR_ICON.all { it == destination.id } ->
                    // Hide icon left toolbar
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)

                //Ocultar toolbar
                (R.id.logoutFragment == destination.id) -> supportActionBar?.hide()

                else -> supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }
        mainViewModel.getDrawerData()
    }

    private fun setupProfileImage(uri: Uri) {
        val headerView = binding.navView.getHeaderView(0)
        val profileImage = headerView.findViewById<ImageView>(R.id.img_photo)

        profileImage.setImageURI(uri)
    }

    companion object {
        private val FRAGMENTS_WITHOUT_TOOLBAR_ICON =
            listOf(R.id.loginFragment)
    }
}