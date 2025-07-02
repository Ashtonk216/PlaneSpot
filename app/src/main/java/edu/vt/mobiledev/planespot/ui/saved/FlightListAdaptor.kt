package edu.vt.mobiledev.planespot.ui.saved

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.vt.mobiledev.planespot.databinding.ListItemFlightBinding
import edu.vt.mobiledev.planespot.ui.component.FlightCard

class FlightHolder(
    val binding: ListItemFlightBinding
) : RecyclerView.ViewHolder(binding.root) {

}

class FlightListAdapter(
    private val flights: List<FlightCard>,
    private val onFlightClick: (FlightCard) -> Unit
) : RecyclerView.Adapter<FlightHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FlightHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemFlightBinding.inflate(inflater, parent, false)
        return FlightHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FlightHolder,
        position: Int
    ) {
        val flight = flights[position]
        holder.apply {
            binding.flightNumber.text = flight.flight
            binding.saveDate.text = flight.date.toString()
        }

        holder.itemView.setOnClickListener {
            onFlightClick(flight)
        }

    }

    override fun getItemCount() = flights.size
}