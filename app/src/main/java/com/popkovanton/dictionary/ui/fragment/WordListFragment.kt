package com.popkovanton.dictionary.ui.fragment

import Word
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.popkovanton.dictionary.R
import com.popkovanton.dictionary.data.network.ApiFactory
import com.popkovanton.dictionary.ui.adapter.WordListAdapter
import com.popkovanton.dictionary.ui.viewmodel.WordListViewModel
import com.popkovanton.dictionary.ui.viewmodel.ViewModelFactory
import com.popkovanton.dictionary.utils.ResultWrapper
import kotlinx.android.synthetic.main.fragment_word_list.*

class WordListFragment : Fragment(R.layout.fragment_word_list), SearchView.OnQueryTextListener {
    private lateinit var viewModel: WordListViewModel
    private val wordAdapter = WordListAdapter()
    private var currentQuery: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
        initUI()
        initObservers()
        initListeners()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiFactory.getClient())
        ).get(WordListViewModel::class.java)
    }

    private fun initUI() {
        rvWordsList.layoutManager = LinearLayoutManager(context)
        rvWordsList.adapter = wordAdapter
        rvWordsList.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        svWords.setOnQueryTextListener(this)
    }

    private fun initObservers() {
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    rvWordsList.visibility = View.VISIBLE
                    progress_bar.visibility = View.GONE
                    tvNotFound.visibility = View.GONE
                    retrieveList(result.data)
                }
                is ResultWrapper.Loading -> {
                    progress_bar.visibility = View.VISIBLE
                    tvNotFound.visibility = View.GONE
                }
                is ResultWrapper.Error -> {
                    rvWordsList.visibility = View.GONE
                    progress_bar.visibility = View.GONE
                    tvNotFound.setText(R.string.error_occurred)
                    tvNotFound.visibility = View.VISIBLE
                    swRefreshWords.isRefreshing = false
                }
            }
        })
    }

    private fun initListeners() {
        swRefreshWords.setOnRefreshListener {
            if (currentQuery.isNullOrBlank()) {
                swRefreshWords.isRefreshing = false
            } else {
                search(currentQuery!!)
            }
        }
    }

    private fun search(query: String) {
        viewModel.fetchData(query)
    }

    private fun retrieveList(users: List<Word>) {
        wordAdapter.apply {
            swRefreshWords.isRefreshing = false
            wordAdapter.submitList(users)
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