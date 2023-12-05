package com.example.pokemonclasses.presentation.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemonclasses.data.User
import com.example.pokemonclasses.databinding.ActivityHomeBinding
import com.example.pokemonclasses.utils.parcelable

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Todo: Agarrar arguments
        getArguments()
        setupViews()
    }

    private fun getArguments() {

    }

    private fun setupViews() {
        setSupportActionBar(binding.toolbar)
    }
}