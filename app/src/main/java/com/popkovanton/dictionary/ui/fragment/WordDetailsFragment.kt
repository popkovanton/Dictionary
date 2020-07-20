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
import com.popkovanton.dictionary.data.model.DetailsFinal
import com.popkovanton.dictionary.data.network.ApiFactory
import com.popkovanton.dictionary.extentions.toDetailsFinal
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
                    result.data.getOrNull(0)?.toDetailsFinal(args.word)?.let { updateUI(it) }
                }
            }
        })
        wordDetailsViewModel.loadDetails(args.word.meanings.getOrNull(0)?.id.toString())
    }

    private fun updateUI(detailsFinal: DetailsFinal) {
        detailsFinal.apply {
            tv_word_name.text = text
            tv_word_translation.text = translation
            tv_transcription.text = transcription
            tv_definition.text = definition
            setImage(imageUrl)
        }
    }

    private fun setImage(imageUrl: String?) {
        Glide.with(iv_header_background)
            .load(imageUrl)
            .into(iv_header_background)
    }
}