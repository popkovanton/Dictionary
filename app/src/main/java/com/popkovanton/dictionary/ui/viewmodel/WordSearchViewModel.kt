package com.popkovanton.dictionary.ui.viewmodel

import com.popkovanton.dictionary.data.model.Word
import androidx.lifecycle.*
import com.popkovanton.dictionary.data.repository.WordRepository
import com.popkovanton.dictionary.utils.ResultWrapper
import kotlinx.coroutines.launch

class WordSearchViewModel(private val mainRepository: WordRepository) : ViewModel() {

    private val mutableLiveData = MutableLiveData<ResultWrapper<List<Word>>>()
    fun getLiveData(): LiveData<ResultWrapper<List<Word>>> = mutableLiveData

    fun loadWordsList(query: String) {
        mutableLiveData.value = ResultWrapper.Loading(data = null)
        viewModelScope.launch {
            when (val response = mainRepository.searchWords(query)) {
                is ResultWrapper.Success -> mutableLiveData.postValue(
                    ResultWrapper.Success(response.data))
                is ResultWrapper.Error -> mutableLiveData.postValue(
                    ResultWrapper.Error(response.exception))
            }
        }
    }
}