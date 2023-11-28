package com.example.pokemonclasses.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pokemonclasses.data.User
import com.example.pokemonclasses.databinding.FragmentRegisterBinding
import com.example.pokemonclasses.persistence.SharedPreferencesManager

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
            SharedPreferencesManager().saveUser(requireActivity(), user)

            // Navigate to login
            val action = RegisterFragmentDirections.actionLoginFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }
}