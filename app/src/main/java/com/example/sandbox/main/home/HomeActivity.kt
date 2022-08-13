package com.example.sandbox.main.home

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.sandbox.R
import com.example.sandbox.databinding.ActivityHomeBinding
import com.example.sandbox.main.alert.DefaultAlert
import com.example.sandbox.main.platform.BaseAppcompatActivity
import com.example.sandbox.main.platform.EventObserver
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseAppcompatActivity() {

    private val homeViewModel by viewModel<HomeViewModel>()

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycle.addObserver(homeViewModel)

        val navController: NavController = findNavController(R.id.navHostFragment)
        binding.bottomNavigation.setupWithNavController(navController)
        initActions()
    }

    private fun initActions() {
        homeViewModel.failure.observe(this, EventObserver{
            manageError(it)
        })

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

            }
        }
    }
}
