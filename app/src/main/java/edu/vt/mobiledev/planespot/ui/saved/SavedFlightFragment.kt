package edu.vt.mobiledev.planespot.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import edu.vt.mobiledev.planespot.databinding.FragmentFlightListBinding

const val TAG = "SavedFlightFragment"

class SavedFlightFragment : Fragment() {
    private var _binding: FragmentFlightListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val flightListViewModel: FlightListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFlightListBinding.inflate(inflater, container, false)
        binding.flightRecyclerView.layoutManager = LinearLayoutManager(context)

        val flights = flightListViewModel.flights
        val adapter = FlightListAdapter(flights)
        binding.flightRecyclerView.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}