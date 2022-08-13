package com.example.sandbox.main.image

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.example.sandbox.databinding.FragmentImageBinding
import com.example.sandbox.main.image.adapter.ImageAdapter
import com.example.sandbox.main.image.binding.initImageAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ImageFragment : Fragment() {

    private val imageViewModel by viewModel<ImageViewModel>()

    private lateinit var imageAdapter: ImageAdapter

    private var _binding: FragmentImageBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(imageViewModel)
        imageAdapter = ImageAdapter(imageViewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageBinding.inflate(inflater, container, false)
        binding.viewmodel = imageViewModel
        binding.lifecycleOwner = this@ImageFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageListContainer.initImageAdapter(imageAdapter)
        initActions()
    }

    private fun initActions() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                imageViewModel.imagePager.collectLatest {
                    imageAdapter.submitData(it)
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                (imageAdapter.loadStateFlow as Flow).collect {
                    binding.imageProgress.isVisible =
                        it.source.append is LoadState.Loading || it.source.prepend is LoadState.Loading
                }
            }
        }

        fun setupFullImage(show: Boolean) {
            val orientation = resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                binding.fullImage!!.isVisible = show
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                imageViewModel.uiState.collect {
                    setupFullImage(it is ImageViewModel.UiState.ShowImage)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
