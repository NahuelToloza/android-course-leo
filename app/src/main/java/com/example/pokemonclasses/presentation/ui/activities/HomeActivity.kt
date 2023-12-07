package com.example.pokemonclasses.presentation.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemonclasses.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
    }

    private fun setupViews() {
        setSupportActionBar(binding.toolbar)
    }
}