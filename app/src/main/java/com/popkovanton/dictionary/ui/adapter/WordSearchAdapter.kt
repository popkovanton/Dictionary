package com.popkovanton.dictionary.ui.adapter

import com.popkovanton.dictionary.data.model.Word
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.popkovanton.dictionary.R
import com.popkovanton.dictionary.utils.DiffUtilCallback
import kotlinx.android.synthetic.main.item_word_list.view.*

class WordSearchAdapter(private val listener: (Word) -> Unit) :
    ListAdapter<Word, WordSearchAdapter.ItemHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordSearchAdapter.ItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemHolder(inflater.inflate(R.layout.item_word_list, parent, false))
    }

    override fun onBindViewHolder(holder: WordSearchAdapter.ItemHolder, position: Int) {
        holder.bind(currentList[position], listener)
    }

    fun updateData(data: List<Word>) {
        submitList(data)
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(word: Word, listener: (Word) -> Unit) {
            itemView.apply {
                rootView.setOnClickListener { listener.invoke(word) }
                tv_word_name.text = word.text
                tv_word_translation.text = word.meanings.getOrNull(0)?.translation?.text ?: ""
            }
        }
    }
}