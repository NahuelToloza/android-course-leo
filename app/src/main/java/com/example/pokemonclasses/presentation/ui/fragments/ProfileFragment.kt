package com.example.pokemonclasses.presentation.ui.fragments

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pokemonclasses.databinding.FragmentProfileBinding
import com.example.pokemonclasses.presentation.ui.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d("PERMISSION ANDROID", "isGranted")
            // PERMISSION GRANTED
        } else {
            Log.d("PERMISSION ANDROID", "isNOTGranted")
            // PERMISSION NOT GRANTED
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()
        viewModel.getProfileData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            it.setupViews?.getContentIfNotHandled()?.let { email ->
                setupViews(email)
            }
        }
    }

    private fun setupListeners() {
        binding.imgProfile.setOnClickListener {
            askCameraPermission()
        }
    }

    private fun setupViews(email: String) {
        binding.tvEmailValue.text = email
    }

    private fun askCameraPermission() {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }
}