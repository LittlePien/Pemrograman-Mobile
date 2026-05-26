package com.example.recyclerviewproject.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewproject.databinding.ItemCardBinding
import com.example.recyclerviewproject.model.ItemData

class ItemCardAdapter(
    private var items: List<ItemData> = emptyList(),
    private val onItemClicked: (ItemData) -> Unit,
    private val onSwitchToggled: (ItemData, Boolean) -> Unit,
    private val onButtonClicked: (ItemData) -> Unit
) : RecyclerView.Adapter<ItemCardAdapter.ItemCardViewHolder>() {

    fun submitList(newItems: List<ItemData>) {
        items = newItems
        notifyDataSetChanged()
    }

    class ItemCardViewHolder(
        val binding: ItemCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: ItemData,
            onItemClicked: (ItemData) -> Unit,
            onSwitchToggled: (ItemData, Boolean) -> Unit,
            onButtonClicked: (ItemData) -> Unit
        ) {
            binding.apply {
                ivItem.setImageResource(item.image)
                tvTitle.text = root.context.getString(item.title)
                tvDesc.text = root.context.getString(item.description)

                swItem.setOnCheckedChangeListener(null)
                swItem.isChecked = item.isSwitchOn
                swItem.setOnCheckedChangeListener { _, isChecked ->
                    onSwitchToggled(item, isChecked)
                }

                btnItem.setOnClickListener { onButtonClicked(item) }

                root.setOnClickListener { onItemClicked(item) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCardViewHolder {
        val binding = ItemCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemCardViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, onItemClicked, onSwitchToggled, onButtonClicked)
        if (position % 2 != 0) {
            holder.binding.root.setCardBackgroundColor("#97e89b".toColorInt())
        } else {
            holder.binding.root.setCardBackgroundColor("#FFFFFF".toColorInt())
        }
    }

    override fun getItemCount(): Int = items.size
}