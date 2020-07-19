package com.popkovanton.dictionary.ui.adapter

import Word
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.popkovanton.dictionary.R
import com.popkovanton.dictionary.utils.DiffUtilCallback
import kotlinx.android.synthetic.main.item_word_list.view.*

class WordListAdapter : ListAdapter<Word, WordListAdapter.ItemHolder>(DiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordListAdapter.ItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemHolder(inflater.inflate(R.layout.item_word_list, parent, false))
    }

    override fun onBindViewHolder(holder: WordListAdapter.ItemHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(word: Word) {
            itemView.apply {
                tv_word_name.text = word.text
                tv_word_translation.text = word.meanings[0].translation.text
            }
        }
    }
}