package com.example.gamelistxml.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gamelistcomp.data.Game
import com.example.gamelistxml.R
import com.example.gamelistxml.databinding.ItemCarouselBinding
import com.example.gamelistxml.databinding.ItemGameCardBinding

class GameListAdapter(
    private val games: List<Game>,
    private var isIndonesian: Boolean = false,
    private val onDetailClick: (Int) -> Unit,
    private val onBrowserClick: (String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_CAROUSEL = 0
        private const val TYPE_GAME = 1
    }

    override fun getItemViewType(position: Int) = if (position == 0) TYPE_CAROUSEL else TYPE_GAME

    override fun getItemCount() = games.size + 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_CAROUSEL) {
            CarouselViewHolder(
                ItemCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        } else {
            GameViewHolder(
                ItemGameCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CarouselViewHolder -> holder.bind(games, onDetailClick)
            is GameViewHolder -> holder.bind(
                game = games[position - 1],
                isIndonesian = isIndonesian,
                onDetailClick = onDetailClick,
                onBrowserClick = onBrowserClick
            )
        }
    }

    fun updateLanguage(isIndonesian: Boolean) {
        this.isIndonesian = isIndonesian
        notifyDataSetChanged()
    }

    inner class CarouselViewHolder(
        private val binding: ItemCarouselBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(games: List<Game>, onDetailClick: (Int) -> Unit) {
            val carouselAdapter = CarouselAdapter(games, onDetailClick)
            binding.viewPager.adapter = carouselAdapter
        }
    }

    inner class GameViewHolder(
        private val binding: ItemGameCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            game: Game,
            isIndonesian: Boolean,
            onDetailClick: (Int) -> Unit,
            onBrowserClick: (String) -> Unit
        ) {
            val ctx = binding.root.context
            binding.textTitle.text = ctx.getString(game.title)
            binding.textRating.text = ctx.getString(game.ratings)
            val desc = if (isIndonesian) game.descriptionId else game.descriptionEn
            binding.textDescription.text = ctx.getString(desc)
            binding.imageGame.setImageResource(game.imageRes)
            if (isIndonesian) {
                binding.buttonSteam.text = ctx.getString(R.string.steam_buttonId)
                binding.buttonDetail.text = ctx.getString(R.string.detail_buttonId)
            } else {
                binding.buttonSteam.text = ctx.getString(R.string.steam_button)
                binding.buttonDetail.text = ctx.getString(R.string.detail_button)
            }
            binding.buttonSteam.setOnClickListener { onBrowserClick(game.steamLink) }
            binding.buttonDetail.setOnClickListener { onDetailClick(game.id) }
        }
    }
}