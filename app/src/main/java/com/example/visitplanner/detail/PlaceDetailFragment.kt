package com.example.visitplanner.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.example.visitplanner.R
import com.example.visitplanner.databinding.FragmentPlaceDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlaceDetailFragment : Fragment() {

    private var _binding: FragmentPlaceDetailBinding? = null
    private val binding get() = _binding!!
    private val args: PlaceDetailFragmentArgs by navArgs()
    private val placeDetailViewModel: PlaceDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaceDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.placeItemToolbar.setNavigationIconTint(Color.WHITE)
        NavigationUI.setupWithNavController(binding.placeItemToolbar, findNavController())
        with(args.place) {
            binding.placeItemDescription.text = description
            binding.placeItemToolbar.title = label
            binding.placeItemVisited.isChecked = isVisited
            binding.placeItemMap.onCreate(savedInstanceState)
            binding.placeItemMap.getMapAsync { map ->
                val cameraUpdate =
                    CameraUpdateFactory.newLatLngZoom(LatLng(latLong.first, latLong.second), 10f)
                map.animateCamera(cameraUpdate)
                binding.loadingIndicator.isVisible = false
            }
            binding.placeItemVisited.setOnCheckedChangeListener { _, isChecked ->
                placeDetailViewModel.markAsVisited(args.place.id, isChecked)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.home) {
            findNavController().navigateUp()
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        binding.placeItemMap.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.placeItemMap.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.placeItemMap.onLowMemory()
    }

}