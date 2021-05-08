package com.example.visitplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.visitplanner.databinding.FragmentPlacesListBinding

class PlacesListFragment : Fragment() {

    private var _binding: FragmentPlacesListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlacesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PlacesAdapter(
            listOf(
                Place(
                    "Eiffel Tower",
                    false,
                    "The Eiffel Tower is a wrought-iron lattice tower on the Champ de Mars in Paris, France. It is named after the engineer Gustave Eiffel, whose company designed and built the tower.",
                    Pair(48.8584, 2.2945)
                ), Place(
                    "Cloud Gate",
                    false,
                    "Cloud Gate is a public sculpture by Indian-born British artist Sir Anish Kapoor",
                    Pair(41.8826572, -87.6254979)
                ),
                Place(
                    "Guatapé",
                    true,
                    "Guatapé is an Andean resort town in northwest Colombia, east of Medellín",
                    Pair(6.2338, -75.1592)
                )
            )
        ) {
            val action =
                PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailFragment(it)
            NavHostFragment.findNavController(this).navigate(action)
        }
        binding.placesList.layoutManager = LinearLayoutManager(activity)
        binding.placesList.adapter = adapter
    }
}