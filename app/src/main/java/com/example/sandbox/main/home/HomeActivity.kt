package com.example.sandbox.main.home

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.sandbox.R
import com.example.sandbox.databinding.ActivityHomeBinding
import com.example.sandbox.main.extension.showAsInformationSnackBar
import com.example.sandbox.main.platform.BaseAppcompatActivity
import com.example.sandbox.main.platform.EventObserver
import com.example.uibox.tools.ColorSource
import com.example.uibox.tools.StringSource
import com.example.uibox.view.InformationAlertView
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseAppcompatActivity() {

    private val homeViewModel by viewModel<HomeViewModel>()

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewmodel = homeViewModel
        binding.lifecycleOwner = this@HomeActivity
        lifecycle.addObserver(homeViewModel)

        val navController: NavController = findNavController(R.id.navHostFragment)
        binding.bottomNavigation.setupWithNavController(navController)
        initActions()
    }

    private fun initActions() {
        homeViewModel.failure.observe(
            this,
            EventObserver {
                manageError(it)
            }
        )

        fun showInformationNotification(data: InformationAlertView.Information) {
            InformationAlertView(this, data)
                .showAsInformationSnackBar(binding.root)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.uiState.collect {
                    when (it) {
                        HomeViewModel.UiState.AlreadyUpToDate -> {
                            binding.refreshButton.repeatCount = 1
                            showInformationNotification(
                                InformationAlertView.Information(
                                    text = StringSource.Res(R.string.already_up_to_data),
                                    backgroundColor = ColorSource.Res(
                                        com.example.uibox.R.color.color_orange
                                    )
                                )
                            )
                        }
                        HomeViewModel.UiState.Loading -> {
                            binding.refreshButton.repeatCount = ValueAnimator.INFINITE
                            binding.refreshButton.playAnimation()
                        }
                        HomeViewModel.UiState.Updated -> {
                            binding.refreshButton.repeatCount = 1
                            showInformationNotification(
                                InformationAlertView.Information(
                                    text = StringSource.Res(R.string.updated),
                                    backgroundColor = ColorSource.Res(
                                        com.example.uibox.R.color.color_green
                                    )
                                )
                            )
                        }
                        else -> {
                        }
                    }
                }
            }
        }
    }
}
