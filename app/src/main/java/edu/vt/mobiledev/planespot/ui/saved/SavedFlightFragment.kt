package edu.vt.mobiledev.planespot.ui.saved

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import edu.vt.mobiledev.planespot.databinding.FragmentFlightListBinding
import edu.vt.mobiledev.planespot.ui.component.FlightCard
import edu.vt.mobiledev.planespot.ui.detail.BasicFlightActivity
import edu.vt.mobiledev.planespot.ui.detail.EnrichedFlightActivity
import kotlinx.coroutines.launch

const val TAG = "SavedFlightFragment"

class SavedFlightFragment : Fragment() {

    private val savedFlightDetailsLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val wasDeleted = result.data?.getBooleanExtra("wasDeleted", false) == true
                Log.d(TAG, "Was deleted: $wasDeleted")
                if (wasDeleted) {
                    val flight = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        result.data?.getParcelableExtra("flightData", FlightCard::class.java)
                    } else {
                        @Suppress("DEPRECATION")
                        result.data?.getParcelableExtra("flightData")
                    }
                    Log.d(TAG, "Deleting flight: ${flight?.flight}")
                    flight?.let {
                        viewLifecycleOwner.lifecycleScope.launch {
                            flightListViewModel.deleteFlight(it)
                            Log.d(TAG, "Deleted flight: ${it.flight}")
                            loadFlights()
                        }
                    }
                }
            }
        }

    private var _binding: FragmentFlightListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val flightListViewModel: SavedViewModel by viewModels()

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

        viewLifecycleOwner.lifecycleScope.launch {
            loadFlights()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun loadFlights() {
        val flights = flightListViewModel.loadFlights()
        val adapter = FlightListAdapter(flights) { clickedFlight ->
            Log.d(TAG, "Clicked on flight: ${clickedFlight.flight}")
            val flight = clickedFlight
            val intent = when (flight.aircraftName) {
                null -> Intent(requireContext(), SavedBasicFlightActivity::class.java)
                else -> Intent(requireContext(), SavedEnrichedFlightActivity::class.java)
            }.apply {
                putExtra("flightData", flight)
            }

            intent.let {
                savedFlightDetailsLauncher.launch(it)
            }
        }
        binding.flightRecyclerView.adapter = adapter
    }
}