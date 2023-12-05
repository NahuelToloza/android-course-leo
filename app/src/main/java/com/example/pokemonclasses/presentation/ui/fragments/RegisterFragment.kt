package com.example.pokemonclasses.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.pokemonclasses.data.User
import com.example.pokemonclasses.databinding.FragmentRegisterBinding
import com.example.pokemonclasses.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.setOnClickListener {
            // Save user
            val user = User(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString(),
            )
            lifecycleScope.launch(Dispatchers.IO) {
                UserRepository(requireActivity()).saveUser(user)
            }

            // Navigate to login
            val action = RegisterFragmentDirections.actionLoginFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }
}