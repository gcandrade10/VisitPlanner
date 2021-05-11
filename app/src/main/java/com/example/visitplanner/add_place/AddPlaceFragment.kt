package com.example.visitplanner.add_place

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.example.visitplanner.databinding.FragmentAddPlaceBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddPlaceFragment : Fragment() {

    private var _binding: FragmentAddPlaceBinding? = null
    private val binding get() = _binding!!
    private val args: AddPlaceFragmentArgs by navArgs()
    private val placeViewModel: AddPlaceViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addPlaceToolbar.setNavigationIconTint(Color.WHITE)
        NavigationUI.setupWithNavController(binding.addPlaceToolbar, findNavController())
        placeViewModel.completeLiveData.observe(viewLifecycleOwner, {
            findNavController().navigateUp()
        })
        with(args.place) {
            binding.nameText.setText(this.label)
        }
        binding.addPlaceSave.setOnClickListener {
            placeViewModel.save(
                args.place.copy(
                    label = binding.nameText.text.toString(),
                    description = binding.descriptionText.text.toString()
                )
            )
        }

    }

}