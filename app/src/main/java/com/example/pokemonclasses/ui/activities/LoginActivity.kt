package com.example.pokemonclasses.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemonclasses.data.User
import com.example.pokemonclasses.databinding.ActivityLoginBinding
import kotlinx.parcelize.Parcelize
import java.io.Serializable

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            val user = User("Pepito", 20)
            intent.putExtra(ARGUMENT_USERNAME, user)
            startActivity(intent)
        }
    }

    companion object {
        const val ARGUMENT_USERNAME = "ARGUMENT_USERNAME"
    }
}