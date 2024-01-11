package com.example.pokemonclasses.presentation.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemonclasses.data.Pokemon
import com.example.pokemonclasses.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val args: HomeFragmentArgs by navArgs()

    private var pokemonList: MutableList<PokemonItem> = mutableListOf()
    private lateinit var adapter: PokemonListAdapter

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getParameters()
        setupObservers()
        setupListeners()
        viewModel.getAllPokemons()
    }

    private fun setupRecyclerView(pokemonList: List<PokemonItem>) {
        //Setup recycler
        adapter = PokemonListAdapter()
        adapter.submitList(pokemonList)
        //Click to remove
        adapter.setupListener(pokemonClickListener())
        binding.rvList.adapter = adapter
        binding.rvList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getParameters() {
        val email = args.user.email
//        binding.tvTitle.text = "Hola, $email"
    }

    private fun setupObservers() {
        viewModel.pokemonsLiveData.observe(viewLifecycleOwner) {
            setupRecyclerView(it)
        }
    }

    private fun setupListeners() {
        binding.root.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
            findNavController().navigate(action)
        }
        binding.fabAddPokemon.setOnClickListener {
            addNewPokemon()
        }
    }

    private fun addNewPokemon() {
        val newList = pokemonList.toMutableList()
        newList.add(
            Pokemon(
                "https://th.bing.com/th/id/R.fd50e335da0518878dc916fe107759a1?rik=237fShUHn4NuGw&pid=ImgRaw&r=0",
                "alakazan2"
            )
        )
        pokemonList = newList.toMutableList()
        adapter.submitList(newList)
    }

    private fun pokemonClickListener() = object : PokemonListAdapter.PokemonListListener {
        override fun onClickListener(position: Int) {
            val newList = pokemonList.toMutableList()
            newList.removeAt(position)
            pokemonList = newList.toMutableList()
            adapter.submitList(newList)
        }
    }
}