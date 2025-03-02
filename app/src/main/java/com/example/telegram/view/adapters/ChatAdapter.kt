package com.example.telegram.view.adapters

import androidx.recyclerview.widget.ListAdapter
import com.example.telegram.models.model.ChatModel
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.telegram.databinding.ItemMessageBinding

class ChatAdapter(
    private val click: (ChatModel) -> Unit,
    private val longClick: (ChatModel) -> Unit
) : ListAdapter<ChatModel, ChatAdapter.ChatViewHolder>(DifUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(
            ItemMessageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val messange = getItem(position)
        with(holder.binding) {
            txtMessange.text = messange.message

        }
        holder.itemView.setOnClickListener {
            click(messange)
        }
        holder.itemView.setOnLongClickListener {
            longClick(messange)
            true
        }
    }

    class DifUtil : DiffUtil.ItemCallback<ChatModel>() {
        override fun areItemsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean {
            return oldItem == newItem
        }
    }

    inner class ChatViewHolder(val binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root)
}
