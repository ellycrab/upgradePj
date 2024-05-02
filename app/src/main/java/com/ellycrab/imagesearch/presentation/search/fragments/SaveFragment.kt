package com.ellycrab.imagesearch.presentation.search.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ellycrab.imagesearch.R
import com.ellycrab.imagesearch.databinding.FragmentSaveBinding
import com.ellycrab.imagesearch.presentation.adapter.BookmarkAdapter
import com.ellycrab.imagesearch.presentation.search.SharedViewModel


class SaveFragment : Fragment() {

    private val sharedViewModel:SharedViewModel by activityViewModels()
    private lateinit var bookmarkAdapter: BookmarkAdapter
    private val binding by lazy { FragmentSaveBinding.inflate(layoutInflater) }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupRecyclerView()
        observeBookmarkedItems()
    }


    private fun observeBookmarkedItems() {
        sharedViewModel.bookmarkedItems.observe(viewLifecycleOwner) { bookmarkedItems ->
            bookmarkAdapter.submitList(bookmarkedItems)
        }
    }

    private fun setupRecyclerView() {



        bookmarkAdapter = BookmarkAdapter()
        binding.bookmarkRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = bookmarkAdapter
        }


    }
}