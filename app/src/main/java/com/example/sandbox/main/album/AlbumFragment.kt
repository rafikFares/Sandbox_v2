package com.example.sandbox.main.album

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
import com.example.sandbox.core.data.ImageItem
import com.example.sandbox.databinding.FragmentAlbumBinding
import com.example.sandbox.main.album.adapter.AlbumAdapter
import com.example.sandbox.main.album.binding.initAlbumAdapter
import com.example.uibox.databinding.ViewAlbumDetailBinding
import com.example.uibox.tools.animateClick
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso
import eu.okatrych.rightsheet.RightSheetBehavior
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumFragment : Fragment() {
    private val albumViewModel by viewModel<AlbumViewModel>()

    private lateinit var albumAdapter: AlbumAdapter
    private var bottomSheetBehavior: BottomSheetBehavior<MaterialCardView>? = null
    private var rightSheetBehavior: RightSheetBehavior<MaterialCardView>? = null

    private var _binding: FragmentAlbumBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        albumAdapter = AlbumAdapter(albumViewModel)
        lifecycle.addObserver(albumViewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumBinding.inflate(inflater, container, false)
        binding.viewmodel = albumViewModel
        binding.lifecycleOwner = this@AlbumFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val orientation = resources.configuration.orientation
        initCommonViews()
        initActions()

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.albumListContainer.initAlbumAdapter(albumAdapter, true)
            bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetContainer!!)
            initPortraitViews()
        } else {
            binding.albumListContainer.initAlbumAdapter(albumAdapter, false)
            rightSheetBehavior = RightSheetBehavior.from(binding.rightSheetContainer!!)
            initLandViews()
        }
    }

    private fun initCommonViews() {
        binding.containerLayout.setOnClickListener {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
            rightSheetBehavior?.state = RightSheetBehavior.STATE_HIDDEN
        }
        binding.back.setOnClickListener {
            it.animateClick {
                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
                rightSheetBehavior?.state = RightSheetBehavior.STATE_HIDDEN
            }
        }
    }

    private fun initLandViews() {
        rightSheetBehavior!!.addRightSheetCallback(object : RightSheetBehavior.RightSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    RightSheetBehavior.STATE_HIDDEN -> {
                        binding.containerLayout.isVisible = false
                    }
                    else -> {
                        // nothing
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // nothing
            }
        })
    }

    private fun initPortraitViews() {
        bottomSheetBehavior!!.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.containerLayout.isVisible = false
                    }
                    else -> {
                        // nothing
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // nothing
            }
        })
    }

    private fun initActions() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                albumViewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is AlbumViewModel.UiState.AlbumClick -> {
                            setupAlbumDetails(uiState.items)
                        }
                        else -> {
                            // do nothing
                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                albumViewModel.albumPager.collectLatest {
                    albumAdapter.submitData(it)
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                (albumAdapter.loadStateFlow as Flow).collect {
                    binding.albumProgress.isVisible =
                        it.source.append is LoadState.Loading || it.source.prepend is LoadState.Loading
                }
            }
        }
    }

    private fun setupAlbumDetails(items: List<ImageItem>) {
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.containerLayout.isVisible = true
            bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
            binding.albumImagesContainer.removeAllViews()
            items.forEach {
                val child = createAlbumDetail(it)
                binding.albumImagesContainer.addView(child)
            }
        } else {
            binding.containerLayout.isVisible = true
            rightSheetBehavior!!.state = RightSheetBehavior.STATE_COLLAPSED
            binding.albumImagesContainer.removeAllViews()
            items.forEach {
                val child = createAlbumDetail(it)
                binding.albumImagesContainer.addView(child)
            }
        }
    }

    private fun createAlbumDetail(item: ImageItem): View {
        val view = LayoutInflater.from(requireContext()).inflate(com.example.uibox.R.layout.view_album_detail, null)
        val childBinding = ViewAlbumDetailBinding.bind(view)
        childBinding.apply {
            Picasso.get().load(item.url).into(detailIcon)
            detailId.text = "${item.id}"
            detailTitle.text = item.title
        }
        return childBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
