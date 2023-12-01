package com.example.pokemonclasses.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.pokemonclasses.data.User
import com.example.pokemonclasses.databinding.FragmentLoginBinding
import com.example.pokemonclasses.persistence.SharedPreferencesManager
import com.example.pokemonclasses.persistence.repository.UserRepository
import com.example.pokemonclasses.utils.gone
import com.example.pokemonclasses.utils.isValidEmail
import com.example.pokemonclasses.utils.isValidPassword
import com.example.pokemonclasses.utils.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

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
        setupClickListeners()
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

    private fun loginButtonClicked() {
        // validate
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        lifecycleScope.launch(Dispatchers.IO) {
            val savedUser = UserRepository(requireActivity()).getUser(email)
            if (savedUser != null && savedUser.email == email && savedUser.password == password) {
                val user = User(email, password)
                withContext(Dispatchers.Main) {
                    val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment(user)
                    findNavController().navigate(action)
                }
            } else {
                withContext(Dispatchers.Main) {
                    binding.tvLoginError.visible()
                }
            }
        }
    }

    private fun handleEnabledButton() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        binding.btnLogin.isEnabled = (email.isNotEmpty() && password.isNotEmpty())
        binding.tvLoginError.gone()
    }
}