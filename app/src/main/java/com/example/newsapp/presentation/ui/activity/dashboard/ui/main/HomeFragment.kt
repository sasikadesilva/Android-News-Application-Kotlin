package com.example.newsapp.presentation.ui.activity.dashboard.ui.main

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.presentation.ui.activity.dashboard.ui.main.adapter.HorizontalButtonWrapperAdapter
import com.example.newsapp.presentation.ui.activity.dashboard.ui.main.adapter.LatestArticleWrapperAdapter
import com.example.newsapp.presentation.ui.activity.dashboard.ui.main.adapter.NewsFeedAdapter
import com.example.newsapp.presentation.viewmodel.ArticleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A placeholder fragment containing a simple view.
 */

@AndroidEntryPoint
class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null

    private val articleViewModel: ArticleViewModel by viewModels()

    private lateinit var mainRecyclerView: RecyclerView

    private lateinit var searchView: SearchView

    private lateinit var latestNewsAdapter: LatestArticleWrapperAdapter
    private lateinit var categoryAdapter: HorizontalButtonWrapperAdapter
    private lateinit var articleAdapter: NewsFeedAdapter
    private lateinit var concatAdapter: ConcatAdapter
    private val buttonList = listOf("Health", "Technology", "Finance", "Art", "Country", "Category")
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root = binding.root

        mainRecyclerView = root.findViewById(R.id.mainRecyclerView)
        latestNewsAdapter = LatestArticleWrapperAdapter(emptyList())
        articleViewModel.getAllNewsWithCategory(buttonList[0])
        categoryAdapter = HorizontalButtonWrapperAdapter(buttonList) { position ->
            articleViewModel.getAllNewsWithCategory(buttonList[position])

        }
        articleAdapter = NewsFeedAdapter(emptyList())

        concatAdapter = ConcatAdapter(
            latestNewsAdapter,
            categoryAdapter,
            articleAdapter
        )


        mainRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        mainRecyclerView.adapter = concatAdapter
        mainRecyclerView.setHasFixedSize(true)

        //latest news panel
        articleViewModel.loadLatestArticles()
        lifecycleScope.launch {
            articleViewModel.latestNews.collect { news ->
                latestNewsAdapter.updateData(news.take(5))
            }
        }

        //news feed
        lifecycleScope.launch {
            articleViewModel.newsFeed.collect { news ->
                articleAdapter.updateData(news)
            }
        }
        searchView = root.findViewById(R.id.searchView)
        setSearchBlock()

        return root
    }

    private fun setSearchBlock() {

        val searchEditText = searchView.findViewById<EditText>(
            androidx.appcompat.R.id.search_src_text
        )
        searchEditText.setTextColor(Color.BLACK)
        searchEditText.setHintTextColor(Color.GRAY)
        searchEditText.textSize = 16f
        searchEditText.addTextChangedListener(object : android.text.TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
                if (p0.toString().isNotEmpty()) {
                    concatAdapter.removeAdapter(latestNewsAdapter)
                    concatAdapter.removeAdapter(categoryAdapter)
                } else {

                    concatAdapter.addAdapter(0, latestNewsAdapter)
                    concatAdapter.addAdapter(1, categoryAdapter)
                    concatAdapter.notifyDataSetChanged()
                    mainRecyclerView.scrollToPosition(0)
                    articleViewModel.getAllNewsWithCategory(buttonList[0])

                }
            }
        })

        searchEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = searchView.query.toString()

                articleViewModel.onSearchNews(query)

                val imm =
                    v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)

                true
            } else {
                false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}