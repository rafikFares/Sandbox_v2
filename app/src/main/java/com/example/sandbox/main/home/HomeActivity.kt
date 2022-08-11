package com.example.sandbox.main.home

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.sandbox.R
import com.example.sandbox.databinding.ActivityItemsBinding
import com.example.sandbox.main.home.adapter.ImageAdapter
import com.example.sandbox.main.home.binding.initAdapter
import com.example.sandbox.main.platform.BaseAppcompatActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseAppcompatActivity() {

    private val homeViewModel by viewModel<HomeViewModel>()

    private lateinit var binding: ActivityItemsBinding

    private val imageAdapter : ImageAdapter = ImageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_items)
        binding.viewmodel = homeViewModel
        binding.lifecycleOwner = this@HomeActivity
        lifecycle.addObserver(homeViewModel)

        binding.listContainer.initAdapter(imageAdapter)

        initActions()
    }

    private fun initActions() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.items.collectLatest {
                    imageAdapter.submitData(it)
                }
            }
        }
    }
}
