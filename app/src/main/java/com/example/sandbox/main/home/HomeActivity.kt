package com.example.sandbox.main.home

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.sandbox.R
import com.example.sandbox.databinding.ActivityItemsBinding
import com.example.sandbox.main.platform.BaseAppcompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseAppcompatActivity() {

    private val homeViewModel by viewModel<HomeViewModel>()

    private lateinit var binding: ActivityItemsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_items)
        binding.viewmodel = homeViewModel
        binding.lifecycleOwner = this@HomeActivity
        lifecycle.addObserver(homeViewModel)

        initActions()
    }

    private fun initActions() {

    }
}
