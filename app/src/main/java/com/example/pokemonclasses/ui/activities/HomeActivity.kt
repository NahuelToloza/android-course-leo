package com.example.pokemonclasses.ui.activities

import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.pokemonclasses.R
import com.example.pokemonclasses.data.User
import com.example.pokemonclasses.databinding.ActivityHomeBinding
import com.example.pokemonclasses.databinding.ActivityLoginBinding
import com.example.pokemonclasses.ui.activities.LoginActivity.Companion.ARGUMENT_USERNAME
import com.example.pokemonclasses.ui.fragments.HomeFragment
import com.example.pokemonclasses.utils.parcelable

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getArguments()
        setupViews()
        setupHomeFragment()
    }

    private fun getArguments() {
        user = intent.parcelable(ARGUMENT_USERNAME)
    }

    private fun setupViews() {
        val beforeText = binding.tvTitle.text
        val resultText = " $beforeText ${user?.username}"
        binding.tvTitle.text = resultText
    }

    private fun setupHomeFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)

            val homeFragment = HomeFragment.newInstance(user?.username ?: "", 20, "Pedro")
            replace(binding.fragmentContainerView.id, homeFragment)
        }
    }
}