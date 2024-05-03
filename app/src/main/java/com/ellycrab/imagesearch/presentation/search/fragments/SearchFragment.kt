package com.ellycrab.imagesearch.presentation.search.fragments

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ellycrab.imagesearch.R
import com.ellycrab.imagesearch.databinding.FragmentSearchBinding
import com.ellycrab.imagesearch.presentation.adapter.ImgAdapter
import com.ellycrab.imagesearch.presentation.search.SearchImgViewModel
import com.ellycrab.imagesearch.presentation.search.SearchImgViewModelFactory
import com.ellycrab.imagesearch.presentation.search.SharedViewModel
import com.google.android.material.internal.ViewUtils.hideKeyboard


private const val PREFS_FILENAME = "com.ellycrab.imagesearch.last_search"
private const val LAST_SEARCH_KEY = "last_search"


class SearchFragment : Fragment(),ImgAdapter.OnSwitchStateChangeListener {

    private lateinit var sharedPreferences: SharedPreferences


    private val sharedViewModel:SharedViewModel by activityViewModels()

    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }

    private lateinit var imageResultAdapter: ImgAdapter

    private val viewModel: SearchImgViewModel by viewModels{
        SearchImgViewModelFactory()
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.searchResults.observe(viewLifecycleOwner, Observer {
            searchResults->
            imageResultAdapter.submitList(searchResults)
        })

        //sharedPreferences초기화
        sharedPreferences = requireContext().getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)


        //마지막 검색어 인풋필드에 세팅
        val lastSearchTerm = sharedPreferences.getString(LAST_SEARCH_KEY, "")
        binding.imgSearch.setText(lastSearchTerm)

        binding.recyclerImg.apply {
            layoutManager = GridLayoutManager(context, 2)

            imageResultAdapter = ImgAdapter(this@SearchFragment)
            adapter = imageResultAdapter
        }



        viewModel.imageSearchResponse.observe(viewLifecycleOwner) { imageList ->

            //item총 갯수 업데이트
            val totalCount = imageList?.size ?:0
            binding.itemCountTextView.text = "Total Items:$totalCount"

            //80개까지 제한
            val limitedList = imageList?.take(80)
            imageResultAdapter.submitList(limitedList)
        }

        binding.imgSrcBtn.setOnClickListener {
            performImageSearch()

            //키보드 자판 숨기기
            hideKeyboard()

        }
    }

    private fun performImageSearch() {
        val searchQuery = binding.imgSearch.text.toString()
        viewModel.onSearch(searchQuery)
    }


    private fun hideKeyboard(){
        val inputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }


    //스위치가 켜졌을 때 해당 항목을 북마크에 추가하거나 스위치가 꺼졌을때 해당 항목을 북마크에서 제거
    override fun onSwitchStateChanged(position: Int, isChecked: Boolean) {

        val item = imageResultAdapter.currentList[position]
        val bookmarkedItems = sharedViewModel.bookmarkedItems.value ?: emptyList()


        //아이템이 이미 북마크됬는지 체크
        val isBookmarked = bookmarkedItems.contains(item)

        if(isBookmarked){//이미 찜되어있으면
            //북마크에서 제거
            val updatedBookmarkedItems = bookmarkedItems - item
            sharedViewModel.bookmarkedItems.value = updatedBookmarkedItems
        }else{//찜되지 않았으면
            //북마크에 추가
            val updatedBookmarkedItems = bookmarkedItems + item
            sharedViewModel.bookmarkedItems.value = updatedBookmarkedItems
        }
    }


}