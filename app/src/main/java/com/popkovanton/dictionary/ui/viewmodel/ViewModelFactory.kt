package com.popkovanton.dictionary.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.popkovanton.dictionary.data.repository.WordRepository
import com.popkovanton.dictionary.data.network.SEApi

class ViewModelFactory(private val seApi: SEApi) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordListViewModel::class.java)) {
            return WordListViewModel(WordRepository(seApi)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}