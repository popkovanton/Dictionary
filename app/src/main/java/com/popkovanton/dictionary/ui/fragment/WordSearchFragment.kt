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
import com.popkovanton.dictionary.ui.adapter.WordSearchAdapter
import com.popkovanton.dictionary.ui.viewmodel.WordSearchViewModel
import com.popkovanton.dictionary.ui.viewmodel.ViewModelFactory
import com.popkovanton.dictionary.utils.ResultWrapper
import kotlinx.android.synthetic.main.fragment_word_list.*

class WordSearchFragment : Fragment(R.layout.fragment_word_list), SearchView.OnQueryTextListener {
    private lateinit var viewModel: WordSearchViewModel
    private lateinit var wordAdapter : WordSearchAdapter
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
        rv_words_list.layoutManager = LinearLayoutManager(context)
        rv_words_list.adapter = wordAdapter
        rv_words_list.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        sv_word_search.setOnQueryTextListener(this)
    }

    private fun initObservers() {
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    rv_words_list.visibility = View.VISIBLE
                    progress_bar.visibility = View.GONE
                    tv_info_middle_text.visibility = View.GONE
                    retrieveList(result.data)
                }
                is ResultWrapper.Loading -> {
                    progress_bar.visibility = View.VISIBLE
                    tv_info_middle_text.visibility = View.GONE
                }
                is ResultWrapper.Error -> {
                    rv_words_list.visibility = View.GONE
                    progress_bar.visibility = View.GONE
                    tv_info_middle_text.setText(R.string.error_occurred)
                    tv_info_middle_text.visibility = View.VISIBLE
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

    private fun openDetailFragment(word: Word){
        findNavController().navigate(WordSearchFragmentDirections.fromWordListToWordDetail(word))
    }

    private fun search(query: String) {
        viewModel.fetchData(query)
    }

    private fun retrieveList(users: List<Word>) {
        wordAdapter.apply {
            sw_refresh_word_list.isRefreshing = false
            wordAdapter.updateData(users)
        }
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        currentQuery = query
        search(query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        currentQuery = newText
        search(newText)
        return true
    }
}