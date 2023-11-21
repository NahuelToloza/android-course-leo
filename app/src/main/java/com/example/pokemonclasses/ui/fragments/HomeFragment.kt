package com.example.pokemonclasses.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pokemonclasses.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getParameters()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getParameters() {
        val username = arguments?.getString(ARG_USERNAME)
        val age = arguments?.getString(ARG_AGE)
        val name = arguments?.getString(ARG_NAME)
        val resultText = "username: $username, age: $age, name: $name"
        binding.tvTitle.text = resultText
    }

    companion object {
        private const val ARG_USERNAME = "ARG_USERNAME"
        private const val ARG_AGE = "ARG_AGE"
        private const val ARG_NAME = "ARG_NAME"

        fun newInstance(
            username: String,
            age: Int? = null,
            name: String? = null,
        ) = HomeFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_USERNAME, username)
                putInt(ARG_AGE, age ?: 0)
                putString(ARG_NAME, name)
            }
        }
    }
}

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // TODO: Use the param
            val userName = it.getString(ARG_USERNAME)
        }
    }*/

   /* companion object {
        private const val ARG_USERNAME = "ARG_USERNAME"

        fun newInstance(
            param1: String
        ) = HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USERNAME, param1)
                }
            }
    }*/