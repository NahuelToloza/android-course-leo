package com.example.pokemonclasses.presentation.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

    private var pokemonList: MutableList<Pokemon> = mutableListOf()

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
        setupListeners()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        pokemonList = mutableListOf<Pokemon>(
            Pokemon(
                "https://th.bing.com/th/id/R.eda5531538101e2e5e3babbd75f811eb?rik=m857wQaMnapABw&riu=http%3a%2f%2forig08.deviantart.net%2f4dfb%2ff%2f2014%2f090%2f6%2f1%2fpikachu___01_by_mighty355-d7cdjy7.png&ehk=%2byUUP8C5NCRSYi0hJ3Lq2HpzY3uaqw2AKbH0JyCGFik%3d&risl=&pid=ImgRaw&r=0",
                "pikachu"
            ),
            Pokemon(
                "https://th.bing.com/th/id/R.eda5531538101e2e5e3babbd75f811eb?rik=m857wQaMnapABw&riu=http%3a%2f%2forig08.deviantart.net%2f4dfb%2ff%2f2014%2f090%2f6%2f1%2fpikachu___01_by_mighty355-d7cdjy7.png&ehk=%2byUUP8C5NCRSYi0hJ3Lq2HpzY3uaqw2AKbH0JyCGFik%3d&risl=&pid=ImgRaw&r=0",
                "pikachu2"
            ),
            Pokemon(
                "https://th.bing.com/th/id/OIP.0nH0o75e59PYY_esKYK5OgHaG1?rs=1&pid=ImgDetMain",
                "bulbasur"
            ),
            Pokemon(
                "https://th.bing.com/th/id/OIP.0nH0o75e59PYY_esKYK5OgHaG1?rs=1&pid=ImgDetMain",
                "bulbasur2"
            ),
            Pokemon(
                "https://th.bing.com/th/id/R.ac98fd4ec9918a1f0fa6e3b19a62f431?rik=1osLZXcBAs1nWg&pid=ImgRaw&r=0",
                "charmander"
            ),
            Pokemon(
                "https://th.bing.com/th/id/R.ac98fd4ec9918a1f0fa6e3b19a62f431?rik=1osLZXcBAs1nWg&pid=ImgRaw&r=0",
                "charmander2"
            ),
            Pokemon(
                "https://th.bing.com/th/id/R.fd50e335da0518878dc916fe107759a1?rik=237fShUHn4NuGw&pid=ImgRaw&r=0",
                "alakazan"
            ),
            Pokemon(
                "https://th.bing.com/th/id/R.fd50e335da0518878dc916fe107759a1?rik=237fShUHn4NuGw&pid=ImgRaw&r=0",
                "alakazan2"
            ),
        )

        val adapter = PokemonListAdapter(pokemonList)

        //Click to remove
        /*adapter.setupListener(object : PokemonListAdapter.PokemonListListener {
            override fun onClickListener(position: Int) {
                super.onClickListener(position)
                pokemonList.remove(pokemonList[position])
                adapter.notifyItemRemoved(position)
            }
        })*/

        //Setup recycler
        binding.rvList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getParameters() {
        val email = args.user.email
//        binding.tvTitle.text = "Hola, $email"
    }

    private fun setupListeners() {
        binding.root.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
            findNavController().navigate(action)
        }
    }
}