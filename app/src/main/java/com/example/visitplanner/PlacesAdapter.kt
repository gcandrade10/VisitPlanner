package com.example.visitplanner

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.visitplanner.databinding.PlaceItemBinding
import kotlinx.parcelize.Parcelize

@Parcelize
data class Place(
    val label: String,
    val isVisited: Boolean,
    val description: String,
    val latLong: Pair<Double, Double>
) : Parcelable

class PlacesAdapter(
    private val places: List<Place>, private val listener: (Place) -> Unit
) :
    RecyclerView.Adapter<ForecastViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val itemBinding =
            PlaceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ForecastViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(places[position], listener)
    }

    override fun getItemCount() = places.size

}

class ForecastViewHolder(private val itemBinding: PlaceItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(place: Place, listener: (Place) -> Unit) {
        itemBinding.placeItemTitle.text = place.label
        itemBinding.placeItemDescription.text = place.description
        itemBinding.root.setOnClickListener { listener(place) }
    }
}

