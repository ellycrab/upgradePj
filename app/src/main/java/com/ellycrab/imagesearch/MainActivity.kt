package com.ellycrab.imagesearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ellycrab.imagesearch.databinding.ActivityMainBinding
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.bottomSearch.setOnClickListener {
            findNavController(R.id.fragmentContainerView).navigate(R.id.searchFragment)
        }

        binding.bottomSave.setOnClickListener {
            findNavController(R.id.fragmentContainerView).navigate(R.id.saveFragment)
        }
    }
}