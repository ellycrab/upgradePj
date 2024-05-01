package com.ellycrab.imagesearch.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ellycrab.imagesearch.databinding.BookmarkItemLayoutBinding
import com.ellycrab.imagesearch.presentation.callback.BookmarkDiffCallback
import com.ellycrab.imagesearch.presentation.search.model.ImageDocumentEntity

class BookmarkAdapter : ListAdapter<ImageDocumentEntity, BookmarkAdapter.BookmarkViewHolder>(
    BookmarkDiffCallback()
) {

    inner class BookmarkViewHolder(private val binding: BookmarkItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ImageDocumentEntity) {

            Glide.with(itemView.context)
                .load(item.thumbnailUrl)
                .into(binding.imageViewThumbnail)


            binding.displaySitename.text = item.displaySitename
            binding.datetime.text = item.datetime.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val binding = BookmarkItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}