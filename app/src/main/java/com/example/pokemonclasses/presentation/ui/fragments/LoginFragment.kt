package com.example.pokemonclasses.presentation.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pokemonclasses.databinding.FragmentLoginBinding
import com.example.pokemonclasses.presentation.ui.viewmodel.profile.LoginViewModel
import com.example.pokemonclasses.utils.gone
import com.example.pokemonclasses.utils.isValidEmail
import com.example.pokemonclasses.utils.isValidPassword
import com.example.pokemonclasses.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupClickListeners()
        setupData()
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiModel ->
            uiModel.navigateToHome?.getContentIfNotHandled()?.let {
                navigateToHomeFragment()
            }
            uiModel.showErrorInvalidUser?.getContentIfNotHandled()?.let {
                showErrorInvalidUser()
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            loginButtonClicked()
        }
        binding.tvRegister.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(text: Editable?) {
                // Cada vez que se cambie el texto del editext
                handleEnabledButton()
                if (text.toString().isValidEmail().not()) {
                    binding.etEmail.error = "El email is invalido"
                }
            }
        })
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(text: Editable?) {
                // Cada vez que se cambie el texto del editext
                handleEnabledButton()
                val password = text.toString()
                if (password.isValidPassword().not()) {
                    binding.etPassword.error =
                        "La contrasenia no es valida, deberia tener una minuscula, mayuscula y numeros"
                }
            }
        })
    }

    private fun setupData() {
        viewModel.setupData()
    }

    private fun loginButtonClicked() {
        // validate
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        viewModel.loginButtonClicked(email, password)
    }

    private fun handleEnabledButton() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        binding.btnLogin.isEnabled = (email.isNotEmpty() && password.isNotEmpty())
        binding.tvLoginError.gone()
    }

    private fun navigateToHomeFragment() {
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        findNavController().navigate(action)
    }

    private fun showErrorInvalidUser() {
        binding.tvLoginError.visible()
    }
}