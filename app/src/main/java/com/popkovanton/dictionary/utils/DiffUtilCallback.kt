package com.popkovanton.dictionary.utils

import Word
import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback : DiffUtil.ItemCallback<Word>() {
    override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem.text == newItem.text
    }
}