package com.example.pokemonclasses.presentation.ui.fragments.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.pokemonclasses.data.Pokemon
import com.example.pokemonclasses.databinding.ItemPokemonHeaderBinding
import com.example.pokemonclasses.databinding.ItemPokemonListBinding
import com.example.pokemonclasses.presentation.ui.fragments.home.PokemonListAdapter.Companion.HEADER_TYPE

class PokemonListAdapter :
    ListAdapter<PokemonItem, PokemonListAdapter.PokemonParentViewHolder>(diffCallbackPokemon) {
    private var listener: PokemonListListener? = null

    override fun getItemViewType(position: Int): Int {
        return currentList[position].getViewType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonParentViewHolder {
        when (viewType) {
            POKEMON_TYPE -> {
                val binding =
                    ItemPokemonListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return PokemonItemViewHolder(binding)
            }

            HEADER_TYPE -> {
                val binding =
                    ItemPokemonHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return HeaderPokemonItemViewHolder(binding)
            }

            else -> {
                throw IllegalStateException("The viewType number: $viewType not exist")
            }
        }
    }

    override fun onBindViewHolder(holder: PokemonParentViewHolder, position: Int) {
        when (holder) {
            is PokemonItemViewHolder -> {
                holder.onBind(currentList[position] as Pokemon, listener)
            }

            is HeaderPokemonItemViewHolder -> {
                holder.onBind(currentList[position] as HeaderItem)
            }
        }
    }

    fun setupListener(listener: PokemonListListener) {
        this.listener = listener
    }

    abstract class PokemonParentViewHolder(itemView: View) : ViewHolder(itemView)

    class PokemonItemViewHolder(
        private val binding: ItemPokemonListBinding
    ) : PokemonParentViewHolder(binding.root) {
        fun onBind(pokemon: Pokemon, listener: PokemonListListener?) {
            binding.root.setOnClickListener {
                listener?.onClickListener(adapterPosition)
            }
            Glide.with(binding.root.context).load(pokemon.image).into(binding.ivPokemon)
            binding.tvPokemonName.text = pokemon.name
        }
    }

    class HeaderPokemonItemViewHolder(
        private val binding: ItemPokemonHeaderBinding
    ) : PokemonParentViewHolder(binding.root) {
        fun onBind(item: HeaderItem) {
            binding.tvTitle.text = item.title
            val color = binding.root.context.getColor(item.backgroundColor)
            binding.tvTitle.setBackgroundColor(color)
        }
    }

    interface PokemonListListener {
        fun onClickListener(position: Int)
    }

    companion object {
        const val POKEMON_TYPE = 0
        const val HEADER_TYPE = 1
    }
}

data class HeaderItem(
    val title: String,
    @ColorRes val backgroundColor: Int,
) : PokemonItem {
    override fun getViewType() = HEADER_TYPE
    override fun getId() = title
    override fun getContent() = title + backgroundColor
}

interface PokemonItem {
    fun getViewType(): Int
    fun getId(): String
    fun getContent(): String
}

private val diffCallbackPokemon = object : DiffUtil.ItemCallback<PokemonItem>() {
    override fun areItemsTheSame(oldItem: PokemonItem, newItem: PokemonItem): Boolean {
        return (oldItem.getId() == newItem.getId())
    }

    override fun areContentsTheSame(oldItem: PokemonItem, newItem: PokemonItem): Boolean {
        return (oldItem.getContent() == newItem.getContent())
    }
}

