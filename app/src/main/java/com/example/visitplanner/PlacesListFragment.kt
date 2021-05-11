package com.example.visitplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.visitplanner.MainActivity.Companion.AUTOCOMPLETE_REQUEST_CODE
import com.example.visitplanner.databinding.FragmentPlacesListBinding
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.visitplanner.Place as VisitplannerPlace

class PlacesListFragment : Fragment() {

    private var _binding: FragmentPlacesListBinding? = null
    private val binding get() = _binding!!
    private val placesListViewModel: PlacesListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlacesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        placesListViewModel.placesLiveData.observe(viewLifecycleOwner, { places ->
            val adapter = PlacesAdapter(places) {
                val action =
                    PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailFragment(it)
                NavHostFragment.findNavController(this).navigate(action)
            }
            binding.placesList.layoutManager = LinearLayoutManager(activity)
            binding.placesList.adapter = adapter
        })

        binding.fab.setOnClickListener {
            val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(requireActivity().baseContext)
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
        }
    }
}