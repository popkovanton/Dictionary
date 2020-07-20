package com.popkovanton.dictionary.ui.fragment

import com.popkovanton.dictionary.data.model.Word
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.popkovanton.dictionary.R
import com.popkovanton.dictionary.data.network.ApiFactory
import com.popkovanton.dictionary.extentions.hide
import com.popkovanton.dictionary.extentions.show
import com.popkovanton.dictionary.ui.adapter.WordSearchAdapter
import com.popkovanton.dictionary.ui.viewmodel.WordSearchViewModel
import com.popkovanton.dictionary.ui.viewmodel.ViewModelFactory
import com.popkovanton.dictionary.utils.ResultWrapper
import kotlinx.android.synthetic.main.fragment_word_list.*

class WordSearchFragment : Fragment(R.layout.fragment_word_list) {
    private lateinit var viewModel: WordSearchViewModel
    private lateinit var wordAdapter: WordSearchAdapter
    private var currentQuery: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initUI()
        initObservers()
        initListeners()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiFactory.getClient())
        ).get(WordSearchViewModel::class.java)
    }

    private fun initUI() {
        wordAdapter = WordSearchAdapter {
            openDetailFragment(it)
        }
        rv_words_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = wordAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
        sv_word_search.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextChange(newText: String): Boolean {
                    currentQuery = newText
                    search(newText)
                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    currentQuery = query
                    search(query)
                    return true
                }

            })
        }
    }

    private fun initObservers() {
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    rv_words_list.show()
                    progress_bar.hide()
                    tv_info_middle_text.hide()
                    retrieveList(result.data)
                }
                is ResultWrapper.Loading -> {
                    progress_bar.show()
                    tv_info_middle_text.hide()
                }
                is ResultWrapper.Error -> {
                    rv_words_list.hide()
                    progress_bar.hide()
                    tv_info_middle_text.setText(R.string.error_occurred)
                    tv_info_middle_text.show()
                    sw_refresh_word_list.isRefreshing = false
                }
            }
        })
    }

    private fun initListeners() {
        sw_refresh_word_list.setOnRefreshListener {
            if (currentQuery.isNullOrBlank()) {
                sw_refresh_word_list.isRefreshing = false
            } else {
                search(currentQuery!!)
            }
        }
    }

    private fun openDetailFragment(word: Word) {
        findNavController().navigate(WordSearchFragmentDirections.fromWordListToWordDetail(word))
    }

    private fun search(query: String) {
        viewModel.loadWordsList(query)
    }

    private fun retrieveList(users: List<Word>) {
        wordAdapter.apply {
            sw_refresh_word_list.isRefreshing = false
            wordAdapter.updateData(users)
        }
    }
}