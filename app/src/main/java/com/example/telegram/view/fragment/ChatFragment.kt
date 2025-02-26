package com.example.telegram.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.telegram.databinding.FragmentChatBinding
import com.example.telegram.models.model.ChatModel
import com.example.telegram.view.adapters.ChatAdapter
import kotlinx.coroutines.launch


class ChatFragment : Fragment() {
    private val viewModel: ChatViewModel by viewModels()
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val adapter = ChatAdapter(
        click = { messange -> showMessage(messange) },
        longClick = { messange -> longClickMessage(messange) }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setupListeners()
    }

    private fun init() {
        binding.recyclerView.adapter = adapter
        viewModel.getChat(777)
        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            adapter.submitList(messages)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.observe(viewLifecycleOwner) { event ->
                    when (event) {
                        is ChatViewModel.UiEvent.ShowError -> {
                            showToast(event.message)
                        }

                        is ChatViewModel.UiEvent.MessageSent -> {
                            showToast(event.message)
                        }

                        is ChatViewModel.UiEvent.MessageDeleted -> {
                            showToast(event.message)
                        }

                        is ChatViewModel.UiEvent.MessageUpdated -> {
                            showToast(event.message)
                        }
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setupListeners() {

    }

    private fun showMessage(messange: ChatModel) {
        val builder = AlertDialog.Builder(requireContext())
        val editText = EditText(requireContext())
        editText.setText(messange.message)
        builder.setView(editText)
        builder.setPositiveButton("Сохранить") { _, _ ->
            val text = editText.text.toString().trim()
//            text.let { viewModel.updateMessage(messange.id, text) } }
            builder.setNegativeButton("Отмена") { dialog, _ ->
                dialog.cancel()
                dialog.dismiss()
            }
            builder.create().show()
        }
    }


    private fun longClickMessage(messange: ChatModel) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Удалить сообщение?")
        builder.setMessage("Вы уверены, что хотите удалить это сообщение?")
        builder.setPositiveButton("Да") { _, _ ->
//            viewModel.deleteMessage(messange.id)
        }
        builder.setNegativeButton("Нет") { dialog, _ ->
            dialog.cancel()
        }
    }
}
