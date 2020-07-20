package com.popkovanton.dictionary.ui.viewmodel

import androidx.lifecycle.*
import com.popkovanton.dictionary.data.model.Meaning
import com.popkovanton.dictionary.data.model.MeaningDetails
import com.popkovanton.dictionary.data.repository.WordRepository
import com.popkovanton.dictionary.utils.ResultWrapper
import kotlinx.coroutines.launch

class WordDetailsViewModel(private val mainRepository: WordRepository) : ViewModel() {

    private val mutableLiveData = MutableLiveData<ResultWrapper<List<MeaningDetails>>>()
    fun getLiveData(): LiveData<ResultWrapper<List<MeaningDetails>>> = mutableLiveData

    fun loadDetails(query: String) {
        mutableLiveData.value = ResultWrapper.Loading(data = null)
        viewModelScope.launch {
            when (val response = mainRepository.searchMeaning(query)) {
                is ResultWrapper.Success -> mutableLiveData.postValue(
                    ResultWrapper.Success(response.data))
                is ResultWrapper.Error -> mutableLiveData.postValue(
                    ResultWrapper.Error(response.exception))
            }
        }
    }
}