package com.example.gamelistxml.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gamelistcomp.data.Game
import com.example.gamelistxml.databinding.ItemCarouselPageBinding

class CarouselAdapter(
    private val games: List<Game>,
    private val onDetailClick: (Int) -> Unit
) : RecyclerView.Adapter<CarouselAdapter.CarouselPageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselPageViewHolder {
        return CarouselPageViewHolder(
            ItemCarouselPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = games.size

    override fun onBindViewHolder(holder: CarouselPageViewHolder, position: Int) {
        holder.bind(games[position])
    }

    inner class CarouselPageViewHolder(
        private val binding: ItemCarouselPageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(game: Game) {
            binding.imageCarousel.setImageResource(game.detailImageRes)
            binding.root.setOnClickListener { onDetailClick(game.id) }
        }
    }
}