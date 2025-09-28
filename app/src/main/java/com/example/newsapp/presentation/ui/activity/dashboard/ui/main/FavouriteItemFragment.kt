package com.example.newsapp.presentation.ui.activity.dashboard.ui.main

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.presentation.ui.activity.auth.LoginActivity
import com.example.newsapp.presentation.ui.activity.dashboard.ui.main.adapter.FavouriteItemRecyclerViewAdapter
import com.example.newsapp.presentation.viewmodel.ArticleViewModel
import com.example.newsapp.presentation.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class FavouriteItemFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModels()
    private val articleViewModel: ArticleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.favourite_fragment_item_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.list)
        // Set the adapter
        val adapter = FavouriteItemRecyclerViewAdapter(emptyList(),true ) { article ->
            AlertDialog.Builder(requireContext())
                .setTitle("Confirm Action")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Yes") { dialog, _ ->
                    dialog.dismiss()
                  articleViewModel.deleteFavouriteArticle(article)

                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)


        lifecycleScope.launch {
            val userModel = userViewModel.getUserSessionInfo()
            if (userModel != null) {
                if (activity != null) {
                    articleViewModel.getFavouriteArticles(userModel.id).observe(activity) { news ->
                        adapter.updateData(news)
                    }
                }
            }
        }

        return view
    }
}