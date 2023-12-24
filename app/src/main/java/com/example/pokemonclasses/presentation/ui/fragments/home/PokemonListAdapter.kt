package com.example.pokemonclasses.presentation.ui.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.pokemonclasses.data.Pokemon
import com.example.pokemonclasses.databinding.ItemPokemonListBinding

class PokemonListAdapter(
    private val pokemonList: List<Pokemon>
) : RecyclerView.Adapter<PokemonListAdapter.PokemonItemViewHolder>() {
    private var listener: PokemonListListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonItemViewHolder {
        val binding =
            ItemPokemonListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonItemViewHolder, position: Int) {
        holder.onBind(pokemonList[position], listener, position)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    class PokemonItemViewHolder(private val binding: ItemPokemonListBinding) :
        ViewHolder(binding.root) {
        fun onBind(pokemon: Pokemon, listener: PokemonListListener?, position: Int) {
            binding.root.setOnClickListener {
                listener?.onClickListener(position)
            }
            Glide.with(binding.root.context).load(pokemon.image).into(binding.ivPokemon)
            binding.tvPokemonName.text = pokemon.name
        }

    }

    fun setupListener(listener: PokemonListListener) {
        this.listener = listener
    }

    interface PokemonListListener {
        fun onClickListener(position: Int) {

        }
    }

}

