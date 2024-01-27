package com.example.pokemonclasses.presentation.ui.fragments.home.menu

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.pokemonclasses.R
import com.example.pokemonclasses.databinding.FragmentLogoutBinding
import com.example.pokemonclasses.presentation.ui.fragments.LoginFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogoutFragment: Fragment() {
    private var _binding: FragmentLogoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LogoutViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateData()
        navigateToLogginFragment()
    }

    private fun navigateToLogginFragment() {
        val action = LogoutFragmentDirections.actionLogoutFragmentToLoginFragment()
        Handler(Looper.getMainLooper()).postDelayed({
            // navigate to fragment after 3 seconds to be able to appreciate the animation
            findNavController().navigate(action)
        }, 3000) // = 3 seconds
    }

    private fun updateData() {
        viewModel.updateData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}