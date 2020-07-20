package com.popkovanton.dictionary.ui.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.popkovanton.dictionary.R
import com.popkovanton.dictionary.data.network.ApiFactory
import com.popkovanton.dictionary.ui.viewmodel.ViewModelFactory
import com.popkovanton.dictionary.ui.viewmodel.WordDetailsViewModel
import com.popkovanton.dictionary.utils.ResultWrapper
import kotlinx.android.synthetic.main.fragment_word_details.*

class WordDetailsFragment : Fragment(R.layout.fragment_word_details) {
    private val args: WordDetailsFragmentArgs by navArgs()
    private lateinit var wordDetailsViewModel: WordDetailsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initObservers()
    }

    private fun initViewModel() {
        wordDetailsViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiFactory.getClient())
        ).get(WordDetailsViewModel::class.java)
    }

    private fun initObservers() {
        wordDetailsViewModel.getLiveData().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    tv_word_name.text = result.data[0].text
                    tv_word_translation.text = result.data[0].translation.text
                    tv_transcription.text = result.data[0].transcription
                    tv_definition.text = result.data[0].definition.text
                }
            }
        })
        wordDetailsViewModel.fetchData(args.word.meanings[0].id.toString())
        setImage(args.word.meanings[0].imageUrl)
    }

    private fun setImage(imageUrl: String?){
        Log.d("setImage", "imageUrl: $imageUrl")
        Glide.with(iv_header_background)
            .load("https:$imageUrl")
            .into(iv_header_background)
    }
}